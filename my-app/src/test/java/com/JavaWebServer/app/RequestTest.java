package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class RequestTest {
  private Request request;

  @Before
  public void buildNewRequest() {
    request = new Request();
  }

  @Test
  public void getRoute() {
    String message = "GET / HTTP/1.1";
    String route = "/";
    request.setMessage(message);
    assertEquals(request.getRoute(),route);
  }

  @Test
  public void TestSetMessage() {
    String message = "GET / HTTP/1.1";
    request.setMessage(message);
    assertEquals(request.getRequest(),"GET /");
  }

  @Test
  public void getMethod() {
    String message = "POST / HTTP/1.1";
    String method ="POST";
  }
}
