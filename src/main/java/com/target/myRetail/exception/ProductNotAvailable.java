package com.target.myRetail.exception;

public class ProductNotAvailable extends RuntimeException {
  public ProductNotAvailable(Integer id) {
    super(String.format("Product for id %d not found", id));
  }
}
