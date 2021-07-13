package com.httpuri.iagent.dao;

import com.httpuri.iagent.annotation.ParamKey;
import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.annotation.UrlScanner;
import com.httpuri.iagent.constant.HttpConstant;
import com.httpuri.iagent.constant.HttpEnum;

import java.util.Map;

@UrlScanner(basePackages = "com.httpuri.iagent.dao")
public interface UserDao {

    @ParamUri(url = "http://localhost:8080/mvc/getGetController",HttpConstant = HttpEnum.GET)
    Map getUserInfo(@ParamKey(key = "name") String name);


    @ParamUri(url = "http://localhost:8080/mvc/getPostController",HttpConstant = HttpEnum.POST)
    Map getUserPostInfo(@ParamKey(key = "name") String name);

    @ParamUri(url = "http://localhost:8080/mvc/getPostControllerJson",HttpConstant = HttpEnum.POST,contentType = HttpConstant.APPLICATION_JSON_UTF8)
    Map getUserInfo(Map param);

}
