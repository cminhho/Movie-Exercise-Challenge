package com.exercise.movie.movie.rest;

import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import com.exercise.movie.playlist.Playlist;
import com.exercise.movie.shared.rest.error.NotFoundException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class MoviePlaylistsRestController {

  private final MovieRepository movieRepository;

  public MoviePlaylistsRestController(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  /**
   * {@code GET  /movies/playlists} : Get a list of playlists by movie
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movie comments
   * in body.
   */
  @GetMapping("/movie/{id}/playlists")
  public ResponseEntity<Set<Playlist>> getPlaylistsByMovie(@PathVariable Long id) {
    log.debug("REST request to get a list of genres by movie : {}", id);
    Movie movie = movieRepository.findByIdAndGetPlaylists(id)
        .orElseThrow(() -> new NotFoundException("Movie not found"));
    Set<Playlist> moviePlaylists = movie.getPlaylists();
    return ResponseEntity.ok().body(moviePlaylists);
  }

}
