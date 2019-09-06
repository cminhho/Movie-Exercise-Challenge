package com.exercise.movie.movie.rest;

import com.exercise.movie.genre.MovieGenre;
import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import com.exercise.movie.shared.exceptions.NotFoundException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class MovieGenresRestController {

  private final MovieRepository movieRepository;

  public MovieGenresRestController(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  /**
   * {@code GET  /movies/genres} : Get a list of genres by movie
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movie comments
   * in body.
   */
  @GetMapping("/movie/{id}/genres")
  public ResponseEntity<Set<MovieGenre>> getGenresByMovie(@PathVariable Long id) {
    log.debug("REST request to get a list of genres by movie : {}", id);
    Movie movie = movieRepository.findByIdAndGetGeneres(id)
        .orElseThrow(() -> new NotFoundException("Movie not found"));
    Set<MovieGenre> movieGenres = movie.getGenres();
    return ResponseEntity.ok().body(movieGenres);
  }

}
