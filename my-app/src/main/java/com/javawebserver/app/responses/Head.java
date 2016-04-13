package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.responseBuilders.ResponseBuilder;

public class Head extends Get {

  public Head(ResponseBuilder responseBuilder) {
    super(responseBuilder);
  }
}
