package com.httpuri.iagent.scan;

import com.httpuri.iagent.HttpUriConf;
import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.proxy.UriProxy;
import com.httpuri.iagent.request.HttpExecutor;
import com.httpuri.iagent.request.SimpleHttpExecutor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassPathBeanScanner {

    //处理代理对象
    private UriProxy proxy;

    private HttpUriConf conf;

    private HttpUriWrapperHandler wrapperHandler = new HttpUriWrapperHandler();

    public ClassPathBeanScanner(HttpUriConf conf){
        super();
        this.conf = conf;
        proxy = new UriProxy(conf);
    }

    public void scannerPackages(Map<Class,Object> uriMap, Map<Method,HttpUriWrapper> uriWrapperMap, String... basePackages) {
        for(String packages : basePackages){
            try {
                String path = this.getClass().getResource("/").getPath();
                String packagePath = packages.replace(".", "/");

                Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("/" + packagePath);
                //当前为jar执行
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
                                            uriMap.put(cls,proxy.newInstance(cls));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else{
                    //非jar包形式
                    File file = new File(path + packagePath);
                    if(file == null)
                        throw new IllegalArgumentException(" interface package is not exists: " + (path + packagePath));
                    File[] files = file.listFiles();
                    if(files == null)
                        throw new IllegalArgumentException(" interface package files is not exists: " + (path + packagePath));
                    for(File f : files){
                        String classPath = packages + "." + f.getName().replace(".class","");
                        if(f.isDirectory()){
                            throw new IllegalArgumentException(" under the package path includes directories:path" + packages);
                        }
                        Class<?> cls = Class.forName(classPath);
                        register(cls,uriWrapperMap);
                        uriMap.put(cls,proxy.newInstance(cls));
                    }
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

    private <T> void register(Class<T> cls,Map<Method,HttpUriWrapper> uriWrapperMap){
        ParamUri clsAnno = cls.getAnnotation(ParamUri.class);
        HttpUriWrapper wrapper = new HttpUriWrapper();
        wrapperHandler.handleHttpUriBean(clsAnno, wrapper);
        wrapperHandler.handleHttpExecutor(clsAnno, wrapper);

        Method[] methods = cls.getDeclaredMethods();
        for(int i = 0;i < methods.length; i++){
            ParamUri methodAnno = methods[i].getAnnotation(ParamUri.class);
            if(methodAnno == null){
                System.out.println("iagent warn the method is not exsits @ParamUri:" + methods[i]);
                continue;
            }
            HttpUriBean cloneBean = wrapper.getBean().clone();
            String url = methodAnno.url();
            if(cloneBean.getUrl() == null)
                cloneBean.setUrl(methodAnno.url());
            else{
                if(cloneBean.getUrl().endsWith("///"))
                    cloneBean.setUrl(cloneBean.getUrl() + url);
                else
                    cloneBean.setUrl(cloneBean.getUrl() + (url.startsWith("///") ? url.substring(1,url.length()) : url));
            }
            cloneBean.setConnectionTime(methodAnno.connectionTime());
            cloneBean.setContentType(methodAnno.contentType());
            cloneBean.setReadTime(methodAnno.readTime());
            cloneBean.setRequestType(methodAnno.requestType());
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


}
