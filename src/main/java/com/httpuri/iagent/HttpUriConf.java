package com.httpuri.iagent;

import com.httpuri.iagent.proxy.UriProxy;
import com.httpuri.iagent.scan.ClassPathBeanScanner;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集中控制器
 */
public class HttpUriConf {

    private String [] basePackages;

    private Set<Class> classSet = new HashSet<Class>();

    Map<Class,Object> uriMap = new ConcurrentHashMap<Class,Object>(4);

    public HttpUriConf(){
        super();
    }

    public HttpUriConf(String basePackages){
        this.basePackages = new String[]{basePackages};
    }

    public String [] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String [] basePackages) {
        this.basePackages = basePackages;
    }

    public void loadUriPathPackage(){
        ClassPathBeanScanner scanner = new ClassPathBeanScanner();
        scanner.scannerPackages(this.classSet,this.basePackages);
        UriProxy.loadProxyClass(this.classSet,this.uriMap);

    }

    public <T> T getUri(Class<T>  cls){
        return (T) this.uriMap.get(cls);
    }

}
