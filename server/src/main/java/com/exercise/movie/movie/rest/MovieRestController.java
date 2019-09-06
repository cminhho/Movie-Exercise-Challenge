package com.exercise.movie.movie.rest;

import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import com.exercise.movie.movie.service.MovieService;
import com.exercise.movie.shared.domain.PageResponseModel;
import com.exercise.movie.shared.exceptions.BadRequestException;
import com.exercise.movie.shared.exceptions.NotFoundException;
import com.exercise.movie.shared.exceptions.ConflictException;
import com.exercise.movie.shared.util.RestResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

  /**
   * {@code POST  /movie} : Create a new movie.
   *
   * @param movie the movie to create.
   * @return the {@link ResponseEntity}
   * with status {@code 201 (Created)} and with body the new movie
   * or with status 400 (Bad Request) if the movie has already an ID
   * or with status 500 (Bad Request) if the movie violated the entity constraint
   */
  @PostMapping("/movie")
  public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie)
      throws URISyntaxException {
    log.debug("REST request to create movie: {}", movie);
    if (movie.getId() != null) {
      throw new BadRequestException("A new movie cannot already have an ID");
    }
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
  public ResponseEntity<PageResponseModel> getAllMovies(Pageable pageable,
      @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
    log.debug("REST request to get a page of Movies");
    Page<Movie> page;
    if (eagerload) {
      page = movieService.findAllWithEagerRelationships(pageable);
    } else {
      page = movieService.findAll(pageable);
    }
    return ResponseEntity.ok().body(RestResponseUtil.pageToResponseResult(page));
  }

  /**
   * {@code GET  /movies/popular} : Get a list of the popular movies
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of popular movies
   * in body.
   */
  @GetMapping("/movie/popular")
  public ResponseEntity<PageResponseModel> getPopularMovies(Pageable pageable) {
    log.debug("REST request to get a list of the popular movies");
    Page<Movie> page = movieService.findPopularMovies(pageable);
    return ResponseEntity.ok().body(RestResponseUtil.pageToResponseResult(page));
  }

  /**
   * {@code GET  /movies/top_rated} : Get the top rated movies
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of top rated
   * movies in body.
   */
  @GetMapping("/movie/top_rated")
  public ResponseEntity<PageResponseModel> getTopRatedMovies(Pageable pageable) {
    log.debug("REST request to gGet the top rated movies");
    Page<Movie> page = movieService.findTopRatedMovies(pageable);
    return ResponseEntity.ok().body(RestResponseUtil.pageToResponseResult(page));
  }

  /**
   * {@code GET  /movies/upcoming} : Get a list of upcoming movies in theatres
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of upcoming movies
   * in body.
   */
  @GetMapping("/movie/upcoming")
  public ResponseEntity<PageResponseModel> getUpcomingMovies(Pageable pageable) {
    log.debug("REST request to get the top rated movies");
    Page<Movie> page = movieService.findUpcomingMovies(pageable);
    return ResponseEntity.ok().body(RestResponseUtil.pageToResponseResult(page));
  }

  /**
   * {@code GET  /movie/:id} : get the "id" movie.
   *
   * @param id the id of the movie to retrieve.
   * @return the {@link ResponseEntity}
   * with status {@code 200 (OK)} and with body the movie,
   * or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/movie/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
    log.debug("REST request to get movie by ID: {}", id);
    return movieService.findOne(id).map((movie) -> ResponseEntity.ok().body(movie))
        .orElseThrow(() -> new NotFoundException("Movie not found"));
  }

  /**
   * {@code PUT  /movie} : Updates an existing movie.
   *
   * @param updateMovie the movie to update.
   * @return the {@link ResponseEntity}
   * with status {@code 200 (OK)} and with body the updated movie,
   * or with status {@code 400 (Bad Request)} if the movie is not valid
   * or with status {@code 409 (Internal Server Error)} if the movie is conflicted while updating
   * or with status {@code 500 (Internal Server Error)} if the movie couldn't be updated
   */
  @PutMapping("/movie")
  public ResponseEntity<Movie> updateMovie(@Valid @RequestBody Movie updateMovie) {
    log.debug("REST request to update movie: {}", updateMovie);

    if (updateMovie.getId() == null) {
      throw new NotFoundException("Movie ID not found");
    }

    Movie movie = movieRepository.findById(updateMovie.getId())
        .orElseThrow(() -> new NotFoundException("Movie not found"));

    if (!movie.getVersion().equals(updateMovie.getVersion())) {
      throw new ConflictException("Conflict while updating entity");
    }

    Movie result = movieService.save(updateMovie);
    return ResponseEntity.ok().body(result);
  }

  /**
   * {@code DELETE  /movie/:id} : delete the movie.
   *
   * @param id the id of the movie to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   * or with status {@code 404 (Not Found)} if the movie is not found
   * or with status {@code 500 (Internal Server Error)} if the movie couldn't be updated
   */
  @DeleteMapping("/movie/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
    try {
      Movie movie = movieRepository.findOneWithEagerRelationships(id)
          .orElseThrow(() -> new NotFoundException("Movie not found"));
      movieService.delete(movie);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
