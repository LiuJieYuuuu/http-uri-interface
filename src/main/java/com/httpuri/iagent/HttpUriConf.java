package com.httpuri.iagent;

import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.scan.ClassPathBeanScanner;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集中控制器
 */
public class HttpUriConf {
    //扫描包集合
    private String [] basePackages;

    Map<Class,Object> uriMap = new ConcurrentHashMap<>(4);

    Map<Method,HttpUriWrapper> uriWrapperMap = new ConcurrentHashMap<>(4);

    public HttpUriConf(){
        super();
    }

    public HttpUriConf(String basePackages){
        this.basePackages = new String[]{basePackages};
        init();
    }

    public Map<Method, HttpUriWrapper> getUriBeanMap() {
        return uriWrapperMap;
    }

    public String [] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String [] basePackages) {
        this.basePackages = basePackages;
    }

    /**
     * 初始化包及扫描
     */
    public void init(){
        if(this.basePackages != null && uriMap.isEmpty() && uriWrapperMap.isEmpty()){
            ClassPathBeanScanner scanner = new ClassPathBeanScanner(this);
            scanner.scannerPackages(this.uriMap,this.uriWrapperMap,this.basePackages);
        }

    }

    //获取对应的接口代理类
    public <T> T getUri(Class<T> cls){
        return (T) this.uriMap.get(cls);
    }

}
