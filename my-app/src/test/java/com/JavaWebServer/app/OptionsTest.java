package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class OptionsTest {

  @Test
  public void handleRequestTest() {
    StatusCodes code = new StatusCodes();
    Request testrequest = new Request();
    testrequest.setMessage("OPTIONS / HTTP/1.1");
    RestMethod testOptions = new Options(code.OK);
    String response = new String (testOptions.handleRequest(testrequest));
    assertEquals(code.OK+ System.lineSeparator() +"Allow: GET,HEAD,POST,OPTIONS,PUT", response);
  }
}
