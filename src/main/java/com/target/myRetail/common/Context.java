package com.target.myRetail.common;

import java.util.HashMap;
import java.util.Map;

public class Context {
  Map<String, Object> request;
  Map<String, Object> context;
  Map<String, Object> response;

  public void setRequest(Map<String, Object> request) {
    this.request = request;
  }

  public void setContext(Map<String, Object> context) {
    this.context = context;
  }

  public void setResponse(Map<String, Object> response) {
    this.response = response;
  }

  public Map<String, Object> getResponse() {
    if (this.response == null) {
      this.response = new HashMap();
    }

    return this.response;
  }

  public Map<String, Object> getContext() {
    if (this.context == null) {
      this.context = new HashMap();
    }

    return this.context;
  }

  public Map<String, Object> getRequest() {
    if (this.request == null) {
      this.request = new HashMap();
    }

    return this.request;
  }
}
