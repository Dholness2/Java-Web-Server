package com.JavaWebServer.app;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RequestTest {
  private Request request;

  @Test
  public void getRoute() {
    String message = "GET / http 1.1";
    String route = "/";
    request = new Request(message);
    assertEquals(request.getRoute(),route);
  }
}
