package com.httpuri.iagent.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.httpuri.iagent.exception.LogException;
import com.httpuri.iagent.json.JSONSupport;
import com.httpuri.iagent.logging.LogFactory;
import com.httpuri.iagent.logging.Logger;

import java.util.List;

/*
 * fastjson support
 */
public class FastJsonSupport implements JSONSupport {

    private Logger logger = LogFactory.getLogger(FastJsonSupport.class);

    public FastJsonSupport() {
        super();
        try {
            String version = JSON.VERSION;
            if (logger.isDebugEnabled()) {
                logger.debug("use fast json version:" + version);
            }
        } catch (Throwable t) {
            logger.error("not alibaba fastjson framework in project");
            throw new LogException("not alibaba fastjson framework in project");
        }
    }

    @Override
    public <T> List<T> getJSONArray(String text, Class<T> clazz) {
        return com.alibaba.fastjson.JSON.parseArray(text, clazz);
    }

    @Override
    public <T> T getJSONObject(String text, Class<T> clazz) {
        return com.alibaba.fastjson.JSON.parseObject(text, clazz);
    }

}
