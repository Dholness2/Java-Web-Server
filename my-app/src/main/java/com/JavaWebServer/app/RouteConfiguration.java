package com.JavaWebServer.app;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.responses.Get;
import com.JavaWebServer.app.responses.GetDirectory;
import com.JavaWebServer.app.responseBuilders.HttpResponseBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class RouteConfiguration {
  private String directory;
  private boolean enableProtection = false;
  private HashMap<String,Response> routes = new HashMap();

  public RouteConfiguration () {}

  public void addRoute (String path, Response response) {
    this.routes.put(path, response);
  }

  public HashMap<String,Response> getRoutes () {
    return this.routes;
  }

  public ArrayList<File> getFileList (String path) {
    File currentDirectory = new File(path);
    File [] files = currentDirectory.listFiles();
    return new ArrayList<File>(Arrays.asList(files));
  }

  public void buildStandardRoutes (String directoryPath) throws Exception {
    ArrayList<File> filesList = getFileList(directoryPath);
    for (File file : filesList) {
      if (! file.isHidden()) {
        if (file.isFile()){
          buildFileRoute(file, directoryPath);
        }else if (file.isDirectory()) {
          buildDirectoryRoute(file);
        }
      }
    }
  }
    private void buildFileRoute (File file, String directoryPath) throws Exception {
      String fileName = "/"+ file.getName();
      String pathKey = "GET " + fileName;
      String fileType = getFileType(file.toPath());
      addRoute(pathKey, (new Get(new HttpResponseBuilder(), fileName, fileType, fileDirectory(file), enableProtection)));
    }

    private void buildDirectoryRoute (File directory) throws Exception {
      String directoryName = "/"+ directory.getName();
      String pathKey = "GET "+ directoryName;
      String directoryPath = directory.getAbsolutePath();
      addRoute(pathKey, new GetDirectory (new StatusCodes(), directoryPath));
      buildStandardRoutes(directoryPath);
    }

    public String getFileType (Path path) throws Exception {
     return Files.probeContentType(path);
    }

   private String fileDirectory (File file) {
     String path =  file.getAbsolutePath();
     return path.substring(0, path.lastIndexOf(File.separator));
   }
}
