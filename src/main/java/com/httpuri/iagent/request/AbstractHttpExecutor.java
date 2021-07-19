package com.httpuri.iagent.request;

import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.util.MapUtil;
import com.httpuri.iagent.util.StringUtil;

import java.util.Map;

public abstract class AbstractHttpExecutor implements HttpExecutor {

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
        if(url.contains("{") && url.contains("}")){//可能存在于Map的数据当中
            Map params = bean.getParams();
            String param = null;
            while((param = StringUtil.getStringByTags(url,"{","}")) != null && !"".equals(param)){
                url = url.replace("{" + param + "}", MapUtil.getNotNullString(params,param));
            }
            if(url.contains("{") && url.contains("}"))
                throw new IllegalArgumentException("the " + url + " contains pathkey not found in args");
        }
        return url;
    }
}
