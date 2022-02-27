package com.target.myRetail.feign;

import com.target.myRetail.exception.InvalidKeyException;
import com.target.myRetail.exception.ProductNotAvailable;
import com.target.myRetail.exception.TooManyRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class FeignClientErrorDecoder implements ErrorDecoder {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());
  private static final String SERVER_ERROR_5XX = " - 5xx Server Error";
  private static final String SERVER_ERROR_4XX = " - 4xx Server Error";
  private static final String SERVER_ERROR_OTHER = " - Other Server error";

  public Exception decode(String methodKey, Response response) {
    String requestUrl = response.request().url();
    HttpStatus responseStatus = HttpStatus.valueOf(response.status());
    if (responseStatus.is5xxServerError()) {
      return new Exception(requestUrl + " - 5xx Server Error");
    } else if (response.status() == 404) {
      return new ProductNotAvailable((Integer)null);
    } else if (response.status() == 401) {
      return new InvalidKeyException();
    } else if (response.status() == 429) {
      return new TooManyRequestException();
    } else {
      return responseStatus.is4xxClientError() ? new Exception(requestUrl + " - 4xx Server Error") : new Exception(requestUrl + " - Other Server error");
    }
  }
}
