package com.httpuri.iagent.proxy;

import com.alibaba.fastjson.JSON;
import com.httpuri.iagent.annotation.ParamKey;
import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.constant.HttpEnum;
import com.httpuri.iagent.request.HttpUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MethodHandler {

    /**
     * 根据方法上的注解获取请求结果集
     * @param method
     * @param args
     * @return
     */
    public Object getMethodResult(Method method, Object[] args){
        ParamUri annotation = method.getAnnotation(ParamUri.class);
        String uri = annotation.url();
        String contentType = annotation.contentType();
        HttpEnum requestType = annotation.requestType();

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

        String result = HttpUtil.sendHttp(uri, param, requestType, contentType);

        return packagingResultObjectType(result, method.getReturnType());
    }


    private <T> T packagingResultObjectType(String result,Class<T> cls){
        return JSON.parseObject(result,cls);
    }

}
