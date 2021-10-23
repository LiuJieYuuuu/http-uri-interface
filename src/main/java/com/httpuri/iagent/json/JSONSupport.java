package com.httpuri.iagent.json;

import java.util.*;

public interface JSONSupport  {

    //JSON数组方法
    <T> List<T> getJSONArray(String text, Class<T> clazz);

    //JSON对象方法
    <T> T getJSONObject(String text, Class<T> clazz);

}
