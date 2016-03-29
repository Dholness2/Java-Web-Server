package com.JavaWebServer.app.responses;

import com.JavaWebServer.app.Request;

public interface Response {
  public byte [] handleRequest(Request request);
}
