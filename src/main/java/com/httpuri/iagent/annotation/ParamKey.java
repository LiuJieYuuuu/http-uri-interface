package com.httpuri.iagent.annotation;

import java.lang.annotation.*;

/**
 * <p>parameter annotation</p>
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER})
@Documented
public @interface ParamKey {

    /**
     * value is key
     * @return
     */
    String value();

}
