package com.httpuri.iagent.logging.slf4j;

import com.httpuri.iagent.logging.Logger;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

public class Slf4jImpl implements Logger {

  private Logger log;

  public Slf4jImpl (String clazz) {

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(clazz);

    if (logger instanceof LocationAwareLogger) {
      try {
        // check for slf4j >= 1.6 method signature
        logger.getClass().getMethod("log", Marker.class, String.class, int.class, String.class, Object[].class, Throwable.class);
        this.log = new Slf4jLocationAwareLoggerImpl((LocationAwareLogger) logger);
        return;
      } catch (SecurityException | NoSuchMethodException e) {
        // fail-back to Slf4jLoggerImpl
      }
    }

    // Logger is not LocationAwareLogger or slf4j version < 1.6
    log = new Slf4jLoggerImpl(logger);
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
