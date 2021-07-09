package com.httpuri.iagent.util;

import java.util.Map;

public class MapUtil {

    public static String getNotNullString(Map map,String key){
        return map == null ? "" : map.get(key) == null ? "" : map.get(key).toString();
    }

}
