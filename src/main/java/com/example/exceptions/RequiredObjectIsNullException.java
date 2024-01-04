package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 2691929924550627417L;

  public RequiredObjectIsNullException() {
    super("It is not allowed to persist a null object!");
  }
  public RequiredObjectIsNullException(String ex) {
    super(ex);
  }
}
