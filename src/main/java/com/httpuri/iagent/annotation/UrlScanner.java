package com.httpuri.iagent.annotation;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
public @interface UrlScanner {

    String [] basePackages();

}
