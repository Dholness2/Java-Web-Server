package com.javawebserver.app.Builds;

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
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import com.javawebserver.app.Request;
import com.javawebserver.app.Responder;
import com.javawebserver.app.RouteBuilder;
import com.javawebserver.app.StatusCodes;
import com.javawebserver.app.serverBuilders.ServerBuilder;
import com.javawebserver.app.serverBuilders.SimpleServerBuilder;
import com.javawebserver.app.Server;

import java.io.IOException;
import java.util.HashMap;

public class CobSpecServerBuild {
  private String workingDirectory = System.getProperty("user.dir");
  private String loggerPath = workingDirectory + "/logs";
  private RouteBuilder routeBuilder;
  private ServerBuilder serverBuilder;
  private ResponseBuilder responseBuilder;

  public CobSpecServerBuild(ServerBuilder serverBuilder, ResponseBuilder responseBuilder, RouteBuilder routeBuilder) {
    this.responseBuilder = responseBuilder;
    this.serverBuilder = serverBuilder;
    this.routeBuilder = routeBuilder;
  }

  public Server getServer(int port, String directory) throws Exception  {
    this.routeBuilder.buildStandardRoutes(directory);
    addCustomRoutes(port, directory);
    this.serverBuilder.setDirectory(directory);
    this.serverBuilder.setLoggerDirectory(this.loggerPath);
    this.serverBuilder.setPort(port);
    this.serverBuilder.addRoutes(this.routeBuilder.getRoutes());
    return this.serverBuilder.buildServer();
  }

  public RouteBuilder getRouteBuilder() {
    return this.routeBuilder;
  }

  public void addCustomRoutes(int port, String directory) {
    String status = "ok";
    GetDirectory rootDirectory = new GetDirectory(directory, responseBuilder.clone());
    rootDirectory.setRootDirectory(true);
    boolean unprotected = false;
    boolean protectedRoute = true;

    routeBuilder.addRoute("POST /file1", new PutPost(responseBuilder.clone(), "/file1", directory, new FileEditor()));
    routeBuilder.addRoute("GET /", rootDirectory);
    routeBuilder.addRoute("PUT /", new PutPost(responseBuilder.clone(), "/", directory, new FileEditor()));
    routeBuilder.addRoute("PUT /text-file.txt", new PutPost(responseBuilder.clone(), "text", directory, new FileEditor()));
    routeBuilder.addRoute("OPTIONS /method_options", new Options(responseBuilder.clone(), new String [] {"GET","HEAD","POST","OPTIONS","PUT"}));
    routeBuilder.addRoute("GET /method_options", new Get(responseBuilder.clone()));
    routeBuilder.addRoute("POST /method_options", new PutPost(responseBuilder.clone(), "/method", directory, new FileEditor()));
    routeBuilder.addRoute("HEAD /method_options", new Head(responseBuilder.clone()));
    routeBuilder.addRoute("PUT /method_options", new PutPost(responseBuilder.clone(), "/method", directory, new FileEditor()));
    routeBuilder.addRoute("GET /redirect", new GetRedirect(new StatusCodes(), port, "http://localhost:"));
    routeBuilder.addRoute("GET /parameters?", new Params(responseBuilder.clone(), "parameters?"));
    routeBuilder.addRoute("GET /form", new GetForm(responseBuilder.clone(), "/form", "text/plain", workingDirectory, unprotected, "My=Data"));
    routeBuilder.addRoute("POST /form", new PutPost(responseBuilder.clone(), "/form", workingDirectory, new FileEditor()));
    routeBuilder.addRoute("PUT /form", new PutPost(responseBuilder.clone(), "/form", workingDirectory, new FileEditor()));
    routeBuilder.addRoute("DELETE /form", new Delete(responseBuilder.clone(), "/form", workingDirectory, new FileEditor()));
    routeBuilder.addRoute("GET /partial_content.txt", new GetPartialContent(new StatusCodes(), "/partial_content.txt", "text/plain", directory));
    routeBuilder.addRoute("PATCH /patch-content.txt", new Patch(new StatusCodes(), "/patch-content.txt", directory, new FileEditor(), new SHA1Encoder()));
    routeBuilder.addRoute("GET /logs", new Get(responseBuilder.clone(), "/logs", "text/plain", workingDirectory, protectedRoute));
  }
}
