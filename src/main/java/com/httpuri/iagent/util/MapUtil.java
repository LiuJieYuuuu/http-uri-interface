package com.httpuri.iagent.util;

import java.util.Map;

/**
 * <b>Map common type handler</b>
 */
public class MapUtil {

    /**
     * <b>get Map'value by Map'key</b>
     * @param map
     * @param key
     * @return
     */
    public static String getNotNullString(Map map,String key){
        return map == null ? "" : map.get(key) == null ? "" : map.get(key).toString();
    }

}
