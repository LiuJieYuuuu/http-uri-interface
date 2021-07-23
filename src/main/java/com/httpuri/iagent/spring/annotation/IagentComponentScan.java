package com.httpuri.iagent.spring.annotation;

import com.httpuri.iagent.spring.bind.HttpUriImportBeanRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <b>spring IDE scanner package</b>
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
@Import(value = {HttpUriImportBeanRegister.class})
public @interface IagentComponentScan {

    /**
     * value is packages
     * @return
     */
    String[] value();

    String[] basePackages() default "";

}
