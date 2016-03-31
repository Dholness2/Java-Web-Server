package com.JavaWebServer.app;

import com.JavaWebServer.app.responses.Response;
import com.JavaWebServer.app.responses.GetDirectory;
import com.JavaWebServer.app.responses.Get;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
  private File testDirectory;
  private File testSubDirectory;
  private File tempHTML;
  private File tempTXT;

  @Before
  public void buildTempDirectory() throws Exception {
    String path = System.getProperty("temp.dir");
    testDirectory = new File (path, "testDir");
    testDirectory .mkdir();
    testSubDirectory = new File (testDirectory, "subDirectory");
    testSubDirectory .mkdir();
    tempHTML = new File(testDirectory, "tempHTML.html");
    tempHTML.createNewFile();
    tempTXT = new File (testDirectory, "tempTXT.txt");
    tempTXT.createNewFile();
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
    ArrayList <File> results = routeConfig.getPaths(this.testDirectory.getPath());
    assertTrue(results.contains(tempHTML));
    assertTrue(results.contains(tempTXT));
  }

  @Test
  public void buildStandardRoutesFileTest() throws Exception {
    routeConfig.buildStandardRoutes(this.testDirectory.getPath());
    String pathKey = "GET /"+ this.tempHTML.getName();
    HashMap <String,Response> currentRoutes = routeConfig.getRoutes();
    Response response = currentRoutes.get(pathKey);
    assertEquals(response.getClass(), Get.class);
  }

  @Test
  public void buildStandardRoutesDirectoryTest() throws Exception {
    routeConfig.buildStandardRoutes(this.testDirectory.getPath());
    String pathKey = "GET /"+ testDirectory.getName() + "/" + this.testSubDirectory.getName();
    System.out.println(pathKey);
    HashMap <String,Response> currentRoutes = routeConfig.getRoutes();
    Response response = currentRoutes.get(pathKey);
    assertEquals(response.getClass(), GetDirectory.class);
  }

  @Test
  public void getFileTypeTest() throws Exception {
    File testFile = new File(path, "test.txt");
    String resultType = routeConfig.getFileType(testFile.toPath());
    String expectedType = "text/plain";
    assertEquals(resultType,resultType);
    testFile.delete();
  }
}
