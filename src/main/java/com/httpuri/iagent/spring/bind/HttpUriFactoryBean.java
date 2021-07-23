package com.httpuri.iagent.spring.bind;

import com.httpuri.iagent.HttpUriConf;
import org.springframework.beans.factory.FactoryBean;

/**
 * <b>spring integration use FactoryBean<T></></b>
 * @param <T>
 */
public class HttpUriFactoryBean<T> implements FactoryBean<T> {

    HttpUriConf conf;

    private Class<T> IClass;

    public HttpUriFactoryBean(HttpUriConf conf, Class<T> IClass){
        this.conf = conf;
        this.IClass = IClass;
    }

    public void setIClass(Class<T> IClass) {
        this.IClass = IClass;
    }

    @Override
    public T getObject() throws Exception {
        return conf.getUri(IClass);
    }

    @Override
    public Class<?> getObjectType() {
        return IClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
