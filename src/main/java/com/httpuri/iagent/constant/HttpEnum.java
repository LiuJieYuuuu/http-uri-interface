package com.httpuri.iagent.constant;

/**
 * <p>enum request method type</p>
 */
public enum HttpEnum {
    GET("GET"),POST("POST"),PUT("PUT");

    private String type;

    HttpEnum(String requestMethodType){
        this.type = requestMethodType;
    }

    public String getRequestMethodType(){
        return type;
    }

}
