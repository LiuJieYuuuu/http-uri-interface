package com.httpuri.iagent.logging.log4j;

import com.httpuri.iagent.logging.Logger;
import org.apache.log4j.Level;

public class Log4jImpl implements Logger {

  private static final String FQCN = Log4jImpl.class.getName();

  private final org.apache.log4j.Logger log;

  public Log4jImpl(String clazz) {
    log = org.apache.log4j.Logger.getLogger(clazz);
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  @Override
  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  @Override
  public void error(String s, Throwable e) {
    log.log(FQCN, Level.ERROR, s, e);
  }

  @Override
  public void error(String s) {
    log.log(FQCN, Level.ERROR, s, null);
  }

  @Override
  public void trace(String s) {
    log.log(FQCN, Level.TRACE, s, null);
  }

  @Override
  public void trace(String s, Throwable e) {
    log.log(FQCN, Level.TRACE, s, e);
  }

  @Override
  public void warn(String s) {
    log.log(FQCN, Level.WARN, s, null);
  }

  @Override
  public void warn(String s, Throwable e) {
    log.log(FQCN, Level.WARN, s, e);
  }

  @Override
  public void info(String s) {
    log.log(FQCN, Level.INFO, s, null);
  }

  @Override
  public void info(String s, Throwable e) {
    log.log(FQCN, Level.INFO, s, e);
  }

  @Override
  public void debug(String s) {
    log.log(FQCN, Level.DEBUG, s, null);
  }

  @Override
  public void debug(String s, Throwable e) {
    log.log(FQCN, Level.DEBUG, s, e);
  }
}
