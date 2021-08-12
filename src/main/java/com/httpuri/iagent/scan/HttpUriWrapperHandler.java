package com.httpuri.iagent.scan;

import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.annotation.PathKey;
import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.exception.HttpUriArgumentException;
import com.httpuri.iagent.request.HttpExecutor;
import com.httpuri.iagent.request.SimpleHttpExecutor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class HttpUriWrapperHandler {

    protected void handleHttpUriBean(ParamUri paramUri, HttpUriWrapper wrapper){
        HttpUriBean bean = null;
        HttpUriBean.HttpUriBeanBuilder builder = new HttpUriBean.HttpUriBeanBuilder();
        if(paramUri != null)
            bean = builder.url(paramUri.url())
                    .requestType(paramUri.requestType())
                    .contentType(paramUri.contentType())
                    .connectionTime(paramUri.connectionTime())
                    .readTime(paramUri.readTime())
                    .build();
        else
            bean = builder.build();

        if(wrapper == null)
            wrapper = new HttpUriWrapper(bean);
        else
            wrapper.setBean(bean);
    }

    protected void handlePathKey(Method method, HttpUriWrapper wrapper){
        ParamUri annotation = method.getAnnotation(ParamUri.class);
        String url = annotation.url();
        Map<String,Integer> argsMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i=0; i < parameters.length ; i++){
            Class<?> type = parameters[i].getType();
            if(Map.class.equals(type)){
                continue;
            }
            PathKey pathKey = parameters[i].getAnnotation(PathKey.class);
            if(pathKey == null) continue;
            String paramKey = pathKey.key();
            if(url.contains("{" + paramKey + "}")){
                argsMap.put(paramKey,i);
            }else{
                throw new HttpUriArgumentException("@PathKey is not found in @ParamUri url");
            }
        }

        wrapper.getBean().setPathParams(argsMap);

    }

    protected void handleHttpExecutor(ParamUri paramUri, HttpUriWrapper wrapper) {
        HttpExecutor executor = null;

        if(paramUri != null){
            Class<?> cls = paramUri.httpExecutor();
            if(cls == null || cls.equals(SimpleHttpExecutor.class))
                executor = new SimpleHttpExecutor();
            else
                try {
                    executor = (HttpExecutor) cls.newInstance();
                } catch (Exception e) {
                    throw new HttpUriArgumentException("Not Found Class:" + cls);
                }
        }
        if(wrapper == null)
            wrapper = new HttpUriWrapper(executor);
        else if (executor != null)
            wrapper.setExecutor(executor);
    }
}
