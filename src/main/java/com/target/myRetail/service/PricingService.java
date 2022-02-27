package com.target.myRetail.service;

import com.target.myRetail.domain.Price;
import com.target.myRetail.exception.ItemNotFoundException;
import com.target.myRetail.repository.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService implements IPricingService {
  @Autowired
  PricingRepository pricingRepository;

  public Price findById(Integer id) {
    return (Price)this.pricingRepository.findById(id).orElseThrow(() -> {
      return new ItemNotFoundException(id);
    });
  }

  public Price save(Price price) {
    return (Price)this.pricingRepository.save(price);
  }

  public List<Price> findAll() {
    return null;
  }
}
