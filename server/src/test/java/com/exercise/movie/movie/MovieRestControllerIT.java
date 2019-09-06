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
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.exercise.movie.MovieApplication;
import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import com.exercise.movie.movie.rest.MovieRestController;
import com.exercise.movie.shared.TestRestUtil;
import com.exercise.movie.shared.RandomStringUtils;
import com.exercise.movie.shared.enumeration.Language;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
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
public class MovieRestControllerIT {

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
  private static final Long DEFAULT_VERSION = 0L;
  private static final com.exercise.movie.shared.enumeration.MediaType DEFAULT_MEDIA_TYPE =
      com.exercise.movie.shared.enumeration.MediaType.MOVIE;

  private Movie movie;

  private MockMvc mvc;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  MovieRestController movieRestController;

  @Autowired
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

  @Before
  public void setup() {
    this.mvc = standaloneSetup(this.movieRestController)
        .setCustomArgumentResolvers(pageableArgumentResolver)
        .build();// Standalone context
  }

  @Test
  @Transactional
  public void createMovie_validPayload_thenStatus201() throws Exception {
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
    assertThat(latestUser.getVersion()).isEqualTo(DEFAULT_VERSION);
  }

  @Test
  public void createMovie_existingIdInPayload_thenStatus400() throws Exception {
    int databaseSizeBeforeTest = movieRepository.findAll().size();
    Movie movie = createEntity();
    movie.setId(1L);

    log.debug("Verify the rest to create movie");
    mvc.perform(post("/api/movie")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(TestRestUtil.convertObjectToJsonBytes(movie)))
        .andExpect(status().isBadRequest());

    log.debug("Validate entities in database");
    List<Movie> movieList = movieRepository.findAll();
    assertThat(movieList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  public void createMovie_nullTitleInPlayload_thenStatus500() throws Exception {
    int databaseSizeBeforeTest = movieRepository.findAll().size();
    Movie movie = createEntity();
    movie.setTitle(null);

    log.debug("Verify the rest to create movie");
    mvc.perform(post("/api/movie")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(TestRestUtil.convertObjectToJsonBytes(movie)))
        .andExpect(status().isBadRequest());

    log.debug("Validate entities in database");
    List<Movie> movieList = movieRepository.findAll();
    assertThat(movieList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  public void createMovie_titleLongerThan100CharactersInPayload_thenStatus500() throws Exception {
    int databaseSizeBeforeTest = movieRepository.findAll().size();
    Movie movie = createEntity();

    String generatedTitle = RandomStringUtils.randomAlphanumeric(101);
    movie.setTitle(generatedTitle);

    log.debug("Verify the rest to create movie");
    mvc.perform(post("/api/movie")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(TestRestUtil.convertObjectToJsonBytes(movie)))
        .andExpect(status().isBadRequest());

    log.debug("Validate the new entity cannot be stored in database");
    List<Movie> movieList = movieRepository.findAll();
    assertThat(movieList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  public void createMovie_titleShorterThan2CharactersInPayload_thenStatus500() throws Exception {
    int databaseSizeBeforeTest = movieRepository.findAll().size();
    Movie movie = createEntity();
    movie.setTitle("a");

    log.debug("Verify the rest to create movie");
    mvc.perform(post("/api/movie")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(TestRestUtil.convertObjectToJsonBytes(movie)))
        .andExpect(status().isBadRequest());

    log.debug("Validate the new entity cannot be stored in database");
    List<Movie> movieList = movieRepository.findAll();
    assertThat(movieList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  public void getAllMovies_isEagerload_ThenStatus200()
      throws Exception {
    movieRepository.saveAndFlush(createEntity());

    mvc.perform(get("/api/movie?eagerload={isEagerload}", true)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.results.[*].title").value(hasItem(DEFAULT_TITLE)));
  }

  @Test
  @Transactional
  public void getAllMovies_notEagerload_ThenStatus200()
      throws Exception {
    movieRepository.saveAndFlush(createEntity());

    mvc.perform(get("/api/movie?eagerload={isEagerload}", false)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.[*].title").value(hasItem(DEFAULT_TITLE)));
  }

  @Test
  @Transactional
  @Ignore
  public void getPopularMovies_thenStatus200() throws Exception {
    Long POPULARITY_1 = 1L;
    Long POPULARITY_2 = 2L;
    Long POPULARITY_3 = 3L;

    log.debug("Initialize the database");
    Movie movie1 = createEntity();
    movie1.setPopularity(POPULARITY_1);

    Movie movie2 = createEntity();
    movie2.setPopularity(POPULARITY_2);

    Movie movie3 = createEntity();
    movie3.setPopularity(POPULARITY_3);

    movieRepository.saveAndFlush(movie1);
    movieRepository.saveAndFlush(movie2);
    movieRepository.saveAndFlush(movie3);

    log.debug("Get popular movies");
    mvc.perform(get("/api/movie/popular")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.[0].popularity").value(hasItem(POPULARITY_3.intValue())))
        .andExpect(jsonPath("$.results.[1].popularity").value(hasItem(POPULARITY_2.intValue())))
        .andExpect(jsonPath("$.results.[2].popularity").value(hasItem(POPULARITY_1.intValue())));
  }

  @Test
  @Transactional
  public void getTopRatedMovies_thenStatus200() throws Exception {
    log.debug("Initialize the database");
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
    Long currentEntityVersion;
    Movie existingMovie = createEntity();
    movieRepository.saveAndFlush(existingMovie);

    Movie updatedMovie = movieRepository.findOneWithEagerRelationships(existingMovie.getId()).get();
    currentEntityVersion = updatedMovie.getVersion();
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
    assertThat(expectedMovie.getVersion()).isEqualTo(currentEntityVersion + 1);
  }

  @Test
  @Transactional
  public void updateMovie_nonExistingID_thenStatus404() throws Exception {
    Movie existingMovie = createEntity();
    existingMovie.setId(null);
    mvc.perform(put("/api/movie")
        .content(TestRestUtil.convertObjectToJsonBytes(existingMovie))
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void updateMovie_conflictVersion_thenStatus409() throws Exception {
    log.debug("Initialize the database");
    Movie movie = createEntity();
    movieRepository.saveAndFlush(movie);

    Movie movieUpdatedByUser1 = movieRepository.getOne(movie.getId());
    log.debug("The movie was updated by user1");
    movieUpdatedByUser1.title("The movie was updated by user1");
    movieRepository.saveAndFlush(movieUpdatedByUser1);

    Movie movieUpdatedByUser2 = createEntity();
    movieUpdatedByUser2.setId(movie.getId());
    movieUpdatedByUser2.setVersion(0L);
    log.debug("The movie is going to be updated by user2 via REST API");
    movieUpdatedByUser2.title("The movie is going to be updated by user2 via REST API");

    mvc.perform(put("/api/movie")
        .content(TestRestUtil.convertObjectToJsonBytes(movieUpdatedByUser2))
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isConflict());

    log.debug("Validate movie in database");
  }

  @Test
  @Transactional
  public void deleteMovie_existingMovie_thenStatus204() throws Exception {
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
  public void deleteMovie_nonExistingMovie_thenStatus404() throws Exception {
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
