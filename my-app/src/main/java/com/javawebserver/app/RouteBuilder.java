package com.javawebserver.app;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.responses.GetDirectory;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RouteBuilder {
  private boolean enableProtection = false;
  private HashMap<String,Response> routes = new HashMap();
  private String rootDirectory;
  private ResponseBuilder responseBuilder;

  public RouteBuilder(String rootDirectory, ResponseBuilder responseBuilder) {
    this.rootDirectory = rootDirectory;
    this.responseBuilder = responseBuilder;
  }

  public void addRoute(String path, Response response) {
    this.routes.put(path, response);
  }

  public HashMap<String,Response> getRoutes() {
    return this.routes;
  }

  public ArrayList<File> getFileList(String path) {
    File currentDirectory = new File(path);
    File[] files = currentDirectory.listFiles();
    return new ArrayList<File>(Arrays.asList(files));
  }

  public void buildStandardRoutes(String directoryPath) throws Exception {
    ArrayList<File> filesList = getFileList(directoryPath);
    for (File file : filesList) {
      if (! file.isHidden()) {
        assembleResponses(file, directoryPath);
      }
    }
  }

  private void assembleResponses(File file, String directoryPath) throws Exception {
    if (file.isFile()) {
      buildFileRoute(file, directoryPath);
    }else if (file.isDirectory()) {
      buildDirectoryRoute(file);
    }
  }

  private void buildFileRoute(File file, String directoryPath) throws Exception {
    String fileName = "/"+ file.getName();
    String routeKey = getRouteKey(file, fileName);
    String fileType = getFileType(file.toPath());
    addRoute(routeKey, (new Get(this.responseBuilder.clone(), fileName, fileType, fileDirectory(file), enableProtection)));
  }

  private void buildDirectoryRoute(File directory) throws Exception {
    String directoryName = "/"+ directory.getName();
    String routeKey = getRouteKey(directory, directoryName);
    String directoryPath = directory.getAbsolutePath();
    addRoute(routeKey, new GetDirectory(directoryPath, this.responseBuilder.clone(), this.rootDirectory));
    buildStandardRoutes(directoryPath);
  }

  private String getRouteKey(File file, String fileName) {
    return ("GET "+ getParentNamePath(file));
  }

  private String getParentNamePath(File file) {
    return file.getAbsolutePath().split(this.rootDirectory)[1];
  }

  public String getFileType(Path path) throws Exception {
    return Files.probeContentType(path);
  }

  private String fileDirectory(File file) {
    String path = file.getAbsolutePath();
    return path.substring(0, path.lastIndexOf(File.separator));
  }
}
