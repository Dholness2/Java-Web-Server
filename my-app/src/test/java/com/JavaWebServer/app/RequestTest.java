package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;


public class RequestTest {
  private Request request;

  private String message = "GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1";

  @Before
  public void buildNewRequest() {
    request = new Request();
  }

  @Test public void getParamTest() {
   request.setMessage(message);
   String expected = "variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
   assertEquals(request.getParams(),expected);
  }

  @Test public void getParamsRouteTest() {
    request.setMessage(message);
    String expected =  "/parameters?";
    assertEquals(expected,request.getParamsRoute());
  }

  @Test
  public void getRoute() {
    String message = "GET /foo HTTP/1.1";
    String route = "/foo";
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

  @Test
  public void validReuestEmpty(){
    request.setMessage("    ");
    assertEquals(false,request.validRequest());
  } 

  @Test
  public void validRequestProtocol() {
   request.setMessage("GET /home");
   assertEquals(false, request.validRequest());
  }
}
