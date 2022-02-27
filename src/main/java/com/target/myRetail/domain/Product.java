//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.target.myRetail.domain;

import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {
  @Id
  @NotNull(
      message = "product id cannot be null"
  )
  public Integer id;
  @Transient
  @NotEmpty(
      message = "product name cannot be empty"
  )
  public String name;
  @NotNull
  public Map<String, Object> current_price;

  public Product(Integer id, String name, Map<String, Object> current_price) {
    this.id = id;
    this.name = name;
    this.current_price = current_price;
  }

  public Product() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, Object> getCurrent_price() {
    return this.current_price;
  }

  public void setCurrent_price(Map<String, Object> current_price) {
    this.current_price = current_price;
  }
}
