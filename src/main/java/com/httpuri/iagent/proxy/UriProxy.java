package com.httpuri.iagent.proxy;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

public class UriProxy {

    private <T> T newInstance(Class<T> cls){
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{cls},
                new UriInvocationHandler());
    }

    public void loadProxyClass(Set<Class> uris, Map<Class,Object> classMap){
        for(Class cls : uris){
            classMap.put(cls,this.newInstance(cls));
        }
    }

}
