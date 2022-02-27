package com.target.myRetail.command;

import com.target.myRetail.common.CommandContext;
import com.target.myRetail.common.command.ACommand;
import com.target.myRetail.common.command.CommandChain;
import com.target.myRetail.exception.ProductNotAvailable;
import com.target.myRetail.exception.TooManyRequestException;
import com.target.myRetail.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GetProductDetailsCommand extends ACommand<CommandContext> {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${product.key}")
  String keyName;

  @Autowired
  private ProductRepository productRepository;

  public Boolean execute(Boolean ignore, CommandContext commandContext) {
    String productId = (String)commandContext.getRequest().get("productId");
    Map<String, Object> response = commandContext.getContext();
    if (response == null) response = new HashMap<>();
    Map<String, String> queryMaps = new HashMap();
    queryMaps.put("key", this.keyName);
    queryMaps.put("tcin", productId);
    response.put("getProductDetailsResponse", this.productRepository.getProductInfoById(queryMaps));
    commandContext.setContext(response);
    return CommandChain.CONTINUE;
  }

  public Boolean postProcess(Boolean result, CommandContext context) {
    this.logger.info("step=GET_PRODUCT_DETAILS, status=SUCCESS");
    return CommandChain.CONTINUE;
  }

  public Boolean handleException(Exception e, Boolean result, CommandContext commandContext) throws Exception {
    String productId = (String)commandContext.getRequest().get("productId");
    this.logger.error("step=GET_PRODUCT_DETAILS, status=FAIL");
    if (e instanceof TooManyRequestException) {
      throw new TooManyRequestException();
    } else {
      throw new ProductNotAvailable(Integer.parseInt(productId));
    }
  }
}
