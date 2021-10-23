package com.httpuri.iagent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Register frequently used data
 */
public class TypeAliasRegister {

    private ConcurrentMap<String, Object> registerMap = new ConcurrentHashMap<>(16);

    public void registerAlias(String key, Object value) {
        registerMap.put(key, value);
    }

    public Object getAlias(String key) {
        return registerMap.get(key);
    }

}
