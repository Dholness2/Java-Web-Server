package com.javawebserver.app.responses;

import com.javawebserver.app.Request;

public interface Response {
  public byte [] handleRequest(Request request);
}
