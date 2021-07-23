package com.httpuri.iagent.spring.bind;

import com.httpuri.iagent.spring.HttpUriConfBean;
import com.httpuri.iagent.spring.annotation.IagentComponentScan;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * <p>http uri dependency in Spring IOC by Spring @Import</p>
 */
public class HttpUriImportBeanRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes =
                importingClassMetadata.getAnnotationAttributes(IagentComponentScan.class.getName());
        String [] packages = (String[]) annotationAttributes.get("value");
        if (packages == null || packages.length == 0){
            packages = (String[]) annotationAttributes.get("basePackages");
        }
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(HttpUriConfBean.class).getBeanDefinition();
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0,packages);
        registry.registerBeanDefinition(HttpUriConfBean.class.getName(),beanDefinition);
    }

}
