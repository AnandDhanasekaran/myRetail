package com.target.myRetail.command

import com.target.myRetail.common.CommandContext
import com.target.myRetail.domain.Price
import com.target.myRetail.exception.ItemNotFoundException
import com.target.myRetail.service.PricingService
import spock.lang.Specification

class UpdatePriceDetailsCommandSpec extends Specification {
  UpdatePriceDetailsCommand command
  PricingService pricingService = Mock(PricingService)

  void setup() {
    command = new UpdatePriceDetailsCommand(
        pricingService: pricingService
    )
  }

  def "Execute"() {
    given:
    Price price = new Price(
        id: 123,
        value: 1.10,
        currencyCode: 'USD'
    )
    Price newPrice = new Price(
        id: 123,
        value: 3.10,
        currencyCode: 'USD'
    )

    CommandContext context = new CommandContext(
        request: ['price' : newPrice],
        context: ['getPricingDetailsResponse': price]
    )

    when:
    Boolean response = command.execute(true, context)

    then:
    1 * pricingService.save(price) >> newPrice
    (context.response.pricingResponse as Price).value == newPrice.value
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
      Boolean response = command.handleException(new ItemNotFoundException(123), true, context)
    } catch(Exception e) {
      exception = e
    }

    then:
    exception instanceof ItemNotFoundException
  }
}
