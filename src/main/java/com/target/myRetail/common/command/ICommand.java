package com.target.myRetail.common.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.target.myRetail.common.CommandContext;

public interface ICommand<T extends CommandContext> {
  Boolean evaluate(T var1);

  Boolean preProcess(Boolean var1, T var2);

  Boolean execute(Boolean var1, T var2) throws JsonProcessingException;

  Boolean postProcess(Boolean var1, T var2);

  Boolean handleException(Exception var1, Boolean var2, T var3) throws Exception;

  Boolean handleIgnoreException(Exception var1, Boolean var2, T var3);
}
