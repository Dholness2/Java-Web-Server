package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
public class PutTest {

  @Test
  public void handleRequestTest() {
    Request testrequest = new Request();
    testrequest.setMessage("PUT / htttp1.1");
    RestMethod testPut = new Put();
    assertEquals("HTTP/1.1 200 ok", testPut.handleRequest(testrequest));
  }

  @Test 
  public void setNextMethodTest() {
    RestMethod testPut = new Put();
    RestMethod testGet = new GetMethod();
    testPut.setNextMethod(testGet);
  }
}
