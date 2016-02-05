 package com.JavaWebServer.app;

 import org.junit.*;
 import org.junit.Assert;
 import  java.util.Map;

public class RequestParserTest {

   @Test
   public void parse () {
    String [] methods = {"GET"};
     RequestParser testParser =  new RequestParser(methods);
     String testRequest = "GET / HTTP/1.0";
     Map parsedRequest  = testParser.parse(testRequest.split(" "));
     Assert.assertEquals(parsedRequest.get("method"), "GET");
   }
}
