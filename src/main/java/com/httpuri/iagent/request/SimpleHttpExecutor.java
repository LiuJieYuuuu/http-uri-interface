package com.httpuri.iagent.request;

import com.alibaba.fastjson.JSON;
import com.httpuri.iagent.builder.HttpUriBean;
import com.httpuri.iagent.constant.HttpConstant;
import com.httpuri.iagent.constant.HttpEnum;
import com.httpuri.iagent.exception.HttpUriArgumentException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * <b> the Simple Common Http Request Executor</b>
 */
public class SimpleHttpExecutor extends AbstractHttpExecutor {

    /**
     * <b>specific send to http request url</b>
     * @param bean
     * @param args
     * @return
     */
    public String sendHttp(HttpUriBean bean,Object[] args){
        if (bean == null)
            throw new HttpUriArgumentException("args is null");
        return this.sendHttp(super.handlePathKey(bean,args),bean.getParams(), bean.getRequestType(),bean.getContentType(),
                bean.getConnectionTime(),bean.getReadTime());
    }

    /**
     * <b>default params</b>
     * @param url
     * @return
     */
    public String sendHttp(String url){
        return this.sendHttp(url,null, HttpEnum.GET,HttpConstant.X_WWW_FORM_URLENCODED);
    }

    /**
     * <b>default params</b>
     * @param url
     * @param param
     * @return
     */
    public String sendHttp(String url, Map param){
        return this.sendHttp(url,param,HttpEnum.GET,HttpConstant.X_WWW_FORM_URLENCODED);
    }

    /**
     * <b>default params</b>
     * @param url
     * @param param
     * @param type
     * @return
     */
    public String sendHttp(String url, Map param, HttpEnum type){
        return this.sendHttp(url,param,type,HttpConstant.X_WWW_FORM_URLENCODED,HttpConstant.CONNECTION_TIME,HttpConstant.READ_TIME);
    }

    /**
     * <b>default params</b>
     * @param url
     * @param param
     * @param contentType
     * @return
     */
    public String sendHttp(String url, Map param, String contentType){
        return this.sendHttp(url,param,HttpEnum.POST,contentType,HttpConstant.CONNECTION_TIME,HttpConstant.READ_TIME);
    }

    /**
     * <b>default params</b>
     * @param url
     * @param param
     * @param type
     * @param contentType
     * @return
     */
    public String sendHttp(String url, Map param, HttpEnum type,
                                  String contentType){
        return this.sendHttp(url,param,type,contentType,HttpConstant.CONNECTION_TIME,HttpConstant.READ_TIME);
    }

    /**
     * <b>send HTTP Request Url</b>
     * @param url
     * @param param
     * @param type
     * @param contentType
     * @param connectionTime
     * @param readTime
     * @return
     */
    public String sendHttp(String url, Map param, HttpEnum type,
                                  String contentType,int connectionTime,int readTime){
        StringBuilder result = new StringBuilder();
        try{

            String params = null;
            if(param != null && !param.isEmpty()){
                params = changeParam(param);
                if(type.equals(HttpEnum.GET))
                    url += "?" + params;
            }
            URL uri = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
            if(type.equals(HttpEnum.GET)){
                urlConnection.setRequestMethod(type.getRequestMethodType());
                setHttpUrlConnectionProperty(urlConnection,contentType,connectionTime,readTime);
            }else if (type.equals(HttpEnum.POST) || type.equals(HttpEnum.PUT)){
                urlConnection.setRequestMethod(type.getRequestMethodType());
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);
                setHttpUrlConnectionProperty(urlConnection,contentType,connectionTime,readTime);
                if (params != null){
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                    if(contentType.equals(HttpConstant.APPLICATION_JSON) || contentType.equals(HttpConstant.APPLICATION_JSON_UTF8))
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

    /**
     * <p>filled with params to HttpConnection</p>
     * @param urlConnection
     * @param contentType
     * @param connectionTime
     * @param readTime
     * @throws IOException
     */
    private void setHttpUrlConnectionProperty(HttpURLConnection urlConnection,String contentType,
                                                     int connectionTime,int readTime) throws IOException {

        if(contentType != null && !"".equals(contentType)){
            urlConnection.setRequestProperty("Content-Type",contentType);
        }else{
            urlConnection.setRequestProperty("Content-Type",HttpConstant.X_WWW_FORM_URLENCODED);

        }

        urlConnection.setConnectTimeout(connectionTime);
        urlConnection.setReadTimeout(readTime);

        urlConnection.connect();
    }

    /**
     * <b>change params of Map to get Http Request,
     * like key=value&key2=value2</b>
     * @param param
     * @return
     */
    private String changeParam(Map<?,?> param) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : param.entrySet()) {
            sb.append(entry.getKey() == null ? "" : entry.getKey().toString());
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue().toString(),"UTF-8"));
            sb.append("&");
        }
        if(sb.length() != 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
