package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.GetDirectory;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.FileEditor;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

public class GetDirectoryTest {
  private ResponseBuilder builder = new HttpResponseBuilder();
  private String directoryPath = System.getProperty("user.dir")+"/public";
  private File directory = new File(directoryPath);

  private String getRootLink(File file) {
    String fileName = file.getName();
    return ("<a href=\"/"+ fileName +"\">"+fileName+"</a><br>");
  }

  private String getNestedFileLink(File file) {
    String fileName = file.getName();
    String path = file.getParentFile().getName() + "/" + fileName;
    return ("<a href=\"/"+ path +"\">"+ fileName +"</a><br>");
  }

  @Test
  public void setRootDirectoryTest() {
    GetDirectory testGetDir = new GetDirectory(directoryPath);
    testGetDir.setRootDirectory(true);
    assertTrue(testGetDir.isRootDirectory());
  }

  @Test
  public void isRootDirectoryTest() {
    GetDirectory testGetDir = new GetDirectory(directoryPath);
    assertEquals(false, testGetDir.isRootDirectory());
  }

  @Test
  public void isRootDirectoryTestTrueState() {
    GetDirectory testGetDir = new GetDirectory(directoryPath);
    testGetDir.setRootDirectory(true);
    assertTrue(testGetDir.isRootDirectory());
  }

  @Test
  public void handleResponseTest() {
    GetDirectory testGetDir = new GetDirectory(directoryPath);
    testGetDir.setRootDirectory(true);
    String response = new String (testGetDir.handleRequest(new Request()));
    for (File file : directory.listFiles()) {
      if (!file.isHidden()) {
        assertThat(response, containsString(getRootLink(file)));
      }
    }
  }

  @Test
  public void hanldeResponseTestForNestedDirectory() {
    GetDirectory testGetDir = new GetDirectory(directoryPath);
    testGetDir.setRootDirectory(false);
    String response = new String (testGetDir.handleRequest(new Request()));
    for (File file : directory.listFiles()) {
      if (!file.isHidden()) {
        String link = getNestedFileLink(file);
        assertThat(response, containsString(link));
      }
    }
  }
}
