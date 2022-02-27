package com.target.myRetail.command;

import com.target.myRetail.common.CommandContext;
import com.target.myRetail.common.command.ACommand;
import com.target.myRetail.common.command.CommandChain;
import com.target.myRetail.exception.ItemNotFoundException;
import com.target.myRetail.service.IPricingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GetPriceDetailsCommand extends ACommand<CommandContext> {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private IPricingService pricingService;

  public Boolean execute(Boolean ignore, CommandContext commandContext) {
    String productId = (String)commandContext.getRequest().get("productId");
    Map<String, Object> context = new HashMap();
    context.put("getPricingDetailsResponse", this.pricingService.findById(Integer.parseInt(productId)));
    commandContext.setContext(context);
    return CommandChain.CONTINUE;
  }

  public Boolean postProcess(Boolean result, CommandContext context) {
    this.logger.info("step=GET_PRICE_DETAILS, status=SUCCESS");
    return CommandChain.CONTINUE;
  }

  public Boolean handleException(Exception e, Boolean result, CommandContext commandContext) throws Exception {
    String productId = (String)commandContext.getRequest().get("productId");
    this.logger.error("step=GET_PRICE_DETAILS, status=FAIL");
    throw new ItemNotFoundException(Integer.parseInt(productId));
  }
}
