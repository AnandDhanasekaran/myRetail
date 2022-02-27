package com.target.myRetail.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myRetail.common.CommandContext;
import com.target.myRetail.common.command.ACommand;
import com.target.myRetail.common.command.CommandChain;
import com.target.myRetail.domain.Price;
import com.target.myRetail.domain.Product;
import com.target.myRetail.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BuildProductResponseCommand extends ACommand<CommandContext> {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ProductRepository productRepository;

  public Boolean execute(Boolean ignore, CommandContext commandContext) throws JsonProcessingException {
    Product product = new Product();
    Price price = (Price) commandContext.getContext().get("getPricingDetailsResponse");
    Map<String, Object> productResponse = new HashMap();
    ResponseEntity<String> responseEntity = (ResponseEntity)commandContext.getContext().get("getProductDetailsResponse");
    ObjectMapper infoMapper = new ObjectMapper();
    Map<String, String> productDescriptionMap = new HashMap<>();
    if (responseEntity != null && responseEntity.getBody() != null) {
      Map<String, Map> infoMap = (Map)infoMapper.readValue(responseEntity.getBody(), Map.class);
      Map<String, Map> dataMap = (Map)infoMap.get("data");
      Map<String, Map> productMap = (Map)dataMap.get("product");
      Map<String, Map> itemMap = (Map)productMap.get("item");
     productDescriptionMap = (Map)itemMap.get("product_description");
    }
    Map<String, Object> currentPrice = new HashMap();
    if (price != null) {
      currentPrice.put("value", price.getValue());
      currentPrice.put("currency_code", price.getCurrencyCode());
    }
    product.setId(price.getId());
    product.setCurrent_price(currentPrice);
    product.setName(productDescriptionMap.get("title"));
    productResponse.put("productDetails", product);
    commandContext.setResponse(productResponse);
    return CommandChain.CONTINUE;
  }

  public Boolean postProcess(Boolean result, CommandContext context) {
    this.logger.info("step=BUILD_PRODUCT_RESPONSE, status=SUCCESS");
    return CommandChain.CONTINUE;
  }

  public Boolean handleException(Exception e, Boolean result, CommandContext commandContext) throws Exception {
    this.logger.error("step=BUILD_PRODUCT_RESPONSE, status=FAIL");
    throw e;
  }
}
