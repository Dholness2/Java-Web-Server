package com.javawebserver.app.responses;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.Delete;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.FileEditor;

public class DeleteTest {
  private Request testRequest = new Request();
  private ResponseBuilder testBuilder = new HttpResponseBuilder();
  private FileEditorMock editorMock = new FileEditorMock();
  private String fileName = "/foobar";
  private String directory = "foo/bar";

  @Test
  public void deletesFileTest() {
    Delete testDelete = new Delete(testBuilder, fileName, directory, editorMock);
    testDelete.handleRequest(testRequest);
    assertEquals(true, editorMock.fileChanged());
  }

  @Test
  public void returnsCorrectResponseTest() {
    Delete testDelete = new Delete(testBuilder, fileName, directory, editorMock);
    String response = new String (testDelete.handleRequest(testRequest));
    String expectedHeader = "HTTP/1.1 200 OK";
    assertEquals(expectedHeader, response);
  }

  public  class FileEditorMock extends FileEditor {
    private boolean hasEditedFile = false;

    public FileEditorMock() {}

    @Override
    public void edit(String path, String post) {
      if(path.equals(directory+fileName)) {
        this.hasEditedFile = true;
      }
    }

    public boolean fileChanged() {
      return this.hasEditedFile;
    }
  }
}
