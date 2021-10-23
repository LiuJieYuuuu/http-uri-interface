package com.httpuri.iagent.proxy;

import com.httpuri.iagent.annotation.ParamKey;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数处理
 */
public class ParameterHandler {

    protected Map getParameterMapByMethod(Parameter[] parameters, Object[] args){
        Map<String, Object> param = new HashMap<>();
        for (int i = 0 ; i < parameters.length ; i ++){
            //如果是Map的话，则直接将数据填充至Map参数当中
            if(!Map.class.equals(parameters[i].getType())){
                ParamKey annotationKey = parameters[i].getAnnotation(ParamKey.class);
                if(annotationKey == null) continue;
                param.put(annotationKey.value(),args[i]);
            }else{
                param.putAll((Map) args[i]);
            }
        }

        return  param;
    }

}
