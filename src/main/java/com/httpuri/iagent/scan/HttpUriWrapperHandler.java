package com.httpuri.iagent.scan;

import com.httpuri.iagent.HttpUriConf;
import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.annotation.PathKey;
import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.exception.HttpUriArgumentException;
import com.httpuri.iagent.logging.LogFactory;
import com.httpuri.iagent.logging.Logger;
import com.httpuri.iagent.request.HttpExecutor;
import com.httpuri.iagent.request.SimpleHttpExecutor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpUri Wrapper Handler
 * @see HttpUriBean
 */
public class HttpUriWrapperHandler {

    private static final Logger logger = LogFactory.getLogger(HttpUriWrapperHandler.class);

    private HttpUriConf conf;

    public HttpUriWrapperHandler (HttpUriConf conf) {
        this.conf = conf;
    }

    /**
     * create HttpUriBean
     * @param paramUri
     * @param wrapper
     */
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

        if (logger.isDebugEnabled())
            logger.debug(" Created HttpUriBean Data :" + bean);

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
            String paramKey = pathKey.value();
            if(url.contains("{" + paramKey + "}")){
                argsMap.put(paramKey,i);
            }else{
                logger.error("@PathKey is not found in @ParamUri url");
                throw new HttpUriArgumentException("@PathKey is not found in @ParamUri url");
            }
        }

        wrapper.getBean().setPathParams(argsMap);

    }

    /**
     * create HttpExecutor
     * @param paramUri
     * @param wrapper
     */
    protected void handleHttpExecutor(ParamUri paramUri, HttpUriWrapper wrapper) {
        HttpExecutor executor = null;

        if(paramUri != null){
            Class<?> cls = paramUri.httpExecutor();
            //repeat use HttpExecutor
            if(this.conf.getExecutorMap().containsKey(cls)) {
                executor = this.conf.getExecutorMap().get(cls);
            } else {
                if(cls == null || cls.equals(SimpleHttpExecutor.class)) {
                    executor = new SimpleHttpExecutor();
                } else {
                    try {
                        executor = (HttpExecutor) cls.newInstance();
                    } catch (Exception e) {
                        logger.error(" Not Found Class:" + cls);
                        throw new HttpUriArgumentException("Not Found Class:" + cls);
                    }
                }
                //register new httpExecutor
                this.conf.getExecutorMap().put(cls, executor);
            }
        }
        if (logger.isDebugEnabled())
            logger.debug(" Created HttpExecutor Object:" + executor);
        wrapper.setExecutor(executor);
    }
}
