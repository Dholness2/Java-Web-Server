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

  @Before
  public void buildTestSetup()  {
     ArrayList<String> routeMethods = new ArrayList<String>(); 
     routeMethods.add("GET");
     routeMethods.add("Post");
     routes = new HashMap<String, ArrayList<String>>();
     routes.put("/",routeMethods);
     testRequest = new Request();
     testResponder =  new Responder(routes);
  }

  @Test
  public void getResponseTest() {
    testRequest.setMessage("GET / htpp 1.1.");
    String response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 200 ok", response);
  }

  @Test
  public void getResponseTestNoMethod() {
    testRequest.setMessage("PUT / htpp 1.1.");
    String response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 405 Method Not Allowed/nput post", response);
  }
  @Test
  public void getResponseTestNoRoute() {
    testRequest.setMessage("PUT /foo htpp 1.1.");
    String response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 404 not found", response);
  }
}
