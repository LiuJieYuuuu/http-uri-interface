package com.httpuri.iagent.proxy;

import com.httpuri.iagent.HttpUriConf;

import java.lang.reflect.Proxy;

/**
 * <p>proxy implementation class</p>
 *
 * @see UriProxy
 */
public class UriProxyFactory {

    private HttpUriConf conf;

    public UriProxyFactory(HttpUriConf conf){
        super();
        this.conf = conf;
    }

    /**
     * <b> create Object of interface by Dynamic Proxy </b>
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T newInstance(Class<T> cls){
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{cls},
                new UriProxy(conf));
    }

}
