package com.target.myRetail.service;

import com.target.myRetail.command.*;
import com.target.myRetail.common.CommandContext;
import com.target.myRetail.common.command.CommandChain;
import com.target.myRetail.domain.Price;
import com.target.myRetail.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
  @Autowired
  CommandChain commandChain;

  public Product getProduct(String productId) throws Exception {
    CommandContext commandContext = new CommandContext();
    this.setContext(commandContext, productId, null);
    List<Class> classList = new ArrayList();
    classList.add(ValidateClientRequestCommand.class);
    classList.add(GetPriceDetailsCommand.class);
    classList.add(GetProductDetailsCommand.class);
    classList.add(BuildProductResponseCommand.class);
    this.commandChain.execute(classList, commandContext);
    return (Product)commandContext.getResponse().get("productDetails");
  }

  public Price updatePrice(Price price, String productId) throws Exception {
    CommandContext commandContext = new CommandContext();
    this.setContext(commandContext, productId, price);
    List<Class> classList = new ArrayList();
    classList.add(ValidateClientRequestCommand.class);
    classList.add(GetPriceDetailsCommand.class);
    classList.add(UpdatePriceDetailsCommand.class);
    this.commandChain.execute(classList, commandContext);
    return (Price)commandContext.getResponse().get("pricingResponse");
  }

  public Price insertPrice(Price price, String productId) throws Exception {
    CommandContext commandContext = new CommandContext();
    this.setContext(commandContext, productId, price);
    List<Class> classList = new ArrayList();
    classList.add(ValidateClientRequestCommand.class);
    classList.add(SavePriceDetailsCommand.class);
    this.commandChain.execute(classList, commandContext);
    return (Price)commandContext.getResponse().get("pricingResponse");
  }

  private void setContext(CommandContext commandContext, String productId, Price price) {
    Map<String, Object> requestMap = new HashMap();
    requestMap.put("productId", productId);
    if (price != null) {
      requestMap.put("price", price);
    }

    commandContext.setRequest(requestMap);
  }
}
