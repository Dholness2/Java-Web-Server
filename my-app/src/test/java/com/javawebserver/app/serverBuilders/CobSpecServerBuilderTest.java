package com.javawebserver.app.serverBuilders;

import com.javawebserver.app.serverBuilders.CobSpecServerBuilder;
import com.javawebserver.app.Server;
import com.javawebserver.app.RouteBuilder;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Get;

import java.io.IOException;

import java.util.HashMap;
import java.util.Set;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class CobSpecServerBuilderTest {
  private String directory = System.getProperty("user.dir") + "/public";
  private CobSpecServerBuilder testBuilder = new CobSpecServerBuilder(directory);
  private int port = 8000;

  @Test
  public void getsServerTest() throws Exception {
    System.out.println(directory);
    Server testServer = testBuilder.getServer(port, directory);
    assertEquals(Server.class, testServer.getClass());
  }

  @Test
  public void getsRouteBuilderTest() {
    RouteBuilder testRouteBuilder = testBuilder.getRouteBuilder();
    assertEquals(RouteBuilder.class, testRouteBuilder.getClass());
  }

  @Test
  public void loadsCustomRoutesTest() {
    testBuilder.addCustomRoutes(port, directory);
    RouteBuilder builder = testBuilder.getRouteBuilder();
    Set<String> routeList = builder.getRoutes().keySet();
    String [] customRoutes = {"POST /file1", "GET /", "PUT /",
                              "PUT /text-file.txt","OPTIONS /method_options",
                              "GET /method_options","POST /method_options",
                              "HEAD /method_options","PUT /method_options",
                              "GET /redirect","GET /parameters?",
                              "GET /form", "POST /form","PUT /form",
                              "DELETE /form", "GET /partial_content.txt",
                              "PATCH /patch-content.txt","GET /logs"};
    for (String route: customRoutes) {
      assertThat(routeList, hasItem(route));
    }
  }
}
