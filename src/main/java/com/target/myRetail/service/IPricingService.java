package com.target.myRetail.service;

import com.target.myRetail.domain.Price;
import java.util.List;

public interface IPricingService {
  Price findById(Integer id);

  Price save(Price price);

  List<Price> findAll();
}
