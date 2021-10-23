package com.httpuri.iagent.logging.jdk14;

import java.util.logging.Level;
import com.httpuri.iagent.logging.Logger;

public class Jdk14LoggingImpl implements Logger {

  private final java.util.logging.Logger log;

  public Jdk14LoggingImpl(String clazz) {
    log = java.util.logging.Logger.getLogger(clazz);
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isLoggable(Level.FINE);
  }

  @Override
  public boolean isInfoEnabled() {
    return log.isLoggable(Level.INFO);
  }

  @Override
  public void error(String s, Throwable e) {
    log.log(Level.SEVERE, s, e);
  }

  @Override
  public void error(String s) {
    log.log(Level.SEVERE, s);
  }

  @Override
  public void trace(String s) {
    log.log(Level.FINER, s);
  }

  @Override
  public void trace(String s, Throwable e) {
    log.log(Level.FINER, s, e);
  }

  @Override
  public void warn(String s) {
    log.log(Level.WARNING, s);
  }

  @Override
  public void warn(String s, Throwable e) {
    log.log(Level.WARNING, s, e);
  }

  @Override
  public void info(String s) {
    log.log(Level.INFO, s);
  }

  @Override
  public void info(String s, Throwable e) {
    log.log(Level.INFO, s, e);
  }

  @Override
  public void debug(String s) {
    log.log(Level.ALL, s);
  }

  @Override
  public void debug(String s, Throwable e) {
    log.log(Level.ALL, s, e);
  }
}
