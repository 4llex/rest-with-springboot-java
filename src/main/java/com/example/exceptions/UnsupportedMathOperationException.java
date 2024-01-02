package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 2691929924550627417L;

  public UnsupportedMathOperationException(String ex) {
    super(ex);
  }
}
