package com.JavaWebServer.app;

import com.JavaWebServer.app.responses.RestMethod;
import com.JavaWebServer.app.responses.PutPost;
import com.JavaWebServer.app.StatusCodes;
import com.JavaWebServer.app.Request;
import com.JavaWebServer.app.helpers.FileEditor;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class PutPostTest {
 private  Request testRequest = new Request();
 private StatusCodes codes = new StatusCodes();
 private String fileName = "/foobar";
 private String directory = "/foo/bar";

  @Test
  public void HandleRequestFormEditTest() {
    testRequest.setBody("Foobar");
    FileEditorMock editorMock = new FileEditorMock();
    PutPost testPost = new PutPost(codes.OK,fileName,directory,editorMock);
    String response = new String (testPost.handleRequest(testRequest));
    assertEquals(true, editorMock.fileChanged());
    assertEquals(codes.OK, response);
  }

  public  class FileEditorMock extends FileEditor {
    private boolean hasEditedFile = false;

    public FileEditorMock () {
    }

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
