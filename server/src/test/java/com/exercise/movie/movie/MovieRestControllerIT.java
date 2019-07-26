package com.exercise.movie.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercise.movie.MovieApplication;
import com.exercise.movie.employee.Employee;
import com.exercise.movie.employee.EmployeeRepository;
import com.exercise.movie.shared.enumeration.Language;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = MovieApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
    locations = "classpath:application-integrationtest.properties")
public class MovieRestControllerIT {

  private static final String DEFAULT_TITLE = "AAAAAAAAAA";
  private static final String UPDATED_TITLE = "BBBBBBBBBB";

  private static final Long DEFAULT_VOTE_AVERAGE = 1L;
  private static final Long UPDATED_VOTE_AVERAGE = 2L;

  private static final Long DEFAULT_VOTE_COUNT = 1L;
  private static final Long UPDATED_VOTE_COUNT = 2L;

  private static final Boolean DEFAULT_VIDEO = false;
  private static final Boolean UPDATED_VIDEO = true;

  private static final com.exercise.movie.shared.enumeration.MediaType DEFAULT_MEDIA_TYPE = com.exercise.movie.shared.enumeration.MediaType.MOVIE;
  private static final com.exercise.movie.shared.enumeration.MediaType UPDATED_MEDIA_TYPE = com.exercise.movie.shared.enumeration.MediaType.SONG;

  private static final Long DEFAULT_POPULARITY = 1L;
  private static final Long UPDATED_POPULARITY = 2L;

  private static final String DEFAULT_POSTER_PATH = "AAAAAAAAAA";
  private static final String UPDATED_POSTER_PATH = "BBBBBBBBBB";

  private static final Language DEFAULT_ORIGINAL_LANGUAGE = Language.FRENCH;
  private static final Language UPDATED_ORIGINAL_LANGUAGE = Language.ENGLISH;

  private static final String DEFAULT_ORIGINAL_TITLE = "AAAAAAAAAA";
  private static final String UPDATED_ORIGINAL_TITLE = "BBBBBBBBBB";

  private static final String DEFAULT_BACKDROP_PATH = "AAAAAAAAAA";
  private static final String UPDATED_BACKDROP_PATH = "BBBBBBBBBB";

  private static final Boolean DEFAULT_ADULT = false;
  private static final Boolean UPDATED_ADULT = true;

  private static final String DEFAULT_OVERVIEW = "AAAAAAAAAA";
  private static final String UPDATED_OVERVIEW = "BBBBBBBBBB";

  private static final String DEFAULT_RELEASE_DATE = "AAAAAAAAAA";
  private static final String UPDATED_RELEASE_DATE = "BBBBBBBBBB";

  private Movie movie;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private MovieRepository repository;

  @Test
  public void getAllMovies_ThenStatus200()
      throws Exception {
    repository.saveAndFlush(createEntity());

    mvc.perform(get("/api/movie?eagerload=true")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
  }

  @Test
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

    repository.saveAndFlush(movie1);
    repository.saveAndFlush(movie2);
    repository.saveAndFlush(movie3);

    mvc.perform(get("/api/movie/popular")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].popularity").value(hasItem(POPULARITY_3.intValue())))
        .andExpect(jsonPath("$.[1].popularity").value(hasItem(POPULARITY_2.intValue())))
        .andExpect(jsonPath("$.[2].popularity").value(hasItem(POPULARITY_1.intValue())));
  }

  @Test
  public void getTopRatedMovies_thenStatus200() throws Exception {
    Movie movie = createEntity();
    repository.saveAndFlush(movie);

    mvc.perform(get("/api/movie/top_rated")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
  }

  @Test
  public void getUpcomingMovies_thenStatus200() throws Exception {
    Movie movie = createEntity();
    repository.saveAndFlush(movie);

    mvc.perform(get("/api/movie/upcoming")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
  }

  public static Movie createEntity() {
    Movie movie = new Movie()
        .title(DEFAULT_TITLE)
        .voteAverage(DEFAULT_VOTE_AVERAGE)
        .voteCount(DEFAULT_VOTE_COUNT)
        .video(DEFAULT_VIDEO)
        .popularity(DEFAULT_POPULARITY)
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
