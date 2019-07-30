package com.exercise.movie.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercise.movie.MovieApplication;
import com.exercise.movie.comment.MovieComment;
import com.exercise.movie.comment.MovieCommentRestRepository;
import com.exercise.movie.shared.TestRestUtil;
import com.exercise.movie.shared.enumeration.Language;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MovieRestControllerTest {

  private static final String DEFAULT_TITLE = "DEFAULT_TITLE";
  private static final Long DEFAULT_VOTE_AVERAGE = 1L;
  private static final Long DEFAULT_VOTE_COUNT = 1L;
  private static final Boolean DEFAULT_VIDEO = false;
  private static final Long DEFAULT_POPULARITY = 1L;
  private static final String DEFAULT_POSTER_PATH = "DEFAULT_POSTER_PATH";
  private static final Language DEFAULT_ORIGINAL_LANGUAGE = Language.FRENCH;
  private static final String DEFAULT_ORIGINAL_TITLE = "DEFAULT_ORIGINAL_TITLE";
  private static final String DEFAULT_BACKDROP_PATH = "DEFAULT_BACKDROP_PATH";
  private static final Boolean DEFAULT_ADULT = false;
  private static final String DEFAULT_OVERVIEW = "DEFAULT_OVERVIEW";
  private static final String DEFAULT_RELEASE_DATE = "DEFAULT_RELEASE_DATE";
  private static final com.exercise.movie.shared.enumeration.MediaType DEFAULT_MEDIA_TYPE =
      com.exercise.movie.shared.enumeration.MediaType.MOVIE;

  private Movie movie;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieCommentRestRepository commentRestRepository;

  @Test
  @Transactional
  public void createMovie_thenStatus201() throws Exception {
    int totalMovieInDatabaseBeforeDelete = movieRepository.findAllWithEagerRelationships().size();

    Movie newMovie = createEntity();

    // verify the rest to create movie
    mvc.perform(post("/api/movie")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(TestRestUtil.convertObjectToJsonBytes(newMovie)))
        .andExpect(status().isCreated());

    // verify the movie in the database
    List<Movie> movies = movieRepository.findAllWithEagerRelationships();
    assertThat(movies).hasSize(totalMovieInDatabaseBeforeDelete + 1);
    Movie latestUser = movies.get(movies.size() - 1);
    assertThat(latestUser.getTitle()).isEqualTo(DEFAULT_TITLE);
    assertThat(latestUser.getBackdropPath()).isEqualTo(DEFAULT_BACKDROP_PATH);
    assertThat(latestUser.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
    assertThat(latestUser.getOriginalLanguage()).isEqualTo(DEFAULT_ORIGINAL_LANGUAGE);
    assertThat(latestUser.getOverview()).isEqualTo(DEFAULT_OVERVIEW);
    assertThat(latestUser.getPopularity()).isEqualTo(DEFAULT_POPULARITY);
    assertThat(latestUser.getVoteAverage()).isEqualTo(DEFAULT_VOTE_AVERAGE);
    assertThat(latestUser.getVoteCount()).isEqualTo(DEFAULT_VOTE_COUNT);
    assertThat(latestUser.isAdult()).isEqualTo(DEFAULT_ADULT);
    assertThat(latestUser.isVideo()).isEqualTo(DEFAULT_VIDEO);
  }

  @Test
  @Transactional
  public void getAllMovies_isEagerload_ThenStatus200()
      throws Exception {
    movieRepository.saveAndFlush(createEntity());

    mvc.perform(get("/api/movie?eagerload=true")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.results.[0].title", is(DEFAULT_TITLE)));
  }

  @Test
  @Transactional
  public void getAllMovies_notEagerload_ThenStatus200()
      throws Exception {
    movieRepository.saveAndFlush(createEntity());

    mvc.perform(get("/api/movie?eagerload=false")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.[*].title").value(hasItem(DEFAULT_TITLE)));
  }

  @Test
  @Transactional
  public void getPopularMovies_thenStatus200() throws Exception {
    Long POPULARITY_1 = 1L;
    Long POPULARITY_2 = 2L;
    Long POPULARITY_3 = 3L;

    Movie movie1 = createEntity();
    movie1.setPopularity(POPULARITY_1);

    Movie movie2 = createEntity();
    movie2.setPopularity(POPULARITY_2);

    Movie movie3 = createEntity();
    movie3.setPopularity(POPULARITY_3);

    movieRepository.saveAndFlush(movie1);
    movieRepository.saveAndFlush(movie2);
    movieRepository.saveAndFlush(movie3);

    mvc.perform(get("/api/movie/popular")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.[0].popularity", is(POPULARITY_3.intValue())))
        .andExpect(jsonPath("$.results.[1].popularity", is(POPULARITY_2.intValue())))
        .andExpect(jsonPath("$.results.[2].popularity", is(POPULARITY_1.intValue())));
  }

  @Test
  @Transactional
  public void getTopRatedMovies_thenStatus200() throws Exception {
    Movie movie = createEntity();
    movieRepository.saveAndFlush(movie);

    mvc.perform(get("/api/movie/top_rated")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.[*].title").value(hasItem(DEFAULT_TITLE)));
  }

  @Test
  @Transactional
  public void getUpcomingMovies_thenStatus200() throws Exception {
    Movie movie = createEntity();
    movieRepository.saveAndFlush(movie);

    mvc.perform(get("/api/movie/upcoming")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.[*].title").value(hasItem(DEFAULT_TITLE)));
  }

  @Test
  public void getMovieComments_existingMovieID_thenStatus200() throws Exception {
    String REVIEW_MESSAGE = "The movie was exciting";

    // Create movie
    Movie movie = createEntity();
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
  public void getMovie_existingID_thenStatus200() throws Exception {
    Movie movie = createEntity();
    movieRepository.saveAndFlush(movie);

    mvc.perform(get("/api/movie/{id}", movie.getId()))
        .andExpect(status().isOk());

  }

  @Test
  @Transactional
  public void getMovie_nonExistingID_thenStatus404() throws Exception {
    mvc.perform(get("/api/movie/{id}", 100L))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void updateMovie_existingID_thenStatus200() throws Exception {
    String UPDATED_TITLE = "Updated Title";
    Movie existingMovie = createEntity();
    movieRepository.saveAndFlush(existingMovie);

    Movie updatedMovie = movieRepository.findOneWithEagerRelationships(existingMovie.getId()).get();
    updatedMovie.setTitle(UPDATED_TITLE);

    // verify the request to update movie
    mvc.perform(put("/api/movie")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(TestRestUtil.convertObjectToJsonBytes(updatedMovie)))
        .andExpect(status().isOk());

    // verify the movie is updated in database
    Movie expectedMovie =
        movieRepository.findOneWithEagerRelationships(existingMovie.getId()).get();
    assertThat(expectedMovie.getTitle()).isEqualTo(UPDATED_TITLE);
  }

  @Test
  @Transactional
  public void updateMovie_nonExistingID_thenStatus400() throws Exception {
    Movie existingMovie = createEntity();
    existingMovie.setId(null);
    mvc.perform(put("/api/movie", existingMovie))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  public void deleteMovie_existingID_thenStatus204() throws Exception {
    Movie movie = createEntity();
    movieRepository.saveAndFlush(movie);

    int totalMovieInDatabaseBeforeDelete = movieRepository.findAll().size();

    // verify the request to delete the movie
    mvc.perform(delete("/api/movie/{id}", movie.getId()))
        .andExpect(status().isNoContent());

    // verify the movie in database after deleting
    List<Movie> movies = movieRepository.findAll();
    assertThat(movies).hasSize(totalMovieInDatabaseBeforeDelete - 1);
  }

  @Test
  @Transactional
  public void deleteMovie_nonExistingID_thenStatus500() throws Exception {
    mvc.perform(delete("/api/movie/{id}", 100L))
        .andExpect(status().is5xxServerError());
  }

  public static Movie createEntity() {
    Movie movie = new Movie()
        .title(DEFAULT_TITLE)
        .voteAverage(DEFAULT_VOTE_AVERAGE)
        .voteCount(DEFAULT_VOTE_COUNT)
        .video(DEFAULT_VIDEO)
        .popularity(DEFAULT_POPULARITY)
        .mediaType(DEFAULT_MEDIA_TYPE)
        .posterPath(DEFAULT_POSTER_PATH)
        .originalLanguage(DEFAULT_ORIGINAL_LANGUAGE)
        .originalTitle(DEFAULT_ORIGINAL_TITLE)
        .backdropPath(DEFAULT_BACKDROP_PATH)
        .adult(DEFAULT_ADULT)
        .overview(DEFAULT_OVERVIEW)
        .releaseDate(DEFAULT_RELEASE_DATE);
    return movie;
  }
}
