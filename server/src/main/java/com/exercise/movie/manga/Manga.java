package com.exercise.movie.manga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Manga {
  private String title;
  private String description;
  private Integer volumes;
  private Double score;
}