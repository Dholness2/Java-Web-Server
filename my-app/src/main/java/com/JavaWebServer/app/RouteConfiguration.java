package com.JavaWebServer.app;

import com.JavaWebServer.app.responses.Response;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class RouteConfiguration {
  private HashMap<String,Response> routes = new HashMap();
  private String directory;

  public RouteConfiguration () {
  
  }

  public void addRoute(String path ,Response response) {
    this.routes.put(path,response);
  }

  public HashMap<String,Response> getRoutes() {
    return this.routes;
  }

  public ArrayList<File> getPaths(String path) {
    File currentDirectory = new File(path);
    File [] files = currentDirectory.listFiles();
    return new ArrayList<File>(Arrays.asList(files));
  }
}
