package com.httpuri.iagent.logging;

import com.httpuri.iagent.exception.LogException;
import com.httpuri.iagent.logging.commons.JakartaCommonsLoggingImpl;
import com.httpuri.iagent.logging.jdk14.Jdk14LoggingImpl;
import com.httpuri.iagent.logging.log4j.Log4jImpl;
import com.httpuri.iagent.logging.log4j2.Log4j2Impl;
import com.httpuri.iagent.logging.nologging.NoLoggingImpl;
import com.httpuri.iagent.logging.slf4j.Slf4jImpl;
import com.httpuri.iagent.logging.console.ConsoleImpl;

import java.lang.reflect.Constructor;

public final class LogFactory {

    public static final String MARKER = "IAGENT";

    private static Constructor<? extends Logger> constructor = null;

    static {
        useSlf4jLogging();
        useCommonsLogging();
        useLog4JLogging();
        useLog4J2Logging();
        useJdkLogging();
        useConsoleLogging();
        useNoLogging();
    }

    private static void useSlf4jLogging(){
        useLogging(Slf4jImpl.class);
    }
    public static synchronized void useCommonsLogging() {
        useLogging(JakartaCommonsLoggingImpl.class);
    }

    public static synchronized void useLog4JLogging() {
        useLogging(Log4jImpl.class);
    }

    public static synchronized void useLog4J2Logging() {
        useLogging(Log4j2Impl.class);
    }

    public static synchronized void useJdkLogging() {
        useLogging(Jdk14LoggingImpl.class);
    }

    public static synchronized void useConsoleLogging() {
        useLogging(ConsoleImpl.class);
    }

    public static synchronized void useNoLogging() {
        useLogging(NoLoggingImpl.class);
    }

    public static Logger getLogger(String clazzName) {
        try {
            return constructor.newInstance(clazzName);
        } catch (Throwable t) {
            throw new LogException("Error creating logger for logger " + clazzName + ".  Cause: " + t, t);
        }
    }

    public static Logger getLogger(Class clazz) {
        try {
            return constructor.newInstance(clazz.getName());
        } catch (Throwable t) {
            throw new LogException("Error creating logger for logger " + clazz.getName() + ".  Cause: " + t, t);
        }
    }

    public static void useLogging(Class<? extends Logger> clazz){
        try {
            Constructor<? extends Logger> candidate = clazz.getConstructor(String.class);
            Logger logger = candidate.newInstance(LogFactory.class.getName());
            if (constructor == null)
                constructor = candidate;
            if (logger.isDebugEnabled()) {
                logger.debug("Logging initialized using '" + clazz + "' adapter.");
            }
        } catch (Throwable e) {
            //adapter log frame work
        }
    }
}
