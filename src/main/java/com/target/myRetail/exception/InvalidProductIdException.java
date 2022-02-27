package com.target.myRetail.exception;

public class InvalidProductIdException extends RuntimeException {
  public InvalidProductIdException() {
    super(String.format("Product id is Invalid"));
  }
}
