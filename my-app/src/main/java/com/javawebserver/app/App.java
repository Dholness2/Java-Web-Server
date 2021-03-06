package com.javawebserver.app;

import com.javawebserver.app.builds.CobSpecServerBuild;
import com.javawebserver.app.serverBuilders.ServerBuilder;
import com.javawebserver.app.serverBuilders.SimpleServerBuilder;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.RouteBuilder;

import java.util.Scanner;

public class App {
  private static String directory;
  private static int port;

  public static void setArgs(String[] args) {
    Scanner consoleScanner =  new Scanner(System.in);
    OptionsParser parser = new OptionsParser(consoleScanner, args);
    directory = parser.getDirectory();
    port = parser.getPort();
  }

  public static void main( String[] args) throws Exception {
    setArgs(args);
    ResponseBuilder responseBuilder = new HttpResponseBuilder();
    RouteBuilder routeBuilder = new RouteBuilder(directory, responseBuilder);
    ServerBuilder simpleServerBuilder = new SimpleServerBuilder();
    CobSpecServerBuild cobSpecBuild = new CobSpecServerBuild(simpleServerBuilder, responseBuilder, routeBuilder);
    Server appServer = cobSpecBuild.getServer(port, directory);
    appServer.run();
  }
}
