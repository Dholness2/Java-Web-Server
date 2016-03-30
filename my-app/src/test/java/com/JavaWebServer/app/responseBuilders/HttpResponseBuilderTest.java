package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertEquals;

public class HttpResponseBuilderTest {
  private ResponseBuilder testResponseBuilder = new HttpResponseBuilder();
  private String CRLF = System.getProperty("line.separator");

  @After
  public void resetResponse(){
    testResponseBuilder.clearBuilder();
  }

  @Test
  public void addStatusTest(){
    String OK = "OK";
    testResponseBuilder.addStatus(OK);
    String response = (new String (testResponseBuilder.getResponse()));
    String expectedResponse = "HTTP/1.1 200 OK"+CRLF;
    assertEquals(expectedResponse, response);
  }

  @Test
  public void addStatusAndHeaderTest(){
    String OK = "OK";
    String header ="Content-Type: ";
    String value = "text/plain";
    testResponseBuilder.addStatus(OK);
    testResponseBuilder.addHeader(header,value);
    String response = (new String (testResponseBuilder.getResponse()));
    String expectedResponse = "HTTP/1.1 200 OK"+CRLF+"Content-Type: text/plain"+CRLF;
    assertEquals(expectedResponse, response);
  }

  @Test
  public void addBodyTest(){
    String OK = "OK";
    byte[] body = ("Hello World").getBytes();
    String header ="Content-Length: ";
    String value = Integer.toString(body.length);
    testResponseBuilder.addStatus(OK);
    testResponseBuilder.addHeader(header,value);
    testResponseBuilder.addBody(body);
    String response = (new String (testResponseBuilder.getResponse()));
    String expectedResponse = "HTTP/1.1 200 OK"+CRLF+"Content-Length: 11"+CRLF+CRLF+CRLF+"Hello World";
    assertEquals(expectedResponse, response);
  }

  @Test
  public void clearBuilderTest() {
    String OK = "OK";
    byte[] body = ("First Response").getBytes();
    String header ="Content-Length: ";
    String value = Integer.toString(body.length);
    testResponseBuilder.addStatus(OK);
    testResponseBuilder.addHeader(header,value);
    testResponseBuilder.addBody(body);
    testResponseBuilder.clearBuilder();
    testResponseBuilder.addStatus("NOT_FOUND");
    String response = (new String (testResponseBuilder.getResponse()));
    String expectedResponse = "HTTP/1.1 404 Not Found"+ CRLF;
    assertEquals(expectedResponse, response);
  }
}
