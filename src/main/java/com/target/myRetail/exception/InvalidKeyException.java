package com.target.myRetail.exception;

public class InvalidKeyException extends RuntimeException {
  public InvalidKeyException() {
    super(String.format("Invalid Key"));
  }
}
