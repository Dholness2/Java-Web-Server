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
  private String request = "GET /paramaters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1";

  private  Map <String, String>  paramatersEncodingKeyMap() {
    Map<String, String> encode = new HashMap<String, String>();
    encode.put("%3C","<");
    encode.put("%3E",">");
    encode.put("%3D","=");
    encode.put("%2C",",");
    encode.put("%21","!" );
    encode.put("%2B","+");
    encode.put("%2D","-");
    encode.put("%20"," ");
    encode.put("%22", "\"");
    encode.put("%3B",";");
    encode.put("%2A","*");
    encode.put("%26","&");
    encode.put("%40","@");
    encode.put("%23","#");
    encode.put("%24","\\$");
    encode.put("%5B","[");
    encode.put("%5D","]");
    encode.put("%3A",":");
    encode.put("%3F","?");
    return encode;
  }

  @Before
  public void buildTestSetup()  {
     HashMap<String, RestMethod> methods = new HashMap<String,RestMethod>();
     methods.put("GET /", new Get(codes.OK));
     methods.put("GET /paramaters?", new Params(codes.OK, "paramaters?", paramatersEncodingKeyMap()));
     ArrayList<String> routeMethods = new ArrayList<String>(); 
     routeMethods.add("GET");
     routes = new HashMap<String, ArrayList<String>>();
     routes.put("/",routeMethods);
     routes.put("/paramaters?",routeMethods);
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
  public void getParamsResponseTest() {
    String CRLF ="\r\n";
    String content = "Content-Length:97";
    String type = "Content-Type: text/plain";
    String varOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
    String varTwo = "variable_2 = stuff";
    String expected = (codes.OK +CRLF+type+CRLF+content+CRLF+CRLF + varOne +CRLF+ varTwo);
    testRequest.setMessage(request);
    byte [] response =  testResponder.getResponse(testRequest);
    assertEquals(expected, new String(response));
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
