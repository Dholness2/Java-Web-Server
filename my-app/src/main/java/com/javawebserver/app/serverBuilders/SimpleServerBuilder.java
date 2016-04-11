package com.javawebserver.app.serverBuilders;

import com.javawebserver.app.Server;
import com.javawebserver.app.Responder;
import com.javawebserver.app.workers.Worker;
import com.javawebserver.app.workers.ClientWorkerService;
import com.javawebserver.app.helpers.Logger;
import com.javawebserver.app.RouteBuilder;
import com.javawebserver.app.serverSockets.ServerSocket;
import com.javawebserver.app.serverSockets.ServerSocketWrapper;
import com.javawebserver.app.responses.Response;

import java.io.IOException;

import java.util.HashMap;

public class SimpleServerBuilder {
  private String directory = System.getProperty("user.dir") + "/logs";
  private RouteBuilder routes = new RouteBuilder(this.directory);

  public SimpleServerBuilder() {}

  public Server getServer(int port) throws IOException  {
    Responder responder = new Responder(routes.getRoutes());
    ServerSocket serverSocket = new ServerSocketWrapper(port);
    Logger logger = new Logger(this.directory);
    ClientWorkerService clientWorker = new ClientWorkerService(responder, logger);
    Server server = new Server(clientWorker, serverSocket);
    return server;
  }

  public void addRoute(String route, Response response) {
    this.routes.addRoute(route,response);
  }

  public HashMap<String, Response>  getRoutes() {
    return this.routes.getRoutes();
  }
}
