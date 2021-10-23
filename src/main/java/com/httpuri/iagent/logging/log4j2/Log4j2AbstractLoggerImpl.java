package com.httpuri.iagent.logging.log4j2;

import com.httpuri.iagent.logging.LogFactory;
import com.httpuri.iagent.logging.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

public class Log4j2AbstractLoggerImpl implements Logger {

  private static final Marker MARKER = MarkerManager.getMarker(LogFactory.MARKER);

  private static final String FQCN = Log4j2Impl.class.getName();

  private final ExtendedLoggerWrapper log;

  public Log4j2AbstractLoggerImpl(AbstractLogger abstractLogger) {
    log = new ExtendedLoggerWrapper(abstractLogger, abstractLogger.getName(), abstractLogger.getMessageFactory());
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
    log.logIfEnabled(FQCN, Level.ERROR, MARKER, (Message) new SimpleMessage(s), e);
  }

  @Override
  public void error(String s) {
    log.logIfEnabled(FQCN, Level.ERROR, MARKER, (Message) new SimpleMessage(s), null);
  }

  @Override
  public void trace(String s) {
    log.logIfEnabled(FQCN, Level.TRACE, MARKER, (Message) new SimpleMessage(s), null);
  }

  @Override
  public void trace(String s, Throwable e) {
    log.logIfEnabled(FQCN, Level.TRACE, MARKER, (Message) new SimpleMessage(s), e);
  }

  @Override
  public void warn(String s) {
    log.logIfEnabled(FQCN, Level.WARN, MARKER, (Message) new SimpleMessage(s), null);
  }

  @Override
  public void warn(String s, Throwable e) {
    log.logIfEnabled(FQCN, Level.WARN, MARKER, (Message) new SimpleMessage(s), e);
  }

  @Override
  public void info(String s) {
    log.logIfEnabled(FQCN, Level.INFO, MARKER, (Message) new SimpleMessage(s), null);
  }

  @Override
  public void info(String s, Throwable e) {
    log.logIfEnabled(FQCN, Level.INFO, MARKER, (Message) new SimpleMessage(s), e);
  }

  @Override
  public void debug(String s) {
    log.logIfEnabled(FQCN, Level.DEBUG, MARKER, (Message) new SimpleMessage(s), null);
  }

  @Override
  public void debug(String s, Throwable e) {
    log.logIfEnabled(FQCN, Level.DEBUG, MARKER, (Message) new SimpleMessage(s), e);
  }
}
