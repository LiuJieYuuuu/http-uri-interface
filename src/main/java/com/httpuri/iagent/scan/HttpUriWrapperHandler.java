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
import java.util.Objects;

/**
 * <b>it's the HttpUriWrapper type handler,
 * HttpUriWrapperHandler is to be used CLassPathBeanScanner </b>
 *
 * see CLassPathBeanScanner.java
 */
public class HttpUriWrapperHandler {

    /**
     * <b>parse @interface data,
     * create HttpUriBean Object set HttpUriWrapper Object</b>
     * @param paramUri
     * @param wrapper
     */
    protected void handleHttpUriBean(ParamUri paramUri, HttpUriWrapper wrapper){
        HttpUriBean bean = null;
        if(paramUri != null) {
            String url = paramUri.url();
            if(Objects.equals("",url))
                url = paramUri.value();
            if(Objects.equals("",url))
                throw new NullPointerException("@ParamUri'url is Null");
            bean = HttpUriBean.HttpUriBeanBuilder.builder().url(url)
                    .requestType(paramUri.requestType())
                    .contentType(paramUri.contentType())
                    .connectionTime(paramUri.connectionTime())
                    .readTime(paramUri.readTime())
                    .build();
        }else {
            bean = HttpUriBean.HttpUriBeanBuilder.builder().build();
        }
        wrapper.setBean(bean);
    }

    /**
     * <b>Handler Method'pathKey replace {param} to real param,
     * and handle path key params to field of HttpUriBean path params</b>
     * @param method
     * @param wrapper
     */
    protected void handlePathKey(Method method, HttpUriWrapper wrapper){
        ParamUri annotation = method.getAnnotation(ParamUri.class);
        String url = annotation.url();
        if (url == null || Objects.equals("",url))
            url = annotation.value();
        if (url == null || Objects.equals("",url))
            throw new NullPointerException("@ParamUri'url is Null");
        Map<String,Integer> argsMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i=0; i < parameters.length ; i++){
            Class<?> type = parameters[i].getType();
            if(Map.class.equals(type)){
                continue;
            }
            PathKey pathKey = parameters[i].getAnnotation(PathKey.class);
            if(pathKey == null) continue;
            String paramKey = pathKey.value();
            if(url.contains("{" + paramKey + "}")){
                argsMap.put(paramKey,i);
            }else{
                throw new HttpUriArgumentException("@PathKey is not found in @ParamUri url");
            }
        }

        wrapper.getBean().setPathParams(argsMap);

    }

    /**
     * <b>create HttpExecutor implementation class is copy to HttpUriWrapper</b>
     * @param paramUri
     * @param wrapper
     */
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
                    try {
                        throw new IllegalAccessException("Not Found Class:" + cls);
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
        }
        wrapper.setExecutor(executor);
    }
}
