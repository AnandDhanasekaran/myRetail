package com.target.myRetail.service;

import com.target.myRetail.domain.Price;
import java.util.List;

public interface IPricingService {
  Price findById(Integer var1);

  Price save(Price var1);

  List<Price> findAll();
}
