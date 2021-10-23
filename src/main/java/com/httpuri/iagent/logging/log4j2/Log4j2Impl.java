package com.httpuri.iagent.logging.log4j2;

import com.httpuri.iagent.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.AbstractLogger;

public class Log4j2Impl implements Logger {

  private final Logger log;

  public Log4j2Impl(String clazz) {
    org.apache.logging.log4j.Logger logger = LogManager.getLogger(clazz);

    if (logger instanceof AbstractLogger) {
      log = new Log4j2AbstractLoggerImpl((AbstractLogger) logger);
    } else {
      log = new Log4j2LoggerImpl(logger);
    }
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
