package com.JavaWebServer.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class PutPostTest {
 private  Request testRequest = new Request();
 private StatusCodes codes = new StatusCodes();
 private String path = "/foobar";


  @Test
  public void HandleRequestFormEditTest() {
    testRequest.setBody("Foobar");
    FileEditorMock editorMock = new FileEditorMock();
    PutPost testPost = new PutPost(codes.OK,path,editorMock);
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
      this.hasEditedFile = true;
    }

    public boolean fileChanged() {
      return this.hasEditedFile;
    }
  }
}
