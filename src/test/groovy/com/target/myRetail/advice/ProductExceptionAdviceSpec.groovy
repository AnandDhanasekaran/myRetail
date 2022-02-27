package com.target.myRetail.advice


import com.target.myRetail.domain.Errors
import com.target.myRetail.exception.ItemNotFoundException
import com.target.myRetail.exception.ProductNotAvailable
import com.target.myRetail.exception.TooManyRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class ProductExceptionAdviceSpec extends Specification {
  ProductExceptionAdvice advice

  void setup() {
    advice = new ProductExceptionAdvice()
  }

  def "HandleItemNotFoundException"() {
    given:
    ItemNotFoundException exception = new ItemNotFoundException()

    when:
    ResponseEntity responseEntity = advice.handleItemNotFoundException(exception)

    then:
    responseEntity.statusCode == HttpStatus.NOT_FOUND
    (responseEntity.body as Errors)?.errors?.get(0)?.errorCode == 'PRICING_NOT_AVAILABLE'
    (responseEntity.body as Errors)?.errors?.get(0)?.userMessage == "please check the product id, if this is a new product please add the price"
  }

  def "HandleProductNotFoundException"() {
    given:
    ProductNotAvailable exception = new ProductNotAvailable()

    when:
    ResponseEntity responseEntity = advice.handleProductNotFoundException(exception)

    then:
    responseEntity.statusCode == HttpStatus.NOT_FOUND
    (responseEntity.body as Errors)?.errors?.get(0)?.errorCode == 'PRODUCT_NOT_FOUND'
    (responseEntity.body as Errors)?.errors?.get(0)?.userMessage == "There is no such product available, please add the product"
  }

  def "HandleInvalidProductIdFoundException"() {
    given:
    ItemNotFoundException exception = new ItemNotFoundException()

    when:
    ResponseEntity responseEntity = advice.handleItemNotFoundException(exception)

    then:
    responseEntity.statusCode == HttpStatus.NOT_FOUND
    (responseEntity.body as Errors)?.errors?.get(0)?.errorCode == 'PRICING_NOT_AVAILABLE'
    (responseEntity.body as Errors)?.errors?.get(0)?.userMessage == "please check the product id, if this is a new product please add the price"
  }

  def "HandleTooManyRequestException"() {
    given:
    TooManyRequestException exception = new TooManyRequestException()

    when:
    ResponseEntity responseEntity = advice.handleTooManyRequestException(exception)

    then:
    responseEntity.statusCode == HttpStatus.TOO_MANY_REQUESTS
    (responseEntity.body as Errors)?.errors?.get(0)?.errorCode == 'TOO_MANY_REQUEST'
    (responseEntity.body as Errors)?.errors?.get(0)?.userMessage == "Too Many Request at this time, please try again later"
  }

  def "BuildError"() {
    when:
    Errors errors = advice.buildError('a', 'bbb')

    then:
    errors.errors.get(0).errorCode == 'a'
    errors.errors.get(0).userMessage == 'bbb'
  }
}
