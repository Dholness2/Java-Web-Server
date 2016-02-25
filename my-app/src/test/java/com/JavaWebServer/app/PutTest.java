package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
public class PutTest {

  @Test
  public void handleRequestTest() {
    StatusCodes codes = new StatusCodes();
    RestMethod testPut = new Put(codes.OK);
    String response = new String (testPut.handleRequest());
    assertEquals(codes.OK , response);
  }
}
