package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class OptionsTest {

  @Test
  public void handleRequestTest() {
    Request testrequest = new Request();
    testrequest.setMessage("OPTIONS / HTTP/1.1");
    RestMethod testOptions = new Options();
    assertEquals("HTTP/1.1 200 ok"+ System.lineSeparator() +"Allow: GET,HEAD,POST,OPTIONS,PUT", testOptions.handleRequest(testrequest));
  }
}
