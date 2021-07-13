package com.httpuri.iagent.annotation;

import com.httpuri.iagent.constant.HttpConstant;
import com.httpuri.iagent.constant.HttpEnum;

import java.lang.annotation.*;


@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface ParamUri {

    String url();

    HttpEnum HttpConstant() default HttpEnum.GET;

    String contentType() default HttpConstant.X_WWW_FORM_URLENCODED;

}
