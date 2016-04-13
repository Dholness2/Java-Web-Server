package com.javawebserver.app.responses;

import org.junit.Test;
import org.junit.Before;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.GetRedirect;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.FileEditor;

import static org.junit.Assert.assertEquals;

public class GetRedirectTest {
  private String locationHeader = "Location: http://localhost:5000/";
  private String CRLF = System.getProperty("line.separator");
  private int port = 5000;
  private Request request = new Request();
  private ResponseBuilder responseBuilder = new HttpResponseBuilder();
  private String serverName = "http://localhost:";

  @Test
  public void handleRequestTest() {
    request.setMessage("GET /redirect HTTP/1.1");
    String expectedResponse = "HTTP/1.1 302 Found" + CRLF + locationHeader + CRLF;
    Response testRedirect = new GetRedirect(responseBuilder, port, serverName);
    String response = new String (testRedirect.handleRequest(request));
    assertEquals(expectedResponse, response);
  }
}
