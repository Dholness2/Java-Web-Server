package com.javawebserver.app.serverBuilders;

import com.javawebserver.app.serverBuilders.ServerBuilder;
import com.javawebserver.app.Server;
import com.javawebserver.app.Responder;
import com.javawebserver.app.workers.Worker;
import com.javawebserver.app.workers.ClientWorkerService;
import com.javawebserver.app.helpers.Logger;
import com.javawebserver.app.RouteBuilder;
import com.javawebserver.app.serverSockets.ServerSocket;
import com.javawebserver.app.serverSockets.ServerSocketWrapper;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import java.util.HashMap;

import java.io.IOException;

public class SimpleServerBuilder implements ServerBuilder {
  private String directory;
  private String loggerDirectory;
  private int port;
  private HashMap<String, Response> routes;

  public SimpleServerBuilder() {}

  public Server buildServer() throws IOException  {
    Responder responder = new Responder(this.routes, new HttpResponseBuilder());
    Logger logger = new Logger(this.loggerDirectory);
    ClientWorkerService clientWorker = new ClientWorkerService(responder, logger);
    ServerSocket serverSocket = new ServerSocketWrapper(this.port);
    return new Server(clientWorker, serverSocket);
  }

  public void setDirectory(String path) {
    this.directory = path;
  }

  public void setLoggerDirectory(String path) {
    this.loggerDirectory = path;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void addRoutes(HashMap<String, Response> routes)  {
    this.routes = routes;
  }

  public HashMap<String, Response> getRoutes() {
    return this.routes;
  }
}
