package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;

import com.javawebserver.app.Request;
import com.javawebserver.app.responseBuilders.ResponseBuilder;

import java.util.Arrays;

public class Options implements Response {
  private ResponseBuilder response;
  private String[] options;
  private static final String ALLOW_HEADER = "Allow: ";

  public Options(ResponseBuilder response, String[] options) {
    this.response = response;
    this.options = options;
  }

  public byte [] handleRequest(Request request) {
    byte[] responseMessage = getResponse();
    this.response.clearBuilder();
    return responseMessage;
  }

  private String getMethods() {
    StringBuilder optionBuilder = new StringBuilder();
    for(String methods : this.options)
      optionBuilder.append(methods +",");
    return optionBuilder.toString();
  }

  private byte[] getResponse() {
    this.response.addStatus("OK");
    this.response.addHeader(ALLOW_HEADER, getMethods());
    return this.response.getResponse();
  }
}
