package com.exercise.movie.playlist;

import com.exercise.movie.movie.Movie;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Getter
@Setter
@NoArgsConstructor
@Entity
public class PlaylistMovie  implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn
  private Movie movie;

  @Id
  @ManyToOne
  @JoinColumn
  private Playlist playlist;

  public PlaylistMovie(Playlist playlist, Movie movie) {
    this.playlist = playlist;
    this.movie = movie;
  }
}
