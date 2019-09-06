package com.exercise.movie.movie.domain;

import com.exercise.movie.comment.MovieComment;
import com.exercise.movie.genre.MovieGenre;
import com.exercise.movie.shared.domain.BaseEntity;
import com.exercise.movie.shared.enumeration.Language;
import com.exercise.movie.shared.enumeration.MediaType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "movie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Movie extends BaseEntity<String> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Movie title cannot be empty")
  @Size(min = 2, max = 100, message = "Movie title must not be longer than 100 characters and shorter than 2 characters")
  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "vote_average")
  private Long voteAverage;

  @Column(name = "vote_count")
  private Long voteCount;

  @Column(name = "video")
  private Boolean video;

  @Enumerated(EnumType.STRING)
  @Column(name = "media_type")
  private MediaType mediaType;

  @Column(name = "popularity")
  private Long popularity;

  @Size(max = 100)
  @Column(name = "poster_path")
  private String posterPath;

  @Enumerated(EnumType.STRING)
  @Column(name = "original_language")
  private Language originalLanguage;

  @Column(name = "original_title")
  private String originalTitle;

  @Size(max = 100)
  @Column(name = "backdrop_path")
  private String backdropPath;

  @Column(name = "adult")
  private Boolean adult;

  @Size(max = 256)
  @Column(name = "overview")
  private String overview;

  @Column(name = "release_date")
  private String releaseDate;

  @OneToMany(
      mappedBy = "movie",
      cascade = CascadeType.ALL,
	    orphanRemoval = true)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @JsonIgnore
  private Set<MovieComment> comments = new HashSet<>();

  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @JoinTable(
      name = "movie_genre",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
  private Set<MovieGenre> genres = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public Movie title(String title) {
    this.title = title;
    return this;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getVoteAverage() {
    return voteAverage;
  }

  public Movie voteAverage(Long voteAverage) {
    this.voteAverage = voteAverage;
    return this;
  }

  public void setVoteAverage(Long voteAverage) {
    this.voteAverage = voteAverage;
  }

  public Long getVoteCount() {
    return voteCount;
  }

  public Movie voteCount(Long voteCount) {
    this.voteCount = voteCount;
    return this;
  }

  public void setVoteCount(Long voteCount) {
    this.voteCount = voteCount;
  }

  public Boolean isVideo() {
    return video;
  }

  public Movie video(Boolean video) {
    this.video = video;
    return this;
  }

  public void setVideo(Boolean video) {
    this.video = video;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public Movie mediaType(MediaType mediaType) {
    this.mediaType = mediaType;
    return this;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public Long getPopularity() {
    return popularity;
  }

  public Movie popularity(Long popularity) {
    this.popularity = popularity;
    return this;
  }

  public void setPopularity(Long popularity) {
    this.popularity = popularity;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public Movie posterPath(String posterPath) {
    this.posterPath = posterPath;
    return this;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public Language getOriginalLanguage() {
    return originalLanguage;
  }

  public Movie originalLanguage(Language originalLanguage) {
    this.originalLanguage = originalLanguage;
    return this;
  }

  public void setOriginalLanguage(Language originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public Movie originalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
    return this;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public Movie backdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
    return this;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public Boolean isAdult() {
    return adult;
  }

  public Movie adult(Boolean adult) {
    this.adult = adult;
    return this;
  }

  public void setAdult(Boolean adult) {
    this.adult = adult;
  }

  public String getOverview() {
    return overview;
  }

  public Movie overview(String overview) {
    this.overview = overview;
    return this;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public Movie releaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Set<MovieComment> getComments() {
    return comments;
  }

  public Movie comments(Set<MovieComment> movieComments) {
    this.comments = movieComments;
    return this;
  }

  public Movie addComment(MovieComment movieComment) {
    this.comments.add(movieComment);
    movieComment.setMovie(this);
    return this;
  }

  public Movie removeComment(MovieComment movieComment) {
    this.comments.remove(movieComment);
    movieComment.setMovie(null);
    return this;
  }

  public void setComments(Set<MovieComment> movieComments) {
    this.comments = movieComments;
  }

  public Set<MovieGenre> getGenres() {
    return genres;
  }

  public Movie genres(Set<MovieGenre> movieGenres) {
    this.genres = movieGenres;
    return this;
  }

  public void setGenres(Set<MovieGenre> movieGenres) {
    this.genres = movieGenres;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Movie)) {
      return false;
    }
    return id != null && id.equals(((Movie) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  @Override
  public String toString() {
    return "Movie{" +
        "id=" + getId() +
        ", title='" + getTitle() + "'" +
        ", voteAverage=" + getVoteAverage() +
        ", voteCount=" + getVoteCount() +
        ", video='" + isVideo() + "'" +
        ", mediaType='" + getMediaType() + "'" +
        ", popularity=" + getPopularity() +
        ", posterPath='" + getPosterPath() + "'" +
        ", originalLanguage='" + getOriginalLanguage() + "'" +
        ", originalTitle='" + getOriginalTitle() + "'" +
        ", backdropPath='" + getBackdropPath() + "'" +
        ", adult='" + isAdult() + "'" +
        ", overview='" + getOverview() + "'" +
        ", releaseDate='" + getReleaseDate() + "'" +
        "}";
  }


}
