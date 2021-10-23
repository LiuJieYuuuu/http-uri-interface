package com.httpuri.iagent;

import com.httpuri.iagent.builder.HttpUriWrapper;
import com.httpuri.iagent.exception.HttpUriArgumentException;
import com.httpuri.iagent.json.JSON;
import com.httpuri.iagent.json.JSONSupport;
import com.httpuri.iagent.json.fastjson.FastJsonSupport;
import com.httpuri.iagent.json.gson.GsonSupport;
import com.httpuri.iagent.json.jackson.JacksonSupport;
import com.httpuri.iagent.logging.LogFactory;
import com.httpuri.iagent.logging.Logger;
import com.httpuri.iagent.logging.commons.JakartaCommonsLoggingImpl;
import com.httpuri.iagent.logging.console.ConsoleImpl;
import com.httpuri.iagent.logging.jdk14.Jdk14LoggingImpl;
import com.httpuri.iagent.logging.log4j.Log4jImpl;
import com.httpuri.iagent.logging.log4j2.Log4j2Impl;
import com.httpuri.iagent.logging.nologging.NoLoggingImpl;
import com.httpuri.iagent.logging.slf4j.Slf4jImpl;
import com.httpuri.iagent.request.HttpExecutor;
import com.httpuri.iagent.scan.ClassPathBeanScanner;
import com.httpuri.iagent.util.LockUtil;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * http uri iagent all Configuration
 */
public class HttpUriConf {
    //scanner package
    private String [] basePackages;
    //log type
    private Class<? extends Logger> logImpl;
    private Logger logger = LogFactory.getLogger(HttpUriConf.class);
    //json type
    private Class<? extends JSONSupport> jsonSupport;
    //interface and proxy object of Map
    private Map<Class,Object> uriMap = new ConcurrentHashMap<>(4);
    //HttpExecutor Map Data
    private Map<Class, HttpExecutor> executorMap = new ConcurrentHashMap<>(4);
    //method and HttpUriWrapper of method Map
    private Map<Method,HttpUriWrapper> uriWrapperMap = new ConcurrentHashMap<>(4);
    //is complete initialize
    private volatile boolean isCompleteInitialize = false;
    //register common data
    private TypeAliasRegister aliasRegister = new TypeAliasRegister();

    {
        aliasRegister.registerAlias("SLF4J", Slf4jImpl.class);
        aliasRegister.registerAlias("COMMONS_LOGGING", JakartaCommonsLoggingImpl.class);
        aliasRegister.registerAlias("LOG4J", Log4jImpl.class);
        aliasRegister.registerAlias("LOG4J2", Log4j2Impl.class);
        aliasRegister.registerAlias("JDK_LOGGING", Jdk14LoggingImpl.class);
        aliasRegister.registerAlias("STDOUT_LOGGING", ConsoleImpl.class);
        aliasRegister.registerAlias("NO_LOGGING", NoLoggingImpl.class);

        aliasRegister.registerAlias("FASTJSON", FastJsonSupport.class);
        aliasRegister.registerAlias("GSON", GsonSupport.class);
        aliasRegister.registerAlias("JACKSON", JacksonSupport.class);
    }

    /**
     * set base packages
     * @param basePackages
     */
    public HttpUriConf(String[] basePackages){
        this.basePackages = basePackages;
        init();
    }

    public HttpUriConf(String basePackages){
        this.basePackages = new String[]{basePackages};
        init();
    }

    public Map<Method, HttpUriWrapper> getUriBeanMap() {
        return uriWrapperMap;
    }

    public void setBasePackages(String [] basePackages) {
        this.basePackages = basePackages;
    }

    public boolean isCompleteInitialize() {
        return isCompleteInitialize;
    }

    /**
     * get all interface class and proxy object
     * @return
     */
    public Map<Class, Object> getUriMap() {
        return uriMap;
    }
    /**
     * get all method of interface class and HttpUriWrapper object
     * @return
     */
    public Map<Method, HttpUriWrapper> getUriWrapperMap() {
        return uriWrapperMap;
    }

    public Map<Class, HttpExecutor> getExecutorMap() {
        return executorMap;
    }

    /**
     * set log type
     * @param logImpl
     */
    public void setLogImpl(Class<? extends Logger> logImpl) {
        this.logImpl = logImpl;
        LogFactory.useLogging(this.logImpl);
        logger = LogFactory.getLogger(this.getClass());
    }
    /**
     * set log type
     * @param logImpl
     */
    public void setLogImpl(String logImpl) {
        this.logImpl = (Class<? extends Logger>) aliasRegister.getAlias(logImpl);
        LogFactory.useLogging(this.logImpl);
        logger = LogFactory.getLogger(this.getClass());
    }

    /**
     * set json type
     * @param jsonSupport
     */
    public void setJsonSupport(Class<? extends JSONSupport> jsonSupport) {
        this.jsonSupport = jsonSupport;
        JSON.useJson(this.jsonSupport);
    }

    /**
     * set json type
     * @param jsonSupport
     */
    public void setJsonSupport(String jsonSupport) {
        this.jsonSupport = (Class<? extends JSONSupport>) aliasRegister.getAlias(jsonSupport);
        JSON.useJson(this.jsonSupport);
    }
    /**
     * initialize package and scanner package
     */
    public void init(){
        if(this.basePackages != null){
            LockUtil.getLock();
            if(isCompleteInitialize) return;
            try {
                ClassPathBeanScanner scanner = new ClassPathBeanScanner(this);
                scanner.scannerPackages(this.uriMap, this.uriWrapperMap, this.basePackages);
                isCompleteInitialize = true;
            } catch (Throwable t) {
                logger.error("Error Create Http Uri Interface Proxy");
            } finally {
                LockUtil.unlock();
            }
        }else{
            logger.error("base packages is null");
            throw new HttpUriArgumentException("base packages is null");
        }

    }

    /**
     * get proxy object by interface class
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T getUri(Class<T> cls){
        return (T) this.uriMap.get(cls);
    }

}
