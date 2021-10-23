package com.httpuri.iagent.logging.log4j2;

import com.httpuri.iagent.logging.LogFactory;
import com.httpuri.iagent.logging.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Log4j2LoggerImpl implements Logger {

  private static final Marker MARKER = MarkerManager.getMarker(LogFactory.MARKER);

  private final org.apache.logging.log4j.Logger log;

  public Log4j2LoggerImpl(org.apache.logging.log4j.Logger logger) {
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
    log.error(MARKER, s, e);
  }

  @Override
  public void error(String s) {
    log.error(MARKER, s);
  }

  @Override
  public void trace(String s) {
    log.trace(MARKER, s);
  }

  @Override
  public void trace(String s, Throwable e) {
    log.trace(MARKER, s, e);
  }

  @Override
  public void warn(String s) {
    log.warn(MARKER, s);
  }

  @Override
  public void warn(String s, Throwable e) {
    log.warn(MARKER, s, e);
  }

  @Override
  public void info(String s) {
    log.info(MARKER, s);
  }

  @Override
  public void info(String s, Throwable e) {
    log.info(MARKER, s, e);
  }

  @Override
  public void debug(String s) {
    log.debug(MARKER, s);
  }

  @Override
  public void debug(String s, Throwable e) {
    log.debug(MARKER, s, e);

  }
}
