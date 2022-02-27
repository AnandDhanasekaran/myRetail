package com.target.myRetail.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {
  @Bean
  public ErrorDecoder errorDecoder() {
    return new FeignClientErrorDecoder();
  }
}
