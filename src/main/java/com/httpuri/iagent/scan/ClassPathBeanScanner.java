package com.httpuri.iagent.scan;

import com.httpuri.iagent.HttpUriConf;
import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.proxy.UriProxy;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

public class ClassPathBeanScanner {

    //处理代理对象
    private UriProxy proxy;

    private HttpUriConf conf;

    private ClassPathBeanScanner(){
        super();
    }

    public ClassPathBeanScanner(HttpUriConf conf){
        super();
        this.conf = conf;
        proxy = new UriProxy(conf);
    }

    public void scannerPackages(Map<Class,Object> uriMap, Map<Method,HttpUriBean> uriBeanMap, String... basePackages) {
        for(String packages : basePackages){
            try {
                String path = this.getClass().getResource("/").getPath();
                String packagePath = packages.replace(".", File.separator);
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
                    register(cls,uriBeanMap);
                    uriMap.put(cls,proxy.newInstance(cls));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private <T> void register(Class<T> cls,Map<Method,HttpUriBean> uriBeanMap){
        ParamUri clsAnno = cls.getAnnotation(ParamUri.class);
        HttpUriBean.HttpUriBeanBuilder builder = new HttpUriBean.HttpUriBeanBuilder();
        HttpUriBean bean = null;
        if(clsAnno != null)
            bean = builder.url(clsAnno.url())
                    .requestType(clsAnno.requestType())
                    .contentType(clsAnno.contentType())
                    .connectionTime(clsAnno.connectionTime())
                    .readTime(clsAnno.readTime())
                    .build();
        else
            bean = builder.build();
        Method[] methods = cls.getDeclaredMethods();
        for(int i = 0;i < methods.length; i++){
            ParamUri methodAnno = methods[i].getAnnotation(ParamUri.class);
            if(methodAnno == null){
                System.out.println("iagent warn the method is not exsits @ParamUri:" + methods[i]);
                continue;
            }
            HttpUriBean cloneBean = bean.clone();
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
            uriBeanMap.put(methods[i],cloneBean);
        }


    }


}
