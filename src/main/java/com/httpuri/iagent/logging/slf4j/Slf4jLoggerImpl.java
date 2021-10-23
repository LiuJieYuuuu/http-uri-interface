package com.httpuri.iagent.logging.slf4j;


import com.httpuri.iagent.logging.Logger;

class Slf4jLoggerImpl implements Logger {

  private final org.slf4j.Logger log;

  public Slf4jLoggerImpl(org.slf4j.Logger logger) {
    log = logger;
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
    log.debug(s, e);
  }
}
