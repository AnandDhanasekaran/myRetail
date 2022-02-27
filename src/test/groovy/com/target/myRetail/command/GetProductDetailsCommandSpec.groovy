package com.target.myRetail.command

import com.target.myRetail.common.CommandContext
import com.target.myRetail.exception.TooManyRequestException
import com.target.myRetail.repository.ProductRepository
import spock.lang.Specification

class GetProductDetailsCommandSpec extends Specification {
  GetProductDetailsCommand command
  ProductRepository productRepository = Mock(ProductRepository)

  void setup() {
    command = new GetProductDetailsCommand(
        productRepository: productRepository,
        keyName: '111111111'
    )
  }

  def "Execute"() {
    given:
    CommandContext context = new CommandContext(request: ['productId': '123'])

    when:
    Boolean response = command.execute(true, context)

    then:
    1 * productRepository.getProductInfoById(['tcin':'123', 'key':'111111111']) >> null
    (context.context.getProductDetailsResponse ) == null
  }

  def "PostProcess"() {
    when:
    Boolean response = command.postProcess(true, new CommandContext())

    then:
    !response
  }

  def "HandleException"() {
    given:
    Exception exception = null
    CommandContext context = new CommandContext(request: ['productId': '123'])

    when:
    try {
      Boolean response = command.handleException(new TooManyRequestException(), true, context)
    } catch(Exception e) {
      exception = e
    }

    then:
    exception instanceof TooManyRequestException
  }
}
