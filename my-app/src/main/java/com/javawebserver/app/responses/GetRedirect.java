package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.Request;

public class GetRedirect implements Response {
  private ResponseBuilder responseBuilder;
  private int port;
  private String serverName;
  private static final String LOCATION_HEADER = "Location: ";
  private static final String SEPARATOR = System.getProperty("file.separator");
  private static final String FOUND_STATUS_CODE = "302";

  public GetRedirect(ResponseBuilder responseBuilder, int port, String serverName) {
    this.responseBuilder = responseBuilder;
    this.port = port;
    this.serverName = serverName;
  }

  public byte[] handleRequest(Request request) {
    ResponseBuilder currentResponse = this.responseBuilder.clone();
    currentResponse.addStatus(FOUND_STATUS_CODE);
    currentResponse.addHeader(LOCATION_HEADER, getLocation());
    return currentResponse.getResponse();
  }

  private String getLocation() {
    return (this.serverName + this.port + SEPARATOR);
  }
}
