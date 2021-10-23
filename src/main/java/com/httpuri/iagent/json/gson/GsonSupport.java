package com.httpuri.iagent.json.gson;

import com.httpuri.iagent.json.JSONSupport;

import java.util.List;

/**
 * gson adapter json framework
 */
public class GsonSupport implements JSONSupport {

    private com.google.gson.Gson gson;

    public GsonSupport () throws Exception {
        super();
        Class<?> aClass = Class.forName("com.google.gson.Gson");
        gson = (com.google.gson.Gson) aClass.newInstance();
    }

    @Override
    public <T> List<T> getJSONArray(String text, Class<T> clazz) {
        return gson.fromJson(text, List.class);
    }

    @Override
    public <T> T getJSONObject(String text, Class<T> clazz) {
        return gson.fromJson(text, clazz);
    }
}
