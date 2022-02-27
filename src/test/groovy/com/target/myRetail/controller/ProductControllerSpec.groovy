package com.target.myRetail.controller

import com.target.myRetail.domain.Price
import com.target.myRetail.domain.Product
import com.target.myRetail.domain.Response
import com.target.myRetail.exception.ItemNotFoundException
import com.target.myRetail.exception.ProductNotAvailable
import com.target.myRetail.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Unroll

class ProductControllerSpec extends Specification {
  ProductController productController
  ProductService productService

  def setup() {
    productService = Mock(ProductService)
    productController = new ProductController(
        productService: productService
    )
  }

  @Unroll
  def "[TestCase] - test getProductDetail - #scenario"() {
    when:
    ResponseEntity<Product> productResponseEntity =  productController.getProductDetail(productId)

    then:
    1 * productService.getProduct(productId) >> productObject
    Product product = productResponseEntity.body
    assert product?.name == productName
    assert product?.current_price?.value == value

    where:
    scenario              | productId | productObject                                                               | productName   | value
    'with positive value' | '12345'   | new Product(id: 12345, name: 'productName', current_price: ['value':12.0] ) | 'productName' | 12.0
    'with null value'     | '12345'   | null                                                                        | null          | null
  }

  def "[TestCase] - test getProductDetail - exception"() {
    when:
    productController.getProductDetail('123')

    then:
    1 * productService.getProduct('123') >> { throw new ItemNotFoundException(123) }
    thrown(ItemNotFoundException)
  }

  @Unroll
  def "[TestCase] - test savePrice - #scenario"() {
    when:
    ResponseEntity<Response> priceDetail =  productController.savePrice(price, productId)

    then:
    1 * productService.insertPrice(price,_) >> new Price()
    assert priceDetail.statusCode == HttpStatus.CREATED

    where:
    scenario              | productId | price
    'with positive value' | '12345'   | new Price(id: 12345, value: 12.0,currencyCode: 'USD' )
  }


  def "[TestCase] - test updatePrice - exception"() {
    when:
    ResponseEntity<Response> priceDetail = productController.updatePrice(new Price(), '12345')

    then:
    1 * productService.updatePrice(_,_) >> { throw new ProductNotAvailable() }
    thrown(ProductNotAvailable)
  }
}
