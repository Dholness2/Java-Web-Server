package com.javawebserver.app;

import com.javawebserver.app.Request;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Params;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.StatusCodes;

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
  private String CRLF = System.getProperty("line.separator");
  private String request = "GET /paramaters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1";

  @Before
  public void buildTestSetup()  {
     HashMap<String, Response> routes = new HashMap<String,Response>();
     routes.put("GET /", new Get(StatusCodes.OK));
     routes.put("GET /paramaters?", new Params(new HttpResponseBuilder(), "paramaters?"));
     testRequest = new Request();
     testResponder =  new Responder(routes);
   }

  @Test
  public void getResponseTest() {
    testRequest.setMessage("GET / HTTP/1.1");
   byte [] response =  testResponder.getResponse(testRequest);
    assertEquals(StatusCodes.OK, new String(response));
  }

  @Test
  public void getParamsResponseTest() {
    String content = "Content-Length: 96";
    String type = "Content-Type: text/plain";
    String varOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
    String varTwo = "variable_2 = stuff";
    String expected = (StatusCodes.OK + CRLF + type + CRLF + content + CRLF + CRLF + varOne + CRLF + varTwo);
    testRequest.setMessage(request);
    byte [] response =  testResponder.getResponse(testRequest);
    assertEquals(expected, new String(response));
  }

  @Test
  public void getResponseTestNoMethod() {
    testRequest.setMessage("PATCH / HTTP/1.1");
   byte [] response =  testResponder.getResponse(testRequest);
    assertEquals(("HTTP/1.1 405 Method Not Allowed"), new String(response));
  }
  @Test
  public void getResponseTestNoRoute() {
    testRequest.setMessage("PUT /foo HTTP/1.1");
    byte [] response =  testResponder.getResponse(testRequest);
    assertEquals("HTTP/1.1 404 Not Found", new String (response));
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
