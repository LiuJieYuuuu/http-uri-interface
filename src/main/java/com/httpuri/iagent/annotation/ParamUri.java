package com.httpuri.iagent.annotation;

import com.httpuri.iagent.constant.HttpConstant;
import com.httpuri.iagent.constant.HttpEnum;
import com.httpuri.iagent.request.HttpExecutor;
import com.httpuri.iagent.request.SimpleHttpExecutor;

import java.lang.annotation.*;

/**
 * <p>create uri</p>
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface ParamUri {

    /**
     * value is url
     * @return
     */
    String value() default "";

    /**
     * url is value
     * @return
     */
    String url() default "";

    HttpEnum requestType() default HttpEnum.GET;

    String contentType() default HttpConstant.X_WWW_FORM_URLENCODED;

    int connectionTime() default HttpConstant.CONNECTION_TIME;

    int readTime() default HttpConstant.READ_TIME;

    /**
     * default use to SimpleHttpExecutor.java
     * @return
     */
    Class<? extends HttpExecutor> httpExecutor() default SimpleHttpExecutor.class;

}
