package com.exercise.movie.shared.rest.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface RestResponseUtil {

  static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
    return wrapOrNotFound(maybeResponse, null);
  }

  static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> optionalResponse, HttpHeaders header) {
    return optionalResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}