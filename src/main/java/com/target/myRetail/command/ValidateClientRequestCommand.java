package com.target.myRetail.command;

import com.target.myRetail.exception.InvalidProductIdException;
import com.target.myRetail.common.CommandContext;
import com.target.myRetail.common.command.ACommand;
import com.target.myRetail.common.command.CommandChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidateClientRequestCommand extends ACommand<CommandContext> {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  public Boolean execute(Boolean ignore, CommandContext commandContext) {
    String productId = (String)commandContext.getRequest().get("productId");
    if (productId != null && !productId.isEmpty()) {
      return CommandChain.CONTINUE;
    } else {
      throw new InvalidProductIdException();
    }
  }

  public Boolean postProcess(Boolean result, CommandContext context) {
    this.logger.info("step=VALIDATE_REQUEST, status=SUCCESS");
    return CommandChain.CONTINUE;
  }

  public Boolean handleException(Exception e, Boolean result, CommandContext context) throws Exception {
    this.logger.error("step=VALIDATE_REQUEST, status=FAIL");
    throw new InvalidProductIdException();
  }
}
