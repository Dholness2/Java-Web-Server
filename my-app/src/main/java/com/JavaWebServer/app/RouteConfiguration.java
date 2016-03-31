package com.JavaWebServer.app;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.responses.Get;
import com.JavaWebServer.app.responses.GetDirectory;

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
  private boolean enableProtection = false;
  private String getRoute = "GET ";
    public RouteConfiguration () {}

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

  public void buildStandardroutes(String directory) throws Exception {
    ArrayList<File> filesList = getPaths(directory);
    for (File file : filesList) {
      if (! file.isHidden()) {
        if(file.isFile()){
          String fileName = "/"+ file.getName();
          String pathKey = this.getRoute + fileName;
          String fileType = getFileType(file.toPath());
          addRoute(pathKey,(new Get (new StatusCodes(),fileName,fileType,directory,enableProtection)));
        }else if (file.isDirectory()) {
          String directoryName = "/"+ file.getName();
          String pathKey = "/"+ (file.getParentFile().getName()) + directoryName;
          String directoryPath = file.getPath();
          addRoute(pathKey,new GetDirectory (new StatusCodes(),directoryPath));
        }
      }
    }
  }

  public String getFileType(Path path)throws Exception {
    return Files.probeContentType(path);
  }
}
