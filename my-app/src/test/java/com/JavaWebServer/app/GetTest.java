package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class GetTest {

  @Test
  public void handleRequestTest() {
    StatusCodes codes = new StatusCodes();
    Request testRequest = new Request();
    testRequest.setMessage("GET / HTTP/1.1");
    Get testGet = new Get(codes.OK);
    String response = testGet.handleRequest(testRequest);
    assertEquals(codes.OK, response);
  }
}
