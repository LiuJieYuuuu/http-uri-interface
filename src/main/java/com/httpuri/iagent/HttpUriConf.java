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
    //扫描包集合
    private String [] basePackages;
    //所有的接口类代理对象的的Set集合
    private Set<Class> classSet = new HashSet<Class>();
    //所有接口类以及代理实现类集合
    Map<Class,Object> uriMap = new ConcurrentHashMap<Class,Object>(4);

    //处理代理对象
    private UriProxy proxy = new UriProxy();

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

    /**
     * 初始化包及扫描
     */
    public void loadUriPathPackage(){
        ClassPathBeanScanner scanner = new ClassPathBeanScanner();
        scanner.scannerPackages(this.classSet,this.basePackages);
        proxy.loadProxyClass(this.classSet,this.uriMap);

    }

    //获取对应的接口代理类
    public <T> T getUri(Class<T>  cls){
        return (T) this.uriMap.get(cls);
    }

}
