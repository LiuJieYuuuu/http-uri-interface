package com.httpuri.iagent.logging;

public interface Logger {

    boolean isDebugEnabled();

    boolean isInfoEnabled();

    void error(String s, Throwable e);

    void error(String s);

    void trace(String s);

    void trace(String s, Throwable e);

    void warn(String s);

    void warn(String s, Throwable e);

    void info(String s);

    void info(String s, Throwable e);

    void debug(String s);

    void debug(String s, Throwable e);

}
