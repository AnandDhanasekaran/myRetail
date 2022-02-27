package com.target.myRetail.command

import com.target.myRetail.common.CommandContext
import com.target.myRetail.domain.Price
import com.target.myRetail.exception.ProductNotAvailable
import spock.lang.Specification

class BuildProductResponseCommandSpec extends Specification {
  BuildProductResponseCommand command

  void setup() {
    command = new BuildProductResponseCommand()
  }

  def "Execute"() {
    given:
    CommandContext context = new CommandContext(
        request: ['productId': '123'],
        context: [
            'getPricingDetailsResponse': new Price(
                id: 123,
                value: 1.10,
                currencyCode: 'USD'
            )
        ]
    )

    when:
    Boolean response = command.execute(true, context)

    then:
    context.response.productDetails
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

    when:
    try {
      Boolean response = command.handleException(new ProductNotAvailable(123), true, new CommandContext())
    } catch(Exception e) {
      exception = e
    }

    then:
    exception instanceof ProductNotAvailable
  }
}
