package com.exercise.movie.shared.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UpdatingConflictException extends RuntimeException {

  public UpdatingConflictException(String message) {
    super(message);
  }
}