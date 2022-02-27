package com.target.myRetail.exception;

public class TooManyRequestException extends RuntimeException {
  public TooManyRequestException() {
    super(String.format("Too Many Request"));
  }
}
