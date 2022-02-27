//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.target.myRetail.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

public class Price {
  @Id
  private Integer id;
  @Min(
      value = 1L,
      message = "the value cannot be zero"
  )
  private Double value;
  @NotEmpty(
      message = "currency has to be specified"
  )
  @JsonProperty("currency_code")
  private String currencyCode;

  public Price(Integer id, Double value, String currencyCode) {
    this.setId(id);
    this.value = value;
    this.currencyCode = currencyCode;
  }

  public Price() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getValue() {
    return this.value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public String getCurrencyCode() {
    return this.currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }
}
