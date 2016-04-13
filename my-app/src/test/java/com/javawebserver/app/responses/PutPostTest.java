package com.javawebserver.app.responses;

import com.javawebserver.app.responses.Response;
import com.javawebserver.app.responses.PutPost;
import com.javawebserver.app.Request;
import com.javawebserver.app.helpers.FileEditor;
import com.javawebserver.app.responseBuilders.ResponseBuilder;
import com.javawebserver.app.responseBuilders.HttpResponseBuilder;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class PutPostTest {
  private  Request testRequest = new Request();
  private ResponseBuilder testResponse = new HttpResponseBuilder();
  private String fileName = "/foobar";
  private String directory = "/foo/bar";

  @Test
  public void HandleRequestFormEditTest() {
    testRequest.setBody("Foobar");
    FileEditorMock editorMock = new FileEditorMock();
    PutPost testPost = new PutPost(testResponse, fileName, directory, editorMock);
    String response = new String (testPost.handleRequest(testRequest));
    assertEquals(true, editorMock.fileChanged());
    assertEquals("HTTP/1.1 200 OK", response);
  }

  public  class FileEditorMock extends FileEditor {
    private boolean hasEditedFile = false;

    public FileEditorMock () {}

    @Override
    public void edit(String path, String post) {
      if (path.equals(directory+fileName)) {
        this.hasEditedFile = true;
      }
    }

    public boolean fileChanged() {
      return this.hasEditedFile;
    }
  }
}
