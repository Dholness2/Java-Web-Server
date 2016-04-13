package com.javawebserver.app.builds;

import com.javawebserver.app.builds.BasicBuild;
import com.javawebserver.app.serverBuilders.ServerBuilder;
import com.javawebserver.app.serverBuilders.SimpleServerBuilder;
import com.javawebserver.app.Server;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class BasicBuildTest {
  private ServerBuilder builder = new SimpleServerBuilder();
  private BasicBuild testBuild = new BasicBuild(builder);

  @Test
  public void getServerTest() throws IOException {
    int port = 7000;
    Server testServer = testBuild.getServer(port);
    assertEquals(Server.class, testServer.getClass());
  }

  @Test
  public void addRouteTest() {
    String routeKey = "GET /";
    testBuild.addRoute("GET /", new Get(new HttpResponseBuilder()));
    HashMap<String, Response> routes = testBuild.getRoutes();
    assertEquals(Get.class, routes.get(routeKey).getClass());
  }
}
