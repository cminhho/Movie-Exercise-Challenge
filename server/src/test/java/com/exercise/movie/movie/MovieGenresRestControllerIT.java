package com.exercise.movie.movie;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercise.movie.MovieApplication;
import com.exercise.movie.comment.MovieCommentRestRepository;
import com.exercise.movie.genre.MovieGenre;
import com.exercise.movie.genre.MovieGenreRestRepository;
import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
public class MovieGenresRestControllerIT {

  private Movie movie;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieGenreRestRepository movieGenreRestRepository;

  @Autowired
  private MovieCommentRestRepository commentRestRepository;


  @Test
  public void getMovieGenres_existingMovieID_thenStatus200() throws Exception {
    String MOVIE_GENRE_TITLE = "Comedy";

    log.debug("Initialize the database");
    // create genres
    MovieGenre comedyGenre = new MovieGenre().title(MOVIE_GENRE_TITLE);
    movieGenreRestRepository.save(comedyGenre);

    // Create movie
    Movie movie = MovieRestControllerIT.createEntity();
    movie.addGenre(comedyGenre);
    movieRepository.saveAndFlush(movie);

    mvc.perform(get("/api/movie/{id}/genres", movie.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].title", is(MOVIE_GENRE_TITLE)));
  }

  @Test
  @Transactional
  public void getMovieGenres_nonExistingMovieID_thenStatus404() throws Exception {
    mvc.perform(get("/api/movie/{id}/genres", 100L))
        .andExpect(status().isNotFound());
  }
}
