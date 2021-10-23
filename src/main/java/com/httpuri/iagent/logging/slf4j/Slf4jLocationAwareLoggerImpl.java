package com.httpuri.iagent.logging.slf4j;

import com.httpuri.iagent.logging.LogFactory;
import com.httpuri.iagent.logging.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.spi.LocationAwareLogger;

class Slf4jLocationAwareLoggerImpl implements Logger {

  private static final Marker MARKER = MarkerFactory.getMarker(LogFactory.MARKER);

  private static final String FQCN = Slf4jImpl.class.getName();

  private final LocationAwareLogger logger;

  Slf4jLocationAwareLoggerImpl(LocationAwareLogger logger) {
    this.logger = logger;
  }

  @Override
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  @Override
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  @Override
  public void error(String s, Throwable e) {
    logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, e);
  }

  @Override
  public void error(String s) {
    logger.log(MARKER, FQCN, LocationAwareLogger.ERROR_INT, s, null, null);
  }

  @Override
  public void trace(String s) {
    logger.log(MARKER, FQCN, LocationAwareLogger.TRACE_INT, s, null, null);
  }

  @Override
  public void trace(String s, Throwable e) {
    logger.log(MARKER, FQCN, LocationAwareLogger.TRACE_INT, s, null, e);
  }

  @Override
  public void warn(String s) {
    logger.log(MARKER, FQCN, LocationAwareLogger.WARN_INT, s, null, null);
  }

  @Override
  public void warn(String s, Throwable e) {
    logger.log(MARKER, FQCN, LocationAwareLogger.WARN_INT, s, null, e);
  }

  @Override
  public void info(String s) {
    logger.log(MARKER, FQCN, LocationAwareLogger.INFO_INT, s, null, null);
  }

  @Override
  public void info(String s, Throwable e) {
    logger.log(MARKER, FQCN, LocationAwareLogger.INFO_INT, s, null, e);
  }

  @Override
  public void debug(String s) {
    logger.log(MARKER, FQCN, LocationAwareLogger.DEBUG_INT, s, null, null);
  }

  @Override
  public void debug(String s, Throwable e) {
    logger.log(MARKER, FQCN, LocationAwareLogger.DEBUG_INT, s, null, e);
  }
}
