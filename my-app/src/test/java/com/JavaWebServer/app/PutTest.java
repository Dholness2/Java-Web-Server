package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
public class PutTest {

  @Test
  public void handleRequestTest() {
    Request testrequest = new Request();
    testrequest.setMessage("PUT / HTTP/1.1");
    RestMethod testPut = new Put("HTTP/1.1 200 OK");
    assertEquals("HTTP/1.1 200 OK", testPut.handleRequest(testrequest));
  }
}
