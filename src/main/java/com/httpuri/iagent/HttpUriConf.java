package com.httpuri.iagent;

import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.exception.HttpUriArgumentException;
import com.httpuri.iagent.scan.ClassPathBeanScanner;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * http uri iagent all Configuration
 */
public class HttpUriConf {
    //scanner package
    private String [] basePackages;
    //interface and proxy object of Map
    Map<Class,Object> uriMap = new ConcurrentHashMap<>(4);
    //method and HttpUriWrapper of method Map
    Map<Method,HttpUriWrapper> uriWrapperMap = new ConcurrentHashMap<>(4);
    //is complete initialize
    boolean isCompleteInitialize = false;

    public HttpUriConf(){
        super();
    }

    /**
     * set base packages
     * @param basePackages
     */
    public HttpUriConf(String[] basePackages){
        this.basePackages = basePackages;
        init();
    }

    public HttpUriConf(String basePackages){
        this.basePackages = new String[]{basePackages};
        init();
    }

    public void setBasePackages(String [] basePackages) {
        this.basePackages = basePackages;
    }

    public boolean isCompleteInitialize() {
        return isCompleteInitialize;
    }

    /**
     * get all interface class and proxy object
     * @return
     */
    public Map<Class, Object> getUriMap() {
        return uriMap;
    }
    /**
     * get all method of interface class and HttpUriWrapper object
     * @return
     */
    public Map<Method, HttpUriWrapper> getUriWrapperMap() {
        return uriWrapperMap;
    }

    /**
     * initialize package and scanner package
     */
    public void init(){
        if(this.basePackages != null && !isCompleteInitialize){
            ClassPathBeanScanner scanner = new ClassPathBeanScanner(this);
            scanner.scannerPackages(this.uriMap,this.uriWrapperMap,this.basePackages);
            isCompleteInitialize = true;
        }else{
            throw new HttpUriArgumentException("base packages is null");
        }

    }

    /**
     * get proxy object by interface class
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T getUri(Class<T> cls){
        return (T) this.uriMap.get(cls);
    }

}
