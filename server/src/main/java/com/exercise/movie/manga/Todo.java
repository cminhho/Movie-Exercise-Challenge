package com.exercise.movie.manga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
  private String userId;
  private String title;
  private Boolean completed;
  private Integer id;
}
