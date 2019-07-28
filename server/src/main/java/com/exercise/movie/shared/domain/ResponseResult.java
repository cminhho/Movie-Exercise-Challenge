package com.exercise.movie.shared.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class ResponseResult {
  private Integer page;
  private Integer total_pages;
  private Integer total_results;
  private List<?> results;
}
