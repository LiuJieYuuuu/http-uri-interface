package com.httpuri.iagent.logging.commons;

import com.httpuri.iagent.logging.Logger;

public class JakartaCommonsLoggingImpl implements Logger {

  private final org.apache.commons.logging.Log log;

  public JakartaCommonsLoggingImpl(String clazz) {
    log = org.apache.commons.logging.LogFactory.getLog(clazz);
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
    log.error(s, e);
  }

  @Override
  public void error(String s) {
    log.error(s);
  }

  @Override
  public void trace(String s) {
    log.trace(s);
  }

  @Override
  public void trace(String s, Throwable e) {
    log.trace(s, e);
  }

  @Override
  public void warn(String s) {
    log.warn(s);
  }

  @Override
  public void warn(String s, Throwable e) {
    log.warn(s, e);
  }

  @Override
  public void info(String s) {
    log.info(s);
  }

  @Override
  public void info(String s, Throwable e) {
    log.info(s, e);
  }

  @Override
  public void debug(String s) {
    log.debug(s);
  }

  @Override
  public void debug(String s, Throwable e) {
    log.info(s, e);
  }
}
