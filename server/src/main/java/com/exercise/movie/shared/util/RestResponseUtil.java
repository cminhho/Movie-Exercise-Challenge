package com.exercise.movie.shared.util;

import com.exercise.movie.shared.domain.PageResponseModel;
import org.springframework.data.domain.Page;
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

  static PageResponseModel pageToResponseResult(Page<?> page) {
    return PageResponseModel.builder()
        .number(page.getNumber())
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .results(page.getContent())
        .build();
  }
}