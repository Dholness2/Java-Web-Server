package com.JavaWebServer.app.responses;

import com.JavaWebServer.app.responses.RestMethod;
import com.JavaWebServer.app.Request;

public class Head implements RestMethod {
  private String responseStatus;

  public Head(String response ) {
    this.responseStatus = response;
  }

  public byte [] handleRequest(Request request) {
      return this.responseStatus.getBytes();
  }
}