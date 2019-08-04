package com.exercise.movie.movie;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercise.movie.MovieApplication;
import com.exercise.movie.comment.MovieCommentRestRepository;
import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import com.exercise.movie.playlist.Playlist;
import com.exercise.movie.playlist.PlaylistRestRepository;
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
public class MoviePlaylistsRestControllerIT {

  private Movie movie;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private PlaylistRestRepository playlistRestRepository;

  @Autowired
  private MovieCommentRestRepository commentRestRepository;


  @Test
  public void getMoviePlaylists_existingMovieID_thenStatus200() throws Exception {
    String PLAYLIST_TITLE = "Playlist 1";

    log.debug("Initialize the database");
    // Create movie
    Movie movie = MovieRestControllerIT.createEntity();
    movieRepository.saveAndFlush(movie);

    // create playlist
    Playlist play1ist1 = new Playlist().title(PLAYLIST_TITLE);
    play1ist1.addMovie(movie);
    playlistRestRepository.save(play1ist1);

    mvc.perform(get("/api/movie/{id}/playlists", movie.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].title", is(PLAYLIST_TITLE)));
  }

  @Test
  @Transactional
  public void getMoviePlaylists_nonExistingMovieID_thenStatus404() throws Exception {
    mvc.perform(get("/api/movie/{id}/playlists", 100L))
        .andExpect(status().isNotFound());
  }
}
