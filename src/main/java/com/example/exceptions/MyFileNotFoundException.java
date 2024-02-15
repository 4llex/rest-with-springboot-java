package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 6254454938834708053L;

  public MyFileNotFoundException(String ex) {
    super(ex);
  }

  public MyFileNotFoundException(String ex, Throwable cause) {
    super(ex, cause);
  }
}
