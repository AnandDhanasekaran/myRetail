package com.target.myRetail.command

import com.target.myRetail.common.CommandContext
import com.target.myRetail.exception.InvalidProductIdException
import spock.lang.Specification
import spock.lang.Unroll

class ValidateClientRequestCommandSpec extends Specification {
  ValidateClientRequestCommand command

  void setup() {
    command = new ValidateClientRequestCommand()
  }

  @Unroll
  def "Execute"() {
    given:
    Exception exception
    CommandContext context = new CommandContext(request: ['productId': product
    ])

    when:
    Boolean response
    try {
      response = command.execute(true, context)
    } catch (Exception e) {
      exception = e
    }

    then:
    (!result) ? response == result : exception instanceof InvalidProductIdException

    where:
    scenario            | product | result
    'when product id'   | '123'   | false
    'when product null' | null    | true
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
    CommandContext context = new CommandContext(request: ['productId': null])

    when:
    try {
      Boolean response = command.handleException(new InvalidProductIdException(), true, context)
    } catch (Exception e) {
      exception = e
    }

    then:
    exception instanceof InvalidProductIdException
  }
}
