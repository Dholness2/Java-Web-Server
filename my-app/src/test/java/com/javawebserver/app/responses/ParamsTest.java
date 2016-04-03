package com.javawebserver.app;

import java.util.HashMap;
import java.util.Map;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Params;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParamsTest {
  private String CRLF = System.getProperty("line.separator");;
  private String request = "GET /parameters?variable_1=Operators%20%3C"+
                           "%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20"+
                           "-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24"+
                           "%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22"+
                           "%3F&variable_2=stuff HTTP/1.1";
  private Request testRequest;
  private Params testParams;

  @Before
  public void buildRequiredObjects() {
    testRequest = new Request();
    testRequest.setMessage (request);
    String params = testRequest.getParams();
    testParams = new Params(new HttpResponseBuilder(), "paramaters?");
  }

  @Test
  public void handleRequest() {
    String status = "HTTP/1.1 200 OK";
    String content = "Content-Length: 96";
    String type = "Content-Type: text/plain";
    String variableOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
    String variableTwo = "variable_2 = stuff";
    String expectedResponse = status + CRLF + type + CRLF + content + CRLF + CRLF+ variableOne + CRLF + variableTwo;
    assertEquals(expectedResponse, new String(testParams.handleRequest(testRequest)));
  }
}
