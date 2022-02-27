package com.target.myRetail.advice;

import com.target.myRetail.domain.Error;
import com.target.myRetail.domain.Errors;
import com.target.myRetail.exception.ItemNotFoundException;
import com.target.myRetail.exception.ProductNotAvailable;
import com.target.myRetail.exception.TooManyRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ProductExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ItemNotFoundException.class})
  public ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException ex) {
    Errors errors = buildError("PRICING_NOT_AVAILABLE", "please check the product id, if this is a new product please add the price");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
  }

  @ExceptionHandler({ProductNotAvailable.class})
  public ResponseEntity<Object> handleProductNotFoundException(ProductNotAvailable ex) {
    Errors errors = buildError("PRODUCT_NOT_FOUND", "There is no such product available, please add the product");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
  }

  @ExceptionHandler({TooManyRequestException.class})
  public ResponseEntity<Object> handleTooManyRequestException(TooManyRequestException ex) {
    Errors errors = buildError("TOO_MANY_REQUEST", "Too Many Request at this time, please try again later");
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errors);
  }

  public Errors buildError(String errorCode, String message) {
    List<Error> errorList = new ArrayList<>();
    Error error = new Error();
    error.setErrorCode(errorCode);
    error.setUserMessage(message);
    errorList.add(error);

    Errors errors = new Errors();
    errors.setErrors(errorList);
    return errors;
  }
}
