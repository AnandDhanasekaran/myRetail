package com.target.myRetail.common.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.target.myRetail.common.CommandContext;

public abstract class ACommand<T extends CommandContext> implements ICommand<T> {
  public Boolean evaluate(T context) {
    return null;
  }

  public Boolean preProcess(Boolean result, T context) {
    return false;
  }

  public Boolean execute(Boolean ignore, T ignored) throws JsonProcessingException {
    return false;
  }

  public Boolean postProcess(Boolean result, T context) {
    return null;
  }

  public Boolean handleException(Exception e, Boolean result, T context) throws Exception {
    return null;
  }

  public Boolean handleIgnoreException(Exception e, Boolean result, T context) {
    return null;
  }
}
