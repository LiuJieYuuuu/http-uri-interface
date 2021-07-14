package com.httpuri.iagent.proxy;

import com.alibaba.fastjson.JSON;
import com.httpuri.iagent.HttpUriConf;
import com.httpuri.iagent.annotation.ParamKey;
import com.httpuri.iagent.builder.HttpUriWrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MethodHandler {

    private HttpUriConf conf;

    public void setConf(HttpUriConf conf) {
        this.conf = conf;
    }

    /**
     * 根据方法上的注解获取请求结果集
     * @param method
     * @param args
     * @return
     */
    public Object getMethodResult(Method method, Object[] args){
        Parameter[] parameters = method.getParameters();
        Map<String, Object> param = new HashMap<>();
        for (int i = 0 ; i < parameters.length ; i ++){
            //如果是Map的话，则直接将数据填充至Map参数当中
            if(!Map.class.equals(parameters[i].getType())){
                ParamKey annotationKey = parameters[i].getAnnotation(ParamKey.class);
                param.put(annotationKey.key(),args[i]);
            }else{
                param.putAll((Map) args[i]);
            }
        }

        HttpUriWrapper wrapper = null;
        try {
            wrapper = conf.getUriBeanMap().get(method);
            wrapper.getBean().setParams(param);
        } catch (Exception e) {
            throw new IllegalArgumentException("iagent warn the method is not exists @ParamUri:" + method);
        }
        Object result = wrapper.getExecutor().sendHttp(wrapper.getBean());

        return packagingResultObjectType(result == null ? null : result.toString(), method.getReturnType());
    }


    private <T> T packagingResultObjectType(String result,Class<T> cls){
        try {
            if(result == null)
                return null;
            else if(cls.equals(String.class))
                return (T) result;
            else
                return JSON.parseObject(result,cls);
        } catch (Exception e) {
            throw new IllegalArgumentException("Not Support JSON Type Change");
        }
    }

}
