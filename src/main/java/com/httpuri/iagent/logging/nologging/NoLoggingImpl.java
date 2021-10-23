package com.httpuri.iagent.logging.nologging;

import com.httpuri.iagent.logging.Logger;

public class NoLoggingImpl implements Logger {

  public NoLoggingImpl(String clazz) {
    // Do Nothing
  }

  @Override
  public boolean isDebugEnabled() {
    return false;
  }

  @Override
  public boolean isInfoEnabled() {
    return false;
  }

  @Override
  public void error(String s, Throwable e) {

  }

  @Override
  public void error(String s) {

  }

  @Override
  public void trace(String s) {

  }

  @Override
  public void trace(String s, Throwable e) {

  }

  @Override
  public void warn(String s) {

  }

  @Override
  public void warn(String s, Throwable e) {

  }

  @Override
  public void info(String s) {

  }

  @Override
  public void info(String s, Throwable e) {

  }

  @Override
  public void debug(String s) {

  }

  @Override
  public void debug(String s, Throwable e) {

  }
}
