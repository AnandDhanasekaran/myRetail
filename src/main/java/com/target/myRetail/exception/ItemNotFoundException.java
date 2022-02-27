package com.target.myRetail.exception;

public class ItemNotFoundException extends RuntimeException {
  public ItemNotFoundException(Integer id) {
    super(String.format("Price information for id %d not found", id));
  }
}
