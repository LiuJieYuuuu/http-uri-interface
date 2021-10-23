package com.httpuri.iagent.json;

import com.httpuri.iagent.json.fastjson.FastJsonSupport;
import com.httpuri.iagent.json.gson.GsonSupport;
import com.httpuri.iagent.json.jackson.JacksonSupport;

import java.util.List;

public final class JSON {

    private static JSONSupport support = null;

    static {
        useFastjsonSupport();
        useGsonSupport();
        useJacksonSupport();
    }

    private static void useFastjsonSupport() {
        useJson(FastJsonSupport.class);
    }

    private static void useGsonSupport() {
        useJson(GsonSupport.class);
    }

    private static void useJacksonSupport () {
        useJson(JacksonSupport.class);
    }

    public static void useJson(Class<? extends JSONSupport> clazz) {
        try {
            if (support == null)
                support = clazz.newInstance();
        } catch (Exception e) {
            //adapter json frame work
        }
    }

    public static <T> List<T> getJSONArray(String text, Class<T> clazz) {
        return support.getJSONArray(text, clazz);
    }

    public static <T> T getJSONObject (String text, Class<T> clazz) {
        return support.getJSONObject(text,clazz);
    }
}
