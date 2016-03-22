package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertEquals;

public class httpResponseTest {
 private Response testResponse = new httpResponse(); 
 private String CRLF = System.getProperty("line.separator");


 @After
  public void resetResponse(){
    testResponse = new httpResponse();
  }

 @Test
 public void addStatusTest(){
   String OK = "OK";
   testResponse.addStatus(OK);
   String response = (new String (testResponse.getResponse()));
   String expectedResponse = "HTTP/1.1 200 OK"+CRLF;
   assertEquals(expectedResponse, response);
 }

 @Test
 public void addStatusAndHeaderTest(){
   String OK = "OK";
   String header ="Content-Type: ";
   String value = "text/plain";
   testResponse.addStatus(OK);
   testResponse.addHeader(header,value);
   String response = (new String (testResponse.getResponse()));
   String expectedResponse = "HTTP/1.1 200 OK"+CRLF+"Content-Type: text/plain"+CRLF;
   assertEquals(expectedResponse, response);
 }

 @Test
 public void addBodyTest(){
   String OK = "OK";
   byte[] body = ("Hello World").getBytes();
   String header ="Content-Length: ";
   String value = Integer.toString(body.length);
   testResponse.addStatus(OK);
   testResponse.addHeader(header,value);
   testResponse.addBody(body);
   String response = (new String (testResponse.getResponse()));
   String expectedResponse = "HTTP/1.1 200 OK"+CRLF+"Content-Length: 11"+CRLF+CRLF+CRLF+"Hello World";
   assertEquals(expectedResponse, response);
}





}
