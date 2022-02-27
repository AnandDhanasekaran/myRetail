package com.target.myRetail.repository;

import com.target.myRetail.feign.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
    name = "product-service",
    url = "${product.host}",
    configuration = {FeignClientConfiguration.class}
)
public interface ProductRepository {
  @RequestMapping(
      method = {RequestMethod.GET},
      value = {"/redsky_aggregations/v1/redsky/case_study_v1"}
  )
  ResponseEntity<String> getProductInfoById(@SpringQueryMap Map queryMap);
}
