package com.httpuri.iagent.spring;

import com.httpuri.iagent.HttpUriConf;
import com.httpuri.iagent.spring.bind.HttpUriFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.Map;

/**
 * <b>http uri BeanDefinitionRegistryPostProcessor
 * HttpUriFactoryBeans dependency in Spring IOC</b>
 */
public class HttpUriConfBean implements BeanDefinitionRegistryPostProcessor,InitializingBean,DisposableBean {

    private HttpUriConf conf;

    private String[] basePackages;

    public HttpUriConfBean (){
        super();
    };

    public HttpUriConfBean(String[] basePackages){
        this.basePackages = basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = new String[] {basePackages};
    }
    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Map<Class, Object> uriMap = conf.getUriMap();
        for (Map.Entry<Class,Object> entry : uriMap.entrySet()){
            Class cls = entry.getKey();
            //create bean definition by class
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(cls).getBeanDefinition();
            //set args by constructor
            beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0,conf);
            beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1,cls);
            //set bean class
            beanDefinition.setBeanClass(HttpUriFactoryBean.class);
            //dependency in Spring IOC
            registry.registerBeanDefinition(cls.getName(),beanDefinition);
        }
    }

    /**
     * Spring Bean Initialize
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if(conf == null)
            conf = new HttpUriConf(basePackages);
        else if (!conf.isCompleteInitialize())
            conf.init();

    }

    /**
     * Spring Bean destory
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        conf = null;
    }
}
