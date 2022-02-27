package com.target.myRetail.command;

import com.target.myRetail.common.CommandContext;
import com.target.myRetail.common.command.ACommand;
import com.target.myRetail.common.command.CommandChain;
import com.target.myRetail.domain.Price;
import com.target.myRetail.service.IPricingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SavePriceDetailsCommand extends ACommand<CommandContext> {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private IPricingService pricingService;

  public Boolean execute(Boolean ignore, CommandContext commandContext) {
    Price price = (Price)commandContext.getRequest().get("price");
    Map<String, Object> response = new HashMap();
    response.put("pricingResponse", this.pricingService.save(price));
    commandContext.setResponse(response);
    return CommandChain.CONTINUE;
  }

  public Boolean postProcess(Boolean result, CommandContext context) {
    this.logger.info("step=SAVE_PRICE_DETAILS, status=SUCCESS");
    return CommandChain.CONTINUE;
  }

  public Boolean handleException(Exception e, Boolean result, CommandContext context) throws Exception {
    this.logger.error("step=SAVE_PRICE_DETAILS, status=FAIL");
    throw e;
  }
}
