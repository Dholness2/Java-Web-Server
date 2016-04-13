package com.javawebserver.app.responseBuilders;

import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertEquals;

public class HttpResponseBuilderTest {
  private ResponseBuilder testResponseBuilder = new HttpResponseBuilder();
  private String CRLF = System.getProperty("line.separator");
  private String okStatusCode = "200";

  @After
  public void resetResponse() {
    testResponseBuilder.clearBuilder();
  }

  @Test
  public void clonesBuilderInstanceTest() {
    ResponseBuilder testClone = testResponseBuilder.clone();
    assertEquals(HttpResponseBuilder.class, testClone.getClass());
  }

  @Test
  public void getsStatusTest() {
    byte[] status = testResponseBuilder.getStatus(okStatusCode);
    String expectedHeader = "HTTP/1.1 200 OK";
    assertEquals(expectedHeader, new String(status));
  }

  @Test
  public void addStatusTest() {
    testResponseBuilder.addStatus(okStatusCode);
    String response = (new String (testResponseBuilder.getResponse()));
    String expectedResponse = "HTTP/1.1 200 OK" + CRLF;
    assertEquals(expectedResponse, response);
  }

  @Test
  public void addStatusAndHeaderTest() {
    String header ="Content-Type: ";
    String value = "text/plain";
    testResponseBuilder.addStatus(okStatusCode);
    testResponseBuilder.addHeader(header,value);
    String response = (new String (testResponseBuilder.getResponse()));
    String expectedResponse = "HTTP/1.1 200 OK"+ CRLF + "Content-Type: text/plain"+ CRLF;
    assertEquals(expectedResponse, response);
  }

  @Test
  public void addBodyTest() {
    byte[] body = ("Hello World").getBytes();
    String header ="Content-Length: ";
    String value = Integer.toString(body.length);
    testResponseBuilder.addStatus(okStatusCode);
    testResponseBuilder.addHeader(header,value);
    testResponseBuilder.addBody(body);
    String response = (new String (testResponseBuilder.getResponse()));
    String expectedResponse = "HTTP/1.1 200 OK" + CRLF + "Content-Length: 11" + CRLF + CRLF + "Hello World";
    assertEquals(expectedResponse, response);
  }

  @Test
  public void clearBuilderTest() {
    String notFoundStatusCode = "404";
    byte[] body = ("First Response").getBytes();
    String header ="Content-Length: ";
    String value = Integer.toString(body.length);
    testResponseBuilder.addStatus(okStatusCode);
    testResponseBuilder.addHeader(header,value);
    testResponseBuilder.addBody(body);
    testResponseBuilder.clearBuilder();
    testResponseBuilder.addStatus(notFoundStatusCode);
    String response = (new String (testResponseBuilder.getResponse()));
    String expectedResponse = "HTTP/1.1 404 Not Found"+ CRLF;
    assertEquals(expectedResponse, response);
  }
}
