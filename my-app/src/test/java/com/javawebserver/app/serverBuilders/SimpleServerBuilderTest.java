package com.javawebserver.app.serverBuilders;

import com.javawebserver.app.serverBuilders.SimpleServerBuilder;
import com.javawebserver.app.Server;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Get;

import java.io.IOException;

import java.util.HashMap;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class SimpleServerBuilderTest {
  private SimpleServerBuilder testBuilder = new SimpleServerBuilder();

  @Test
  public void getServerTest() throws IOException {
    SimpleServerBuilder testBuilder = new SimpleServerBuilder();
    int port = 7000;
    Server testServer = testBuilder.getServer(port);
    assertEquals(Server.class, testServer.getClass());
  }

  @Test
  public void addRouteTest() {
    String routeKey = "GET /";
    testBuilder.addRoute("GET /", new Get("testResponse"));
    HashMap<String, Response> routes = testBuilder.getRoutes();
    assertEquals(Get.class, routes.get(routeKey).getClass());
  }
}
