package com.javawebserver.app;

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

import com.javawebserver.app.helpers.FileEditor;

import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import com.javawebserver.app.sockets.Socket;
import com.javawebserver.app.sockets.ClientSocket;

import com.javawebserver.app.serverSockets.ServerSocketWrapper;
import com.javawebserver.app.serverSockets.ServerSocket;

import com.javawebserver.app.Request;

import com.javawebserver.app.encoders.SHA1Encoder;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class App {
  private static final int PORT_INDEX = 0;
  private static final int DIR_INDEX = 1;
  private static final String [] KEYS = {"-p","-d"};

  private static String currentDirectory = System.getProperty("user.dir");
  private static String loggerPath = currentDirectory + "/logs";
  private static StatusCodes status = new StatusCodes ();

  public static void addCustomRoutes (RouteConfiguration routes, String directory, int port) {
    boolean unprotected = false;
    boolean protectedRoute = true;

    routes.addRoute("POST /file1",new PutPost(status.OK,"/file1",directory,new FileEditor()));
    routes.addRoute("GET /", new GetDirectory(status,directory));
    routes.addRoute("PUT /", new PutPost(status.OK, "/",directory,new FileEditor()));
    routes.addRoute("Put /text-file.txt", new PutPost(status.OK,"text",directory,new FileEditor()));
    routes.addRoute("OPTIONS /method_options", new Options(new HttpResponseBuilder(), new String [] {"GET","HEAD","POST","OPTIONS","PUT"}));
    routes.addRoute("GET /method_options", new Get(status.OK));
    routes.addRoute("POST /method_options", new PutPost(status.OK,"/method",directory,new FileEditor()));
    routes.addRoute("HEAD /method_options", new Head(status.OK));
    routes.addRoute("PUT /method_options", new PutPost(status.OK,"/method",directory,new FileEditor()));
    routes.addRoute("GET /redirect", new GetRedirect(status,port,"http://localhost:"));
    routes.addRoute("GET /parameters?", new Params(new HttpResponseBuilder(),"parameters?"));
    routes.addRoute("GET /form", new GetForm(new HttpResponseBuilder(),"/form", "text/plain", currentDirectory, unprotected, "My=Data"));
    routes.addRoute("POST /form", new PutPost(status.OK,"/form",currentDirectory,new FileEditor()));
    routes.addRoute("PUT /form", new PutPost(status.OK,"/form",currentDirectory,new FileEditor()));
    routes.addRoute("DELETE /form", new Delete(status.OK,"/form",currentDirectory,new FileEditor()));
    routes.addRoute("GET /partial_content.txt", new GetPartialContent(status,"/partial_content.txt","text/plain",directory));
    routes.addRoute("PATCH /patch-content.txt", new Patch(status,"/patch-content.txt",directory,new FileEditor(), new SHA1Encoder()));
    routes.addRoute("GET /logs", new Get(new HttpResponseBuilder(),"/logs","text/plain",currentDirectory, protectedRoute));
  }

  public static void main( String[] args) throws Exception {
    Map<String, String> Options = OptionsParser.parse(args,KEYS);
    String directory = Options.get(KEYS[DIR_INDEX]);
    int port = Integer.parseInt(Options.get(KEYS[PORT_INDEX]));

    RouteConfiguration routes  = new RouteConfiguration();
    routes.buildStandardRoutes(directory);
    addCustomRoutes(routes,directory,port);

    Responder responder = new Responder(routes.getRoutes());
    ServerSocket serverSocket = new ServerSocketWrapper(port);
    Logger logger = new Logger(loggerPath);
    Server server = new Server(port,serverSocket,responder,logger);

    server.run();
  }
}