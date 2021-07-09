package com.httpuri.iagent.util;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    private static final int CONNECTION_TIME = 2000;

    private static final int READ_TIME = 10000;

    public static final String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    public static final String APPLICATION_JSON = "application/json";

    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=utf-8";

    public static enum HttpEnum{
        GET,POST
    }

    /**
     * 默认参数请求
     * @param url
     * @return
     */
    public static String sendHttp(String url){
        return sendHttp(url,null, HttpEnum.GET,X_WWW_FORM_URLENCODED);
    }

    /**
     * 采用默认配置
     * @param url
     * @param param
     * @return
     */
    public static String sendHttp(String url, Map param){
        return sendHttp(url,param,HttpEnum.GET,X_WWW_FORM_URLENCODED);
    }

    /**
     * 采用默认配置
     * @param url
     * @param param
     * @param type
     * @return
     */
    public static String sendHttp(String url, Map param, HttpEnum type){
        return sendHttp(url,param,type,X_WWW_FORM_URLENCODED,CONNECTION_TIME,READ_TIME);
    }

    /**
     * 采用默认配置
     * @param url
     * @param param
     * @param contentType
     * @return
     */
    public static String sendHttp(String url, Map param, String contentType){
        return sendHttp(url,param,HttpEnum.POST,contentType,CONNECTION_TIME,READ_TIME);
    }

    /**
     * 采用默认时间请求
     * @param url
     * @param param
     * @param type
     * @param contentType
     * @return
     */
    public static String sendHttp(String url, Map param, HttpEnum type,
                                  String contentType){
        return sendHttp(url,param,type,contentType,CONNECTION_TIME,READ_TIME);
    }

    /**
     * 发送HTTP请求
     * @param url
     * @param param
     * @param type
     * @param contentType
     * @param connectionTime
     * @param readTime
     * @return
     */
    public static String sendHttp(String url, Map param, HttpEnum type,
                                  String contentType,int connectionTime,int readTime){
        StringBuilder result = new StringBuilder();
        try{

            String params = null;
            if(param != null && !param.isEmpty()){
                params = changeParam(param);
                if(type.equals(HttpEnum.GET))
                    url += "?" + URLEncoder.encode(params,"UTF-8");
            }
            System.out.println("url:" + url);
            URL uri = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
            if(type.equals(HttpEnum.GET)){
                urlConnection.setRequestMethod("GET");
                setHttpUrlConnectionProperty(urlConnection,contentType,connectionTime,readTime);
            }else if (type.equals(HttpEnum.POST)){
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);
                setHttpUrlConnectionProperty(urlConnection,contentType,connectionTime,readTime);
                if (params != null){
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                    if(contentType.equals(APPLICATION_JSON) || contentType.equals(APPLICATION_JSON_UTF8))
                        writer.write(JSON.toJSONString(param));
                    else
                        writer.write(params);
                    writer.close();
                }
            }

            int responseCode = urlConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String temp = null;
                while ((temp = br.readLine()) != null){
                    result.append(temp);
                }
            }

        }catch (Throwable e){
            e.printStackTrace();
        }

        return result.toString();
    }

    private static void setHttpUrlConnectionProperty(HttpURLConnection urlConnection,String contentType,
                                                     int connectionTime,int readTime) throws IOException {

        if(contentType != null && !"".equals(contentType)){
            urlConnection.setRequestProperty("Content-Type",contentType);
        }else{
            urlConnection.setRequestProperty("Content-Type",X_WWW_FORM_URLENCODED);

        }

        urlConnection.setConnectTimeout(connectionTime);
        urlConnection.setReadTimeout(readTime);

        urlConnection.connect();
    }

    /**
     * 转换Map的参数成GET参数形式，key=value&key=value
     * @param param
     * @return
     */
    private static String changeParam(Map<?,?> param){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : param.entrySet()) {
            sb.append(entry.getKey() == null ? "" : entry.getKey().toString());
            sb.append("=");
            sb.append(entry.getValue() == null ? "" : entry.getValue().toString());
            sb.append("&");
        }
        if(sb.length() != 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
