package com.httpuri.iagent;

import com.httpuri.iagent.builder.HttpUriBean;
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

    Map<Method,HttpUriBean> uriBeanMap = new ConcurrentHashMap<>(4);

    public HttpUriConf(){
        super();
    }

    public HttpUriConf(String basePackages){
        this.basePackages = new String[]{basePackages};
        init();
    }

    public Map<Method, HttpUriBean> getUriBeanMap() {
        return uriBeanMap;
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
        if(this.basePackages != null){
            ClassPathBeanScanner scanner = new ClassPathBeanScanner(this);
            scanner.scannerPackages(this.uriMap,this.uriBeanMap,this.basePackages);
        }

    }

    //获取对应的接口代理类
    public <T> T getUri(Class<T> cls){
        return (T) this.uriMap.get(cls);
    }

}
