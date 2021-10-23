package com.httpuri.iagent.scan;

import com.httpuri.iagent.HttpUriConf;
import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.exception.HttpUriArgumentException;
import com.httpuri.iagent.logging.LogFactory;
import com.httpuri.iagent.logging.Logger;
import com.httpuri.iagent.proxy.UriProxyFactory;
import com.httpuri.iagent.request.HttpExecutor;
import com.httpuri.iagent.request.SimpleHttpExecutor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <b>interface is the must scanner,it's to be UriProxyFactory and UriProxy</b>
 *
 * see UriProxyFactory.java
 * see UriProxy.java
 */
public class ClassPathBeanScanner {

    private static final Logger logger = LogFactory.getLogger(ClassPathBeanScanner.class);

    /**
     * <b>use UriProxyFactory to create proxy factory class</b>
     */
    private UriProxyFactory proxyFactory;

    /**
     * <b>global configuration</b>
     */
    private HttpUriConf conf;


    /**
     * <b>HttpUriWrapperHandler is specialized HttpUriWrapper</b>
     */
    private HttpUriWrapperHandler wrapperHandler;

    /**
     * <b>this is global Configuration's Constructor</b>
     * @param conf
     */
    public ClassPathBeanScanner(HttpUriConf conf){
        super();
        this.conf = conf;
        proxyFactory = new UriProxyFactory(conf);
        wrapperHandler = new HttpUriWrapperHandler(conf);
    }

    /**
     * <b>scanner interface of base packages ,
     * and load to wrapper map </b>
     * @param uriMap
     * @param uriWrapperMap
     * @param basePackages
     */
    public void scannerPackages(Map<Class,Object> uriMap, Map<Method,HttpUriWrapper> uriWrapperMap, String... basePackages) {
        for(String packages : basePackages){
            try {
                String path = this.getClass().getResource("/").getPath();
                String packagePath = packages.replace(".", "/");

                Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("/" + packagePath);
                //at present is jar running
                if(resources.hasMoreElements()){
                    while(resources.hasMoreElements()){
                        URL url = resources.nextElement();
                        if("jar".equals(url.getProtocol())){
                            JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                            if(urlConnection != null){
                                JarFile jarFile = urlConnection.getJarFile();
                                if (jarFile != null) {
                                    Enumeration<JarEntry> jarEntries = jarFile.entries();
                                    while (jarEntries.hasMoreElements()) {
                                        JarEntry jarEntry = jarEntries.nextElement();
                                        String jarEntryName = jarEntry.getName();
                                        if(jarEntryName.contains(packagePath)){
                                            String fileName = jarEntryName.replace(".class","").replace(packagePath,"");
                                            if (fileName.startsWith("/"))
                                                fileName = fileName.substring(1,fileName.length());
                                            if("".equals(fileName.trim())) continue;
                                            Class<?> cls = Class.forName(packages + "." + fileName);
                                            register(cls,uriWrapperMap);
                                            if (logger.isDebugEnabled()) {
                                                logger.debug("iagent load: " + fileName);
                                            }
                                            uriMap.put(cls,proxyFactory.newInstance(cls));
                                        }
                                    }
                                }
                            }
                        }else if("file".equals(url.getProtocol())){
                            loadFileTypeScanner(path,packagePath,packages,uriMap,uriWrapperMap);
                        }
                    }
                }else{
                    //at present not jar running
                    loadFileTypeScanner(path,packagePath,packages,uriMap,uriWrapperMap);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * <b>handle annotation of interface is loading to httpUriWrapper </b>
     * @param cls
     * @param uriWrapperMap
     * @param <T>
     */
    private <T> void register(Class<T> cls,Map<Method,HttpUriWrapper> uriWrapperMap){
        ParamUri clsAnno = cls.getAnnotation(ParamUri.class);
        HttpUriWrapper wrapper = new HttpUriWrapper();
        wrapperHandler.handleHttpUriBean(clsAnno, wrapper);
        wrapperHandler.handleHttpExecutor(clsAnno, wrapper);

        Method[] methods = cls.getDeclaredMethods();
        /**
         * handle method of interface is recover annotation of interface
         */
        for(int i = 0;i < methods.length; i++){
            ParamUri methodAnno = methods[i].getAnnotation(ParamUri.class);
            if(methodAnno == null){
                logger.warn("iagent warn the method is not exists @ParamUri:" + methods[i]);
                continue;
            }
            HttpUriBean cloneBean = wrapper.getBean().clone();
            String url = methodAnno.url();
            if(url == null || Objects.equals("",url))
                url = methodAnno.value();
            if(url == null || Objects.equals("",url)) {
                logger.error("@ParamUri#url is Null");
                throw new NullPointerException("@ParamUri#url is Null");
            }
            if(cloneBean.getUrl() == null) {
                cloneBean.setUrl(methodAnno.url());
            } else {
                if(cloneBean.getUrl().endsWith("///")) {
                    cloneBean.setUrl(cloneBean.getUrl() + url);
                }else {
                    cloneBean.setUrl(cloneBean.getUrl() + (url.startsWith("///") ? url.substring(1, url.length()) : url));
                }
            }
            cloneBean.setConnectionTime(methodAnno.connectionTime());
            cloneBean.setContentType(methodAnno.contentType());
            cloneBean.setReadTime(methodAnno.readTime());
            cloneBean.setRequestType(methodAnno.requestType());
            if (logger.isDebugEnabled())
                logger.debug(methods[i] + " url:" + cloneBean.getUrl());
            /*System.out.println(methods[i] + " url:" + cloneBean.getUrl());*/
            HttpExecutor executor = wrapper.getExecutor();
            HttpUriWrapper childWrapper = new HttpUriWrapper(cloneBean);
            wrapperHandler.handlePathKey(methods[i],childWrapper);
            if( executor == null || (!methodAnno.httpExecutor().equals(SimpleHttpExecutor.class)
                    && !methodAnno.httpExecutor().equals(executor.getClass())) ){
                wrapperHandler.handleHttpExecutor(methodAnno, childWrapper);
            }else{
                childWrapper.setExecutor(executor);
            }
            uriWrapperMap.put(methods[i],childWrapper);
        }

    }

    /**
     * <p>at present not jar running,load file</p>
     * @param path
     * @param packagePath
     * @param packages
     * @param uriMap
     * @param uriWrapperMap
     * @throws ClassNotFoundException
     */
    private void loadFileTypeScanner(String path,String packagePath,String packages,
                                     Map<Class,Object> uriMap, Map<Method,HttpUriWrapper> uriWrapperMap)
            throws ClassNotFoundException {
        //at present not jar running
        File file = new File(path + packagePath);
        if(file == null)
            throw new HttpUriArgumentException(" interface package is not exists: " + (path + packagePath));
        File[] files = file.listFiles();
        if(files == null)
            throw new HttpUriArgumentException(" interface package files is not exists: " + (path + packagePath));
        for(File f : files){
            String classPath = packages + "." + f.getName().replace(".class","");
            if(f.isDirectory()){
                throw new HttpUriArgumentException(" under the package path includes directories:path" + packages);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("iagent load: " + classPath);
            }
            Class<?> cls = Class.forName(classPath);
            register(cls,uriWrapperMap);
            uriMap.put(cls,proxyFactory.newInstance(cls));
        }
    }

}
