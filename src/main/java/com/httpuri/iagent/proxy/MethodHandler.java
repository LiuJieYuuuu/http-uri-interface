package com.httpuri.iagent.proxy;

import com.httpuri.iagent.HttpUriConf;
import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.exception.HttpUriArgumentException;
import com.httpuri.iagent.exception.JsonException;
import com.httpuri.iagent.json.JSON;
import com.httpuri.iagent.logging.LogFactory;
import com.httpuri.iagent.logging.Logger;

import java.lang.reflect.Method;

/**
 * Method Handler
 */
public class MethodHandler {

    private HttpUriConf conf;

    private ParameterHandler parameterHandler = new ParameterHandler();

    private static final Logger logger = LogFactory.getLogger(MethodHandler.class);

    public void setConf(HttpUriConf conf) {
        this.conf = conf;
    }

    /**
     * get request result by method annotation
     * @param method
     * @param args
     * @return
     */
    protected Object getMethodResult(Method method, Object[] args){
        HttpUriWrapper wrapper = null;
        try {
            wrapper = conf.getUriBeanMap().get(method);
            wrapper.getBean().setParams(parameterHandler.getParameterMapByMethod(method.getParameters(),args));
        } catch (Exception e) {
            logger.error("iagent warn the method is not exists @ParamUri:" + method);
            throw new HttpUriArgumentException("iagent warn the method is not exists @ParamUri:" + method);
        }
        Object result = wrapper.getExecutor().sendHttp(wrapper.getBean(),args);

        return packagingResultObjectType(result == null ? null : result.toString(), method.getReturnType());
    }

    /**
     * change result data to Json Object
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
                return JSON.getJSONObject(result, cls);
        } catch (Exception e) {
            logger.error("Not Support JSON Type Change,Result:[" + result + "], Class:[" + cls + "]");
            throw new JsonException("Not Support JSON Type Change");
        }
    }

}
