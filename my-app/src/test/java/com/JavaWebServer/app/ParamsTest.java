package com.JavaWebServer.app;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParamsTest {
  private StatusCodes codes = new StatusCodes();
  private String CRLF = "\r\n";
  private String request = "GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1";


  private Request testRequest;
  private Params testParams;

  @Before
  public void buildObjects () {
    testRequest = new Request();
    testRequest.setMessage (request);
    String params = testRequest.getParams();
     testParams = new Params(codes.OK,"paramaters?");
  }

  @Test
  public void handleRequest() {
    String content = "Content-Length:97";
    String type = "Content-Type: text/plain";
    String variableOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
    String variableTwo = "variable_2 = stuff";
    String expectedResponse = codes.OK +CRLF+type+CRLF+content+CRLF+CRLF+variableOne+CRLF+variableTwo;
    assertEquals(expectedResponse, new String(testParams.handleRequest(testRequest)));
  }
}
