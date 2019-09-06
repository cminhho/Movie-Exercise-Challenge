package com.exercise.movie.movie.rest;

import com.exercise.movie.comment.MovieComment;
import com.exercise.movie.comment.MovieCommentRestRepository;
import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import com.exercise.movie.shared.exceptions.BadRequestException;
import com.exercise.movie.shared.exceptions.NotFoundException;
import java.net.URISyntaxException;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class MovieCommentsRestController {
  private final MovieRepository movieRepository;
  private final MovieCommentRestRepository movieCommentRestRepository;

  public MovieCommentsRestController(MovieRepository movieRepository,
      MovieCommentRestRepository movieCommentRestRepository) {
    this.movieRepository = movieRepository;
    this.movieCommentRestRepository = movieCommentRestRepository;
  }

  /**
   * {@code GET  /movie/{id}/comments} : Get a list of comments by movie
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movie comments
   * in body.
   */
  @GetMapping("/movie/{id}/comments")
  public ResponseEntity<Set<MovieComment>> getCommentsByMovie(@PathVariable Long id) {
    log.debug("REST request to get a list of comments by movie : {}", id);
    Movie movie = movieRepository.findByIdAndGetComments(id)
        .orElseThrow(() -> new NotFoundException("Movie not found"));
    Set<MovieComment> movieComments = movie.getComments();
    return ResponseEntity.ok().body(movieComments);
  }

  /**
   * {@code POST  /movie/{id}/comments} : Create a new comment for movie.
   *
   * @param comment the movieComment to create.
   * @return the {@link ResponseEntity}
   * with status {@code 201 (Created)} and with body the new movieComment,
   * or with status {@code 404 (Not Found)} if the movie not found.
   * or with status {@code 500 (Bad Request)} if the comment has already an ID.
   * or with status {@code 500 (Bad Request)} if the comment violated the entity.
   * constraint
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/movie/{id}/comments")
  public ResponseEntity<MovieComment> createComment(@PathVariable Long id,
      @Valid @RequestBody MovieComment comment) throws URISyntaxException {
    log.debug("REST request to save Comment: {} for MovieID {}: ", comment, id);

    Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Movie not found"));

    if (comment.getId() != null) {
      throw new BadRequestException("A new comment cannot already have an ID");
    }

    comment.setMovie(movie);
    MovieComment result = movieCommentRestRepository.save(comment);
    return ResponseEntity.ok().body(result);
  }


  /**
   * {@code DELETE  /movie/{id}/comments/{commentId}} : Delete comment for movie.
   *
   * @param commentId the comment id to delete.
   * @return the {@link ResponseEntity}
   * constraint
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @DeleteMapping("/movie/{movieId}/comments/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long movieId,
      @PathVariable Long commentId) throws URISyntaxException {
    log.debug("REST request to delete comment id : {} for movie id : {}", commentId, movieId);

    movieCommentRestRepository.deleteById(commentId);
    return ResponseEntity.noContent().build();
  }
}
