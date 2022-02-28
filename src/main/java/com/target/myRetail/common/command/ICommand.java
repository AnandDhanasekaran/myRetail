package com.target.myRetail.common.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.target.myRetail.common.CommandContext;

public interface ICommand<T extends CommandContext> {
  Boolean evaluate(T obj);

  Boolean preProcess(Boolean result, T obj);

  Boolean execute(Boolean result, T obj) throws JsonProcessingException;

  Boolean postProcess(Boolean result, T obj);

  Boolean handleException(Exception e, Boolean result, T obj) throws Exception;

  Boolean handleIgnoreException(Exception e, Boolean result, T obj);
}
