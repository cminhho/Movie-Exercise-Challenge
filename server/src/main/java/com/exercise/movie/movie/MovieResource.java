package com.exercise.movie.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieResource {

  private final Logger log = LoggerFactory.getLogger(MovieResource.class);

  private static final String ENTITY_NAME = "movie";

  private final MovieService movieService;

  public MovieResource(MovieService movieService) {
    this.movieService = movieService;
  }

  /**
   * {@code GET  /movies} : get all the movies.
   *
   * @param pageable the pagination information.
   * @param eagerload flag to eager load entities from relationships (This is applicable for
   * many-to-many).
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movies in body.
   */
  @GetMapping("/movie")
  public ResponseEntity<List<Movie>> getAllMovies(Pageable pageable,
      @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
    log.debug("REST request to get a page of Movies");
    Page<Movie> page;
    if (eagerload) {
      page = movieService.findAllWithEagerRelationships(pageable);
    } else {
      page = movieService.findAll(pageable);
    }
    return ResponseEntity.ok().body(page.getContent());
  }

  /**
   * {@code GET  /movies/popular} : Get a list of the popular movies
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of popular movies
   * in body.
   */
  @GetMapping("/movie/popular")
  public ResponseEntity<List<Movie>> getPopularMovies(Pageable pageable) {
    log.debug("REST request to get a list of the popular movies");
    Page<Movie> page = movieService.findPopularMovies(pageable);
    return ResponseEntity.ok().body(page.getContent());
  }

  /**
   * {@code GET  /movies/popular} : Get the top rated movies
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of popular movies
   * in body.
   */
  @GetMapping("/movie/top_rated")
  public ResponseEntity<List<Movie>> getTopRatedMovies(Pageable pageable) {
    log.debug("REST request to gGet the top rated movies");
    Page<Movie> page = movieService.findTopRatedMovies(pageable);
    return ResponseEntity.ok().body(page.getContent());
  }
}
