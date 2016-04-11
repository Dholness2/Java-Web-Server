package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Head;
import com.javawebserver.app.Request;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HeadTest {

  @Test
  public void handleSimepleRequestTest() {
    String simpleResponse = "HTTP/1.1 200 OK";
    Head testGet = new Head(simpleResponse);
    byte [] response = testGet.handleRequest(new Request());
    assertEquals(simpleResponse, new String(response));
  }
}
