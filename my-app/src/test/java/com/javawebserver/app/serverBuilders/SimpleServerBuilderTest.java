package com.javawebserver.app.serverBuilders;

import com.javawebserver.app.serverBuilders.SimpleServerBuilder;
import com.javawebserver.app.Server;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.RouteBuilder;

import java.io.IOException;

import java.util.HashMap;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class SimpleServerBuilderTest {
  private SimpleServerBuilder testBuilder = new SimpleServerBuilder();
  private ResponseBuilder responseBuilder = new HttpResponseBuilder();
  private String directory = "/local/foo";
  private RouteBuilder routeBuilder = new RouteBuilder(directory, responseBuilder);

  @Before
  public void buildRoutes() {
    routeBuilder.addRoute("GET /", new Get(this.responseBuilder));
  }

  @Test
  public void buildServerTest() throws IOException {
    SimpleServerBuilder testBuilder = new SimpleServerBuilder();
    testBuilder.setPort(7120);
    testBuilder.setDirectory(this.directory);
    testBuilder.setLoggerDirectory("/local/logger");
    HashMap<String, Response> routes = testBuilder.getRoutes();
    testBuilder.addRoutes(routes);
    Server testServer = testBuilder.buildServer();
    assertEquals(Server.class, testServer.getClass());
  }
}
