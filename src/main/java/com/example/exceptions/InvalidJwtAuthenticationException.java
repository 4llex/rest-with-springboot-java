package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException {
  @Serial
  private static final long serialVersionUID = 2691929924550627417L;

  public InvalidJwtAuthenticationException(String ex) {
    super(ex);
  }
}
