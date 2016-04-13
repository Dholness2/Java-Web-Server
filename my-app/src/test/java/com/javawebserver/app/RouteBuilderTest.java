package com.javawebserver.app;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.GetDirectory;
import com.javawebserver.app.responses.Get;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class RouteBuilderTest {
  private RouteBuilder routeConfig;
  private Response response = new GetDirectory("/temp", new HttpResponseBuilder(),"/temp");
  private File testDirectory;
  private String testDirectoryPath;
  private File testSubDirectory;
  private File tempHTML;
  private File tempTXT;

  @Before
  public void buildTempDirectory() throws Exception {
    String path = System.getProperty ("temp.dir");
    testDirectory = new File (path, "testDir");
    testDirectory.mkdir();
    testDirectoryPath = this.testDirectory.getPath();
    testSubDirectory = new File (testDirectory, "subDirectory");
    testSubDirectory.mkdir();
    tempHTML = new File (testDirectory, "tempHTML.html");
    tempHTML.createNewFile();
    tempTXT = new File (testDirectory, "tempTXT.txt");
    tempTXT.createNewFile();
    routeConfig = new RouteBuilder(testDirectoryPath, new HttpResponseBuilder());
  }

  @After
  public void removeTempDirectory() {
    tempHTML.delete();
    tempTXT.delete();
    testSubDirectory.delete();
    testDirectory.delete();
  }

  @Test
  public void addsRouteTest() {
    routeConfig.addRoute("GET /home", this.response);
    Map routes = routeConfig.getRoutes();
    assertEquals(routes.get("GET /home"), this.response);
  }

  @Test
  public void getsRoutesTest() {
    routeConfig.addRoute("GET /home",this.response);
    HashMap<String,Response> routes = this.routeConfig.getRoutes();
    assertTrue(routes.containsValue(this.response));
    assertTrue(routes.containsKey("GET /home"));
  }

  @Test
  public void getsFilesListTest() throws Exception {
    ArrayList <File> results = routeConfig.getFileList(testDirectoryPath);
    assertTrue(results.contains(tempHTML));
    assertTrue(results.contains(tempTXT));
  }

  @Test
  public void buildsStandardRoutesFilesTest() throws Exception {
    routeConfig.buildStandardRoutes(testDirectoryPath);
    String pathKey = "GET /"+ this.tempHTML.getName();
    HashMap <String,Response> currentRoutes = routeConfig.getRoutes();
    Response response = currentRoutes.get(pathKey);
    assertEquals(response.getClass(), Get.class);
  }

  @Test
  public void buildStandardRoutesDirectoriesTest() throws Exception {
    routeConfig.buildStandardRoutes(testDirectoryPath);
    String pathKey = "GET /"+ this.testSubDirectory.getName();
    HashMap <String,Response> currentRoutes = routeConfig.getRoutes();
    Response response = currentRoutes.get(pathKey);
    assertEquals(response.getClass(), GetDirectory.class);
  }

  @Test
  public void getsCorrectFileTypeTest() throws Exception {
    File testFile = new File(testDirectoryPath, "test.txt");
    String resultType = routeConfig.getFileType(testFile.toPath());
    String expectedType = "text/plain";
    assertEquals(resultType,resultType);
    testFile.delete();
  }

  @Test
  public void getsCorrectImageFileTypeTest() throws Exception {
    File testFile = new File(testDirectoryPath, "test.jpeg");
    String resultType = routeConfig.getFileType(testFile.toPath());
    String expectedType = "image/plain";
    assertEquals(resultType,resultType);
    testFile.delete();
  }
}
