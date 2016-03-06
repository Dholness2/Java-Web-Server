package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class OptionsTest {

  @Test
  public void handleRequestTest() {
    StatusCodes code = new StatusCodes();
    RestMethod testOptions = new Options(code.OK);
    String response = new String (testOptions.handleRequest());
    assertEquals(code.OK+ System.lineSeparator() +"Allow: GET,HEAD,POST,OPTIONS,PUT", response);
  }
}
