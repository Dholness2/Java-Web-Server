package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class GetTest {
  private StatusCodes codes = new StatusCodes();
  private Request testRequest = new Request();
  @Test
  public void handleRequestTest() {
    testRequest.setMessage("GET / HTTP/1.1");
    Get testGet = new Get(codes.OK);
    byte [] response = testGet.handleRequest(testRequest);
    assertEquals(codes.OK, new String(response));
  }

  @Test
  public void handleImageRequestTest() {
     String directory =  "/Users/don/desktop/Java_Web_Server/my-app/public"; 
     testRequest.setMessage("GET /image.jpeg HTTP/1.1");
     Get testGet = new Get(codes.OK,"image.jpeg","image/jpeg", directory);
     byte [] response = testGet.handleRequest(testRequest);
     assertEquals(codes.OK, new String(response));
  }
}
