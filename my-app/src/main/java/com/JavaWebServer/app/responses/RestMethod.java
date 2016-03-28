package com.JavaWebServer.app.responses;

import com.JavaWebServer.app.Request;

public interface RestMethod {
  public byte [] handleRequest(Request request);
}
