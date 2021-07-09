package com.httpuri.iagent;

import com.httpuri.iagent.dao.UserDao;

import java.util.HashMap;

public class Test {


    public static void main(String[] args) {

        HttpUriConf conf = new HttpUriConf("com.httpuri.iagent.dao");
        conf.loadUriPathPackage();
        UserDao uri = conf.getUri(UserDao.class);
        System.out.println(uri.getUserInfo("zhuzai"));


        System.out.println(uri.getUserInfo(new HashMap(){
            {
                put("name","new HashMap");
            }
        }));

    }
}
