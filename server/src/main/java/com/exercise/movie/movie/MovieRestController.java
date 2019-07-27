package com.exercise.movie.movie;

import com.exercise.movie.comment.MovieComment;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class MovieRestController {

  private static final String ENTITY_NAME = "movie";

  private final MovieService movieService;
  private final MovieRepository movieRepository;

  public MovieRestController(MovieService movieService,
      MovieRepository movieRepository) {
    this.movieService = movieService;
    this.movieRepository = movieRepository;
  }

  @PostMapping("/movie")
  public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie)
      throws URISyntaxException {
    log.debug("REST request to create movie: {}", movie);
    Movie result = movieService.save(movie);
    return ResponseEntity.created(new URI("/api/movie/" + result.getId())).body(result);
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
   * {@code GET  /movies/top_rated} : Get the top rated movies
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of top rated movies
   * in body.
   */
  @GetMapping("/movie/top_rated")
  public ResponseEntity<List<Movie>> getTopRatedMovies(Pageable pageable) {
    log.debug("REST request to gGet the top rated movies");
    Page<Movie> page = movieService.findTopRatedMovies(pageable);
    return ResponseEntity.ok().body(page.getContent());
  }

  /**
   * {@code GET  /movies/upcoming} : Get a list of upcoming movies in theatres
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of upcoming movies
   * in body.
   */
  @GetMapping("/movie/upcoming")
  public ResponseEntity<List<Movie>> getUpcomingMovies(Pageable pageable) {
    log.debug("REST request to get the top rated movies");
    Page<Movie> page = movieService.findUpcomingMovies(pageable);
    return ResponseEntity.ok().body(page.getContent());
  }

  @GetMapping("/movie/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
    log.debug("REST request to get movie by ID: {}", id);
    return movieService.findOne(id).map((movie) -> ResponseEntity.ok().body(movie))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * {@code GET  /movies/upcoming} : Get a list of comment by movie
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movie comments
   * in body.
   */
  @GetMapping("/movie/{id}/comments")
  public ResponseEntity<Set<MovieComment>> getComments(@PathVariable Long id) {
    log.debug("REST request to get a list of comment by movie : {}", id);
    Optional<Movie> movieOptional = movieRepository.findOneWithEagerRelationships(id);
    if(!movieOptional.isPresent()){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Movie movie = movieOptional.get();
    Set<MovieComment> movieComments = movie.getComments();
    return ResponseEntity.ok().body(movieComments);
  }

  @PutMapping("/movie")
  public ResponseEntity<Movie> updateMovie(@Valid @RequestBody Movie movie) {
    log.debug("REST request to update movie: {}", movie);
    if (movie.getId() == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Movie result = movieService.save(movie);
    return ResponseEntity.ok().body(result);
  }

  @DeleteMapping("/movie/{id}")
  public ResponseEntity<Void> updateMovie(@PathVariable Long id) {
    log.debug("REST request to delete movie: {}", id);
    try {
      movieService.delete(id);
      return ResponseEntity.noContent().build();
    } catch (Exception ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }
}
