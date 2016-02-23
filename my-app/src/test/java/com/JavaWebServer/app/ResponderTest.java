package com.JavaWebServer.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponderTest {

  private HashMap <String, ArrayList<String>> routes;
  private Request testRequest;
  private Responder testResponder;
  private StatusCodes codes = new StatusCodes();

  @Before
    public void buildTestSetup()  {
      HashMap<String, RestMethod> methods = new HashMap<String,RestMethod>();
      methods.put("GET /", new Get(codes.OK));
      ArrayList<String> routeMethods = new ArrayList<String>(); 
      routeMethods.add("GET");
      routes = new HashMap<String, ArrayList<String>>();
      routes.put("/",routeMethods);
      testRequest = new Request();
      testResponder =  new Responder(routes, methods);
    }

  @Test
  public void getResponseTest() {
    testRequest.setMessage("GET / HTTP/1.1");
   byte [] response =  testResponder.getResponse(testRequest);
    assertEquals(codes.OK, new String(response));
  }

  @Test
  public void getResponseTestNoMethod() {
    testRequest.setMessage("PUT / HTTP/1.1");
   byte [] response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 405 Method Not Allowed/nput post", new String(response));
  }
  @Test
  public void getResponseTestNoRoute() {
    testRequest.setMessage("PUT /foo HTTP/1.1");
    byte [] response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 404 not found", new String (response));
  }

  @Test
  public void getResponseTestBadRequest() {
    testRequest.setMessage("PUT /foo ");
    byte [] response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 400 Bad Request", new String (response));
  }


  @Test
  public void getResponseTestBadRequestEmptyString() {
    testRequest.setMessage("   ");
    byte [] response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 400 Bad Request", new String (response));
  }

  @Test
  public void getResponseTestBadRequestBadRoute() {
    testRequest.setMessage("PUT foo  HTTP/1.1");
    byte [] response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 400 Bad Request", new String (response));
  }
}
