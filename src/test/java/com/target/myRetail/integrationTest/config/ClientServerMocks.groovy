package com.target.myRetail.integrationTest.config

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

public class ClientServerMocks {

  public static void setupMockProductSuccessResponse(WireMockServer mockService) throws IOException {
    mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/redsky_aggregations/v1/redsky/case_study_v1?tcin=13860428&key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys"))
        .willReturn(WireMock.aResponse()
            .withStatus(HttpStatus.OK.value())
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody("{\"data\":{\"product\":{\"tcin\":\"13860428\",\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\"}}}}}")));
  }

  public static void setupMockProductNotFoundResponse(WireMockServer mockService) throws IOException {
    mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/redsky_aggregations/v1/redsky/case_study_v1?tcin=13860429&key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys"))
        .willReturn(WireMock.aResponse()
            .withStatus(HttpStatus.NOT_FOUND.value())));
  }

  public static void setupMockProductTooManyRequestResponse(WireMockServer mockService) throws IOException {
    mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/redsky_aggregations/v1/redsky/case_study_v1?tcin=13860410&key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys"))
        .willReturn(WireMock.aResponse()
            .withStatus(HttpStatus.TOO_MANY_REQUESTS.value())));
  }
}
