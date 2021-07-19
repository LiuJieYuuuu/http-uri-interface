package com.httpuri.iagent.builder;

import com.httpuri.iagent.constant.HttpConstant;
import com.httpuri.iagent.constant.HttpEnum;

import java.io.Serializable;
import java.util.Map;

/**
 * 使用建造者模式装载请求数据Bean
 */
public class HttpUriBean implements Cloneable,Serializable {

    String url;

    HttpEnum requestType;

    String contentType;

    Map params;

    Map<String,Integer> pathParams;

    int connectionTime;

    int readTime;

    private HttpUriBean(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpEnum getRequestType() {
        return requestType;
    }

    public void setRequestType(HttpEnum requestType) {
        this.requestType = requestType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public Map<String, Integer> getPathParams() {
        return pathParams;
    }

    public void setPathParams(Map<String, Integer> pathParams) {
        this.pathParams = pathParams;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    @Override
    public String toString() {
        return "HttpUriBean{" +
                "url='" + url + '\'' +
                ", requestType=" + requestType +
                ", contentType='" + contentType + '\'' +
                ", params=" + params +
                ", connectionTime=" + connectionTime +
                ", readTime=" + readTime +
                '}';
    }

    @Override
    public HttpUriBean clone() {
        try {
            return (HttpUriBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class HttpUriBeanBuilder{

        String url;

        HttpEnum requestType = HttpEnum.GET;

        String contentType = HttpConstant.X_WWW_FORM_URLENCODED;

        Map params = null;

        Map<String,Integer> pathParams = null;

        int connectionTime = HttpConstant.CONNECTION_TIME;

        int readTime = HttpConstant.READ_TIME;

        public String getUrl() {
            return url;
        }

        public HttpEnum getRequestType() {
            return requestType;
        }

        public String getContentType() {
            return contentType;
        }

        public Map getParams() {
            return params;
        }

        public Map<String, Integer> getPathParams() {
            return pathParams;
        }

        public int getConnectionTime() {
            return connectionTime;
        }

        public int getReadTime() {
            return readTime;
        }

        public HttpUriBeanBuilder(){
            super();
        }

        public static HttpUriBeanBuilder builder(){
            return new HttpUriBeanBuilder();
        }

        public HttpUriBeanBuilder(String url){
            super();
            this.url = url;
        }

        public HttpUriBeanBuilder url(String url){
            this.url = url;
            return this;
        }

        public HttpUriBeanBuilder requestType(HttpEnum requestType){
            this.requestType = requestType;
            return this;
        }

        public HttpUriBeanBuilder contentType(String contentType){
            this.contentType = contentType;
            return this;
        }

        public HttpUriBeanBuilder params(Map params){
            this.params = params;
            return this;
        }

        public HttpUriBeanBuilder pathParas(Map<String,Integer> pathParams){
            this.pathParams = pathParams;
            return this;
        }

        public HttpUriBeanBuilder connectionTime(int connectionTime){
            this.connectionTime = connectionTime;
            return this;
        }

        public HttpUriBeanBuilder readTime(int readTime){
            this.readTime = readTime;
            return this;
        }

        public HttpUriBean build(){
            return new HttpUriBean(this);
        }

    }

    private HttpUriBean (HttpUriBeanBuilder buidler){
        super();
        this.url = buidler.getUrl();
        this.connectionTime = buidler.getConnectionTime();
        this.contentType = buidler.getContentType();
        this.params = buidler.getParams();
        this.pathParams = buidler.getPathParams();
        this.readTime = buidler.getReadTime();
        this.requestType = buidler.getRequestType();
    }
}
