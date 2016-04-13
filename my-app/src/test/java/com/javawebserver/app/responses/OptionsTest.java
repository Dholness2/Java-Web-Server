package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Options;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;

import java.util.HashMap;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class OptionsTest {
  private Request testRequest = new Request();
  private static final String CRLF = System.lineSeparator();

  @Test
  public void handlesOptionsRequestTest() {
    testRequest.setMessage("OPTIONS /method_options HTTP/1.1");
    String[] options = new String[] {"GET","HEAD","POST","OPTIONS","PUT"};
    Response testOptions = new Options (new HttpResponseBuilder(), options);
    String response = new String (testOptions.handleRequest(testRequest));
    String expectedResponse = "HTTP/1.1 200 OK" + CRLF + "Allow: GET,HEAD,POST,OPTIONS,PUT," + CRLF;
    assertEquals(expectedResponse, response);
  }
}
