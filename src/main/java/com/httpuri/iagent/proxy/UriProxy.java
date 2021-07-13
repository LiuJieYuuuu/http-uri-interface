package com.httpuri.iagent.proxy;

import com.httpuri.iagent.HttpUriConf;

import java.lang.reflect.Proxy;

public class UriProxy {

    private HttpUriConf conf;

    public UriProxy(HttpUriConf conf){
        super();
        this.conf = conf;
    }

    public <T> T newInstance(Class<T> cls){
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{cls},
                new UriInvocationHandler(conf));
    }

}
