package com.httpuri.iagent.annotation;

import com.httpuri.iagent.constant.HttpConstant;
import com.httpuri.iagent.constant.HttpEnum;

import java.lang.annotation.*;


@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface ParamUri {

    String url();

    HttpEnum requestType() default HttpEnum.GET;

    String contentType() default HttpConstant.X_WWW_FORM_URLENCODED;

    int connectionTime() default HttpConstant.CONNECTION_TIME;

    int readTime() default HttpConstant.READ_TIME;

}
