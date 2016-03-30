package com.JavaWebServer.app;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.responses.GetDirectory;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class RouteConfigurationTest {
  private RouteConfiguration routeConfig;
  private Response response = new GetDirectory(new StatusCodes(),new String());
  private String path = "GET /Home";
  private Path directory;
  private Path tempHTML;
  private Path tempTXT;
  private String tempDirectoryPath;
  @Before
  public void buildTempDirectory() throws Exception {
    directory = Files.createTempDirectory("tempFiles");
    tempHTML = Files.createTempFile(directory, "tempHTML", ".html");
    tempTXT = Files.createTempFile(directory, "tempTXT", ".txt");
    File testFile = directory.toFile();
    tempDirectoryPath  = testFile.getPath();
    routeConfig = new RouteConfiguration();
  }

  @Test
  public void addRouteTest() {
    routeConfig.addRoute(this.path,this.response);
    Map routes = routeConfig.getRoutes();
    assertEquals(routes.get(path),this.response);
  }

  @Test
  public void getRoutesMethodTest() {
    routeConfig.addRoute(this.path,this.response);
    HashMap<String,Response> routes = this.routeConfig.getRoutes();
    assertTrue(routes.containsValue(this.response));
    assertTrue(routes.containsKey(this.path));
  }

  @Test
  public void getFilesTest() throws Exception {
    ArrayList <File> results = routeConfig.getPaths(tempDirectoryPath);
    assertTrue(results.contains(tempHTML.toFile()));
    assertTrue(results.contains(tempTXT.toFile()));
  }
}
