package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class GetTest {

  @Test 
  public void handleRequestTest() {
    Request testrequest = new Request();
    testrequest.setMessage("GET / HTTP/1.1");
    Get testGet = new Get("HTTP/1.1 200 ok");
    String response = testGet.handleRequest(testrequest);
    assertEquals("HTTP/1.1 200 ok", response);
  }
}
