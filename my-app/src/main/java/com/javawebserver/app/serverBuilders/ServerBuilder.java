package com.javawebserver.app.serverBuilders;

import com.javawebserver.app.Request;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.Server;
import java.io.IOException;

import java.util.HashMap;

public interface ServerBuilder {
  public void setDirectory(String path);
  public void setPort(int port);
  public void setLoggerDirectory(String path);
  public void addRoutes(HashMap<String, Response> routes);
  public Server buildServer() throws IOException;
}

