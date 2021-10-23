package com.httpuri.iagent.request;

import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.exception.HttpUriArgumentException;
import com.httpuri.iagent.util.MapUtil;
import com.httpuri.iagent.util.StringUtil;

import java.util.Map;

/**
 * <b> the Abstract Common Http Request Executor</b>
 */
public abstract class AbstractHttpExecutor implements HttpExecutor {

    /**
     * <b>specific handle http request url path params</b>
     * @param bean
     * @param args
     * @return
     */
    @Override
    public String handlePathKey(HttpUriBean bean, Object[] args) {
        String url = bean.getUrl();
        Map<String,Integer> argsMap = bean.getPathParams();
        if(argsMap == null) return url;
        for (Map.Entry<String,Integer> entry : argsMap.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            url = url.replace("{" + key + "}",args[value].toString());
        }
        if(url.contains("{") && url.contains("}")){//may storage in map data
            Map params = bean.getParams();
            String param = null;
            while((param = StringUtil.getStringByTags(url,"{","}")) != null && !"".equals(param)){
                url = url.replace("{" + param + "}", MapUtil.getNotNullString(params,param));
            }
            if(url.contains("{") && url.contains("}"))
                throw new HttpUriArgumentException("the " + url + " contains pathkey not found in args");
        }
        return url;
    }
}
