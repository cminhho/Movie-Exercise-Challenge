package com.exercise.movie.playlist;

import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;
import com.exercise.movie.shared.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class PlaylistRestController {

  final PlaylistRepository playlistRepository;
  final MovieRepository movieRepository;

  public PlaylistRestController(PlaylistRepository playlistRepository,
      MovieRepository movieRepository) {
    this.playlistRepository = playlistRepository;
    this.movieRepository = movieRepository;
  }

  /**
   * {@code GET  /playlist/{playlistId}/movie/{movieId} : add a movie to a playlist.
   *
   * @return the {@link ResponseEntity}
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/playlist/{playlistId}/movie/{movieId}")
  public ResponseEntity<Void> addMovieToList(
      @PathVariable Long playlistId, @PathVariable Long movieId) {
    log.debug("REST request to add a movie to a playlist");

    Playlist playlist = playlistRepository.findOneWithEagerRelationships(playlistId)
        .orElseThrow(() -> new NotFoundException("Playlist not found"));

    Movie movie = movieRepository.findById(movieId)
        .orElseThrow(() -> new NotFoundException("Movie not found"));

    playlist.getMovies().add(movie);
    playlistRepository.save(playlist);
    return ResponseEntity.noContent().build();
  }


}
