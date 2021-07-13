package com.httpuri.iagent.proxy;

import com.httpuri.iagent.HttpUriConf;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UriInvocationHandler implements InvocationHandler {

    private HttpUriConf conf;

    private MethodHandler methodHandler = new MethodHandler();

    public UriInvocationHandler(){
        super();
    }

    public UriInvocationHandler(HttpUriConf conf){
        this.conf = conf;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this,args);
        }else{
            return methodHandler.getMethodResult(method,args);
        }
    }




}
