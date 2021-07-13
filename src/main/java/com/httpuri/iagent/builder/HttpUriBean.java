package com.httpuri.iagent.builder;

import com.httpuri.iagent.constant.HttpConstant;
import com.httpuri.iagent.constant.HttpEnum;

import java.util.Map;

/**
 * 使用建造者模式装载请求数据Bean
 */
public class HttpUriBean {

    String url;

    HttpEnum requestType;

    String contentType;

    Map params;

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

    public static class HttpUriBeanBuidler{

        String url;

        HttpEnum requestType = HttpEnum.GET;

        String contentType = HttpConstant.X_WWW_FORM_URLENCODED;

        Map params = null;

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

        public int getConnectionTime() {
            return connectionTime;
        }

        public int getReadTime() {
            return readTime;
        }

        public HttpUriBeanBuidler(){
            super();
        }

        public HttpUriBeanBuidler(String url){
            super();
            this.url = url;
        }

        public HttpUriBeanBuidler url(String url){
            this.url = url;
            return this;
        }

        public HttpUriBeanBuidler requestType(HttpEnum requestType){
            this.requestType = requestType;
            return this;
        }

        public HttpUriBeanBuidler contentType(String contentType){
            this.contentType = contentType;
            return this;
        }

        public HttpUriBeanBuidler params(Map params){
            this.params = params;
            return this;
        }

        public HttpUriBeanBuidler connectionTime(int connectionTime){
            this.connectionTime = connectionTime;
            return this;
        }

        public HttpUriBeanBuidler readTime(int readTime){
            this.readTime = readTime;
            return this;
        }

        public HttpUriBean build(){
            return new HttpUriBean(this);
        }

    }

    private HttpUriBean (HttpUriBeanBuidler buidler){
        super();
        this.url = buidler.getUrl();
        this.connectionTime = buidler.getConnectionTime();
        this.contentType = buidler.getContentType();
        this.params = buidler.getParams();
        this.readTime = buidler.getReadTime();
        this.requestType = buidler.getRequestType();
    }
}
