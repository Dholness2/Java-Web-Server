package com.javawebserver.app.builds;

import com.javawebserver.app.Builds.CobSpecServerBuild;
import com.javawebserver.app.Server;
import com.javawebserver.app.serverBuilders.ServerBuilder;
import com.javawebserver.app.serverBuilders.SimpleServerBuilder;
import com.javawebserver.app.RouteBuilder;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
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

public class CobSpecServerBuildTest {
  private String directory = System.getProperty("user.dir") + "/public";
  private ResponseBuilder responseBuilder = new HttpResponseBuilder();
  private RouteBuilder routeBuilder = new RouteBuilder(directory, responseBuilder);
  private SimpleServerBuilder serverBuilder = new SimpleServerBuilder();
  private CobSpecServerBuild testBuild = new CobSpecServerBuild(serverBuilder, responseBuilder, routeBuilder);
  private int port = 8000;

  @Test
  public void getsServerTest() throws Exception {
    Server testServer = testBuild.getServer(this.port, this.directory);
    assertEquals(Server.class, testServer.getClass());
  }

  @Test
  public void loadsCustomRoutesTest() throws Exception {
    testBuild.addCustomRoutes(port, directory);
    RouteBuilder builder = testBuild.getRouteBuilder();
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
