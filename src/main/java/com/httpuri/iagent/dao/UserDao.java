package com.httpuri.iagent.dao;

import com.httpuri.iagent.annotation.ParamKey;
import com.httpuri.iagent.annotation.ParamUri;
import com.httpuri.iagent.annotation.UrlScanner;
import com.httpuri.iagent.util.HttpUtil;
import com.httpuri.iagent.util.HttpUtil.HttpEnum;

import java.util.List;
import java.util.Map;

@UrlScanner(basePackages = "com.httpuri.iagent.dao")
public interface UserDao {

    @ParamUri(url = "http://localhost:8080/mvc/getGetController",requestType = HttpEnum.GET)
    Map getUserInfo(@ParamKey(key = "name") String name);

    @ParamUri(url = "http://localhost:8080/mvc/getPostControllerJson",requestType = HttpEnum.GET,contentType = HttpUtil.APPLICATION_JSON_UTF8)
    Map getUserInfo(Map param);

}
