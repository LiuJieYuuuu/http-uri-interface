package com.httpuri.iagent.annotation;

import com.httpuri.iagent.util.HttpUtil;
import com.httpuri.iagent.util.HttpUtil.HttpEnum;

import java.lang.annotation.*;


@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface ParamUri {

    String url();

    HttpEnum requestType() default HttpEnum.GET;

    String contentType() default HttpUtil.X_WWW_FORM_URLENCODED;

}
