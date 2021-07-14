package com.httpuri.iagent.scan;

import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.request.HttpExecutor;
import com.httpuri.iagent.request.SimpleHttpExecutor;

public class HttpUriWrapperHandler {

    public HttpUriWrapper handleHttpUriBean(ParamUri paramUri, HttpUriWrapper wrapper){
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

        return wrapper;
    }

    public HttpUriWrapper handleHttpExecutor(ParamUri paramUri, HttpUriWrapper wrapper) {
        HttpExecutor executor = null;

        if(paramUri != null){
            Class<?> cls = paramUri.httpExecutor();
            if(cls == null || cls.equals(SimpleHttpExecutor.class))
                executor = new SimpleHttpExecutor();
            else
                try {
                    executor = (HttpExecutor) cls.newInstance();
                } catch (Exception e) {
                    try {
                        throw new IllegalAccessException("Not Found Class:" + cls);
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
        }
        if(wrapper == null)
            wrapper = new HttpUriWrapper(executor);
        else if (executor != null)
            wrapper.setExecutor(executor);

        return wrapper;
    }
}
