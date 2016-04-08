package com.javawebserver.app.serverBuilders;

import com.javawebserver.app.responses.Get;
import com.javawebserver.app.responses.GetForm;
import com.javawebserver.app.responses.PutPost;
import com.javawebserver.app.responses.GetDirectory;
import com.javawebserver.app.responses.GetPartialContent;
import com.javawebserver.app.responses.GetRedirect;
import com.javawebserver.app.responses.Params;
import com.javawebserver.app.responses.Delete;
import com.javawebserver.app.responses.Head;
import com.javawebserver.app.responses.Patch;
import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Options;
import com.javawebserver.app.encoders.SHA1Encoder;

import com.javawebserver.app.helpers.FileEditor;
import com.javawebserver.app.helpers.Logger;

import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.sockets.ClientSocket;

import com.javawebserver.app.serverSockets.ServerSocketWrapper;
import com.javawebserver.app.serverSockets.ServerSocket;

import com.javawebserver.app.Request;
import com.javawebserver.app.Responder;
import com.javawebserver.app.RouteBuilder;
import com.javawebserver.app.StatusCodes;
import com.javawebserver.app.Server;

import java.io.IOException;
import java.util.HashMap;

public class CobSpecServerBuilder {
  private String workingDirectory = System.getProperty("user.dir");
  private String loggerPath = workingDirectory + "/logs";
  private String directoryPath;
  private RouteBuilder routeBuilder;

  public CobSpecServerBuilder(String directoryPath) {
    this.directoryPath = directoryPath;
    routeBuilder =  new RouteBuilder(directoryPath);
  }

  public Server getServer(int port, String directory) throws Exception  {
    routeBuilder.buildStandardRoutes(directory);
    addCustomRoutes(port, directory);
    Responder responder = new Responder(routeBuilder.getRoutes());
    ServerSocket serverSocket = new ServerSocketWrapper(port);
    Logger logger = new Logger(loggerPath);
    Server server = new Server(port, serverSocket,responder,logger);
    return server;
  }

  public RouteBuilder getRouteBuilder() {
    return this.routeBuilder;
  }

  public void addCustomRoutes(int port, String directory) {
    StatusCodes status = new StatusCodes();
    GetDirectory rootDirectory = new GetDirectory(directory);
    rootDirectory.setRootDirectory(true);
    boolean unprotected = false;
    boolean protectedRoute = true;

    routeBuilder.addRoute("POST /file1",new PutPost(status.OK,"/file1",directory,new FileEditor()));
    routeBuilder.addRoute("GET /", rootDirectory);
    routeBuilder.addRoute("PUT /", new PutPost(status.OK, "/",directory,new FileEditor()));
    routeBuilder.addRoute("PUT /text-file.txt", new PutPost(status.OK,"text",directory,new FileEditor()));
    routeBuilder.addRoute("OPTIONS /method_options", new Options(new HttpResponseBuilder(), new String [] {"GET","HEAD","POST","OPTIONS","PUT"}));
    routeBuilder.addRoute("GET /method_options", new Get(status.OK));
    routeBuilder.addRoute("POST /method_options", new PutPost(status.OK,"/method", directory,new FileEditor()));
    routeBuilder.addRoute("HEAD /method_options", new Head(status.OK));
    routeBuilder.addRoute("PUT /method_options", new PutPost(status.OK,"/method",directory,new FileEditor()));
    routeBuilder.addRoute("GET /redirect", new GetRedirect(status,port,"http://localhost:"));
    routeBuilder.addRoute("GET /parameters?", new Params(new HttpResponseBuilder(),"parameters?"));
    routeBuilder.addRoute("GET /form", new GetForm(new HttpResponseBuilder(),"/form", "text/plain", workingDirectory, unprotected, "My=Data"));
    routeBuilder.addRoute("POST /form", new PutPost(status.OK,"/form", workingDirectory,new FileEditor()));
    routeBuilder.addRoute("PUT /form", new PutPost(status.OK,"/form", workingDirectory,new FileEditor()));
    routeBuilder.addRoute("DELETE /form", new Delete(status.OK,"/form", workingDirectory,new FileEditor()));
    routeBuilder.addRoute("GET /partial_content.txt", new GetPartialContent(status,"/partial_content.txt","text/plain",directory));
    routeBuilder.addRoute("PATCH /patch-content.txt", new Patch(status,"/patch-content.txt", directory,new FileEditor(), new SHA1Encoder()));
    routeBuilder.addRoute("GET /logs", new Get(new HttpResponseBuilder(),"/logs","text/plain", workingDirectory, protectedRoute));
  }
}
