package com.javawebserver.app.builds;

import com.javawebserver.app.Server;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.RouteBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.serverBuilders.ServerBuilder;
import com.javawebserver.app.serverBuilders.SimpleServerBuilder;

import java.io.IOException;

import java.util.HashMap;

public class BasicBuild {
  private String directory = System.getProperty("user.dir") + "/logs";
  private RouteBuilder routes = new RouteBuilder(this.directory, new HttpResponseBuilder());
  private ServerBuilder serverBuilder;

  public BasicBuild(ServerBuilder serverBuilder) {
    this.serverBuilder = serverBuilder;
  }

  public Server getServer(int port) throws IOException  {
    this.serverBuilder.setDirectory(directory);
    this.serverBuilder.setLoggerDirectory(directory);
    this.serverBuilder.setPort(port);
    this.serverBuilder.addRoutes(getRoutes());
    return this.serverBuilder.buildServer();
  }

  public void addRoute(String route, Response response) {
    this.routes.addRoute(route,response);
  }

  public HashMap<String, Response>  getRoutes() {
    return this.routes.getRoutes();
  }
}
