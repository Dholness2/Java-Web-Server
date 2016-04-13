package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.Request;

import java.util.Arrays;

public class Options implements Response {
  private ResponseBuilder responseBuilder;
  private String[] options;
  private static final String OK_STATUS_CODE = "200";
  private static final String ALLOW_HEADER = "Allow: ";

  public Options(ResponseBuilder responseBuilder, String[] options) {
    this.responseBuilder = responseBuilder;
    this.options = options;
  }

  public byte[] handleRequest(Request request) {
    ResponseBuilder currentResponse = this.responseBuilder.clone();
    return getResponse(currentResponse);
  }

  private String getMethods() {
    StringBuilder optionBuilder = new StringBuilder();
    for(String methods : this.options)
      optionBuilder.append(methods +",");
    return optionBuilder.toString();
  }

  private byte[] getResponse(ResponseBuilder response) {
    response.addStatus(OK_STATUS_CODE);
    response.addHeader(ALLOW_HEADER, getMethods());
    return response.getResponse();
  }
}
