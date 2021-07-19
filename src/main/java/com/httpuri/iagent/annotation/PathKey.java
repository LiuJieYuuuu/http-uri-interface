package com.httpuri.iagent.annotation;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER})
@Documented
public @interface PathKey {

    String key();

}
