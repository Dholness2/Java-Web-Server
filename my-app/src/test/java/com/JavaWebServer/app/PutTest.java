package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
public class PutTest {

  @Test
  public void handleRequestTest() {
    StatusCodes codes = new StatusCodes();
    Request testrequest = new Request();
    testrequest.setMessage("PUT / HTTP/1.1");
    RestMethod testPut = new Put(codes.OK);
    String response = new String (testPut.handleRequest(testrequest));
    assertEquals(codes.OK , response);
  }
}
