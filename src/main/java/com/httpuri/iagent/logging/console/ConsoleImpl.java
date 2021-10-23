package com.httpuri.iagent.logging.console;

import com.httpuri.iagent.logging.Logger;

public class ConsoleImpl implements Logger {

  public ConsoleImpl(){
    //console out
  }

  @Override
  public boolean isDebugEnabled() {
    return true;
  }

  @Override
  public boolean isInfoEnabled() {
    return true;
  }

  @Override
  public void error(String s, Throwable e) {
    System.err.println("[error]" + s);
    e.printStackTrace(System.err);
  }

  @Override
  public void error(String s) {
    System.err.println("[error]" + s);
  }

  @Override
  public void trace(String s) {
    System.out.println("[trace]" + s);
  }

  @Override
  public void trace(String s, Throwable e) {
    System.out.println("[trace]" + s);
    e.printStackTrace(System.out);
  }

  @Override
  public void warn(String s) {
    System.out.println("[warn]" + s);
  }

  @Override
  public void warn(String s, Throwable e) {
    System.out.println("[warn]" + s);
    e.printStackTrace(System.out);
  }

  @Override
  public void info(String s) {
    System.out.println("[info]" + s);
  }

  @Override
  public void info(String s, Throwable e) {
    System.out.println("[info]" + s);
    e.printStackTrace(System.out);
  }


  @Override
  public void debug(String s) {
    System.out.println("[debug]" + s);
  }

  @Override
  public void debug(String s, Throwable e) {
    System.out.println("[debug]" + s);
    e.printStackTrace(System.out);
  }

}
