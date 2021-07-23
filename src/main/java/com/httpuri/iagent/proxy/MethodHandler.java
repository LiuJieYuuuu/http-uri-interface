package com.httpuri.iagent.proxy;

import com.alibaba.fastjson.JSON;
import com.httpuri.iagent.HttpUriConf;
import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.exception.HttpUriArgumentException;

import java.lang.reflect.Method;

/**
 * <b>handle interface method annotation</b>
 */
public class MethodHandler {

    private HttpUriConf conf;

    private ParameterHandler parameterHandler = new ParameterHandler();

    public void setConf(HttpUriConf conf) {
        this.conf = conf;
    }

    /**
     * <p>get http request result data by annotation of method</p>
     * @param method
     * @param args
     * @return
     */
    protected Object getMethodResult(Method method, Object[] args){
        HttpUriWrapper wrapper = null;
        try {
            wrapper = conf.getUriWrapperMap().get(method);
            wrapper.getBean().setParams(parameterHandler.getParameterMapByMethod(method.getParameters(),args));
        } catch (Exception e) {
            throw new HttpUriArgumentException("iagent warn the method is not exists @ParamUri:" + method);
        }
        Object result = wrapper.getExecutor().sendHttp(wrapper.getBean(),args);

        return packagingResultObjectType(result == null ? null : result.toString(), method.getReturnType());
    }

    /**
     * <b>JSON parse result data,use alibaba fastjson</b>
     * @param result
     * @param cls
     * @param <T>
     * @return
     */
    private <T> T packagingResultObjectType(String result,Class<T> cls){
        try {
            if(result == null)
                return null;
            else if(cls.equals(String.class))
                return (T) result;
            else
                return JSON.parseObject(result,cls);
        } catch (Exception e) {
            throw new HttpUriArgumentException("Not Support JSON Type Change");
        }
    }

}
