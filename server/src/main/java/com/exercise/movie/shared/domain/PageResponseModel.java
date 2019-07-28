package com.exercise.movie.shared.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageResponseModel {
  private Integer size;
  private Long totalElements;
  private Integer totalPages;
  private Integer number;
  private List<?> results;
}