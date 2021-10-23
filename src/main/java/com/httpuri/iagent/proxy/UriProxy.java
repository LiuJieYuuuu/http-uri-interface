package com.httpuri.iagent.proxy;

import com.httpuri.iagent.HttpUriConf;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>dynamic proxy implementation class ,it's InvocationHandler Class</p>
 *
 * @see UriProxyFactory
 */
public class UriProxy implements InvocationHandler {

    private HttpUriConf conf;

    /**
     * <p>method of interface handler class</p>
     */
    private MethodHandler methodHandler = new MethodHandler();

    public UriProxy(HttpUriConf conf){
        this.conf = conf;
    }

    /**
     * <p>Dynamic Proxy real run function</p>
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this,args);
        }else{
            methodHandler.setConf(conf);
            return methodHandler.getMethodResult(method,args);
        }
    }

}
