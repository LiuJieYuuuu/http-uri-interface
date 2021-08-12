package com.httpuri.iagent.builder;

import com.httpuri.iagent.request.HttpExecutor;

import java.io.Serializable;

public class HttpUriWrapper implements Cloneable,Serializable {

    HttpUriBean bean;

    HttpExecutor executor;

    public HttpUriWrapper(){
        super();
    }
    public HttpUriWrapper(HttpUriBean bean){
        super();
        this.bean = bean;
    }

    public HttpUriWrapper(HttpUriBean bean,HttpExecutor executor){
        super();
        this.bean = bean;
        this.executor = executor;
    }

    public HttpUriWrapper(HttpExecutor executor){
        super();
        this.executor = executor;
    }

    public HttpUriBean getBean() {
        return bean;
    }

    public void setBean(HttpUriBean bean) {
        this.bean = bean;
    }

    public HttpExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(HttpExecutor executor) {
        this.executor = executor;
    }

    @Override
    public String toString() {
        return "HttpUriWrapper{" +
                "bean=" + bean +
                ", executor=" + executor +
                '}';
    }

    @Override
    public HttpUriWrapper clone() {
        try {
            HttpUriWrapper clone = (HttpUriWrapper) super.clone();
            clone.setBean(this.bean.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
