package com.exercise.movie.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.exercise.movie.MovieApplication;
import com.exercise.movie.comment.CommentRestControllerIT;
import com.exercise.movie.comment.MovieComment;
import com.exercise.movie.comment.MovieCommentRestRepository;
import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import com.exercise.movie.movie.rest.MovieCommentsRestController;
import com.exercise.movie.shared.TestRestUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieApplication.class)
@Slf4j
public class MovieCommentsRestControllerIT {

  private MockMvc mvc;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieCommentRestRepository commentRestRepository;

  @Autowired
  MovieCommentsRestController movieCommentsRestController;

  @Autowired
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

  @Before
  public void setup() {
    this.mvc = standaloneSetup(this.movieCommentsRestController)
        .setCustomArgumentResolvers(pageableArgumentResolver)
        .build();// Standalone context
  }

  @Test
  public void getMovieComments_existingMovieID_thenStatus200() throws Exception {
    String REVIEW_MESSAGE = "The movie was exciting";

    log.debug("Initialize the database");
    // Create movie
    Movie movie = MovieRestControllerIT.createEntity();
    movieRepository.saveAndFlush(movie);

    // Assign a comment to the movie
    MovieComment movieComment = new MovieComment().review(REVIEW_MESSAGE);
    movieComment.setMovie(movie);
    commentRestRepository.saveAndFlush(movieComment);

    mvc.perform(get("/api/movie/{id}/comments", movie.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].review", is(REVIEW_MESSAGE)));
  }

  @Test
  @Transactional
  public void getMovieComments_nonExistingMovieID_thenStatus404() throws Exception {
    mvc.perform(get("/api/movie/{id}/comments", 100L))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void createComments_validPayload_thenStatus200() throws Exception {
    int databaseSizeBeforeTest = commentRestRepository.findAll().size();

    log.debug("Initialize the database");
    Movie movie = MovieRestControllerIT.createEntity();
    movieRepository.saveAndFlush(movie);

    log.debug("Verify the rest to create comment");
    MovieComment movieComment = CommentRestControllerIT.createEntity();
    movieComment.setMovie(movie);
    mvc.perform(post("/api/movie/{id}/comments", movie.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestRestUtil.convertObjectToJsonBytes(movieComment)))
        .andExpect(status().isOk());

    log.debug("Validate the database");
    List<MovieComment> expectedComments = commentRestRepository.findAll();
    assertThat(expectedComments).hasSize(databaseSizeBeforeTest + 1);

    Movie expectedMovie = movieRepository.findByIdAndGetComments(movie.getId()).get();
//    assertThat(movieComment).isIn(expectedMovie.getComments());
  }

  @Test
  @Transactional
  public void createComments_nonExistingMovieIDInPayload_thenStatus404() throws Exception {
    int databaseSizeBeforeTest = commentRestRepository.findAll().size();
    MovieComment movieComment = CommentRestControllerIT.createEntity();

    mvc.perform(post("/api/movie/{id}/comments", 100L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestRestUtil.convertObjectToJsonBytes(movieComment)))
        .andExpect(status().isNotFound());

    log.debug("Validate the database");
    List<MovieComment> movieCommentList = commentRestRepository.findAll();
    assertThat(movieCommentList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void createComments_existingCommentIdInPayload_thenStatus400() throws Exception {
    int databaseSizeBeforeTest = commentRestRepository.findAll().size();

    log.debug("Initialize the database");
    Movie movie = MovieRestControllerIT.createEntity();
    movieRepository.saveAndFlush(movie);

    MovieComment movieComment = CommentRestControllerIT.createEntity();
    movieComment.setId(10L);
    mvc.perform(post("/api/movie/{id}/comments", movie.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestRestUtil.convertObjectToJsonBytes(movieComment)))
        .andExpect(status().isBadRequest());

    log.debug("Validate the database");
    List<MovieComment> movieCommentList = commentRestRepository.findAll();
    assertThat(movieCommentList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void createComments_reviewIsEmptyInPayload_thenStatus500() throws Exception {
    log.debug("Initialize the database");
    Movie movie = MovieRestControllerIT.createEntity();
    movieRepository.saveAndFlush(movie);

    MovieComment movieComment = CommentRestControllerIT.createEntity();
    movieComment.setReview("");
    int databaseSizeBeforeTest = commentRestRepository.findAll().size();
    mvc.perform(post("/api/movie/{id}/comments", movie.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestRestUtil.convertObjectToJsonBytes(movieComment)))
        .andExpect(status().isBadRequest());

    log.debug("Validate the database");
    List<MovieComment> movieCommentList = commentRestRepository.findAll();
    assertThat(movieCommentList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void deleteComment_validMovieIdAndCommentId_thenStatus204() throws Exception {

    log.debug("Initialize the database");
    Movie movie = MovieRestControllerIT.createEntity();
    movieRepository.saveAndFlush(movie);
    MovieComment movieComment = CommentRestControllerIT.createEntity();
    movieComment.setMovie(movie);
    commentRestRepository.saveAndFlush(movieComment);

    log.debug("Verify the rest to delete comment");
    int databaseSizeBeforeTest = commentRestRepository.findAll().size();
    mvc.perform(delete("/api/movie/{id}/comments/{commentId}", movie.getId(), movieComment.getId()))
        .andExpect(status().isNoContent());

    log.debug("Validate the database");
    List<MovieComment> movieCommentList = commentRestRepository.findAll();
    assertThat(movieCommentList).hasSize(databaseSizeBeforeTest - 1);
  }
}
