package com.exercise.movie.crawler.movie.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "vote_count",
    "id",
    "video",
    "vote_average",
    "title",
    "popularity",
    "poster_path",
    "original_language",
    "original_title",
    "genre_ids",
    "backdrop_path",
    "adult",
    "overview",
    "release_date"
})
public class MovieCrawler {

  @JsonProperty("vote_count")
  private Integer voteCount;
  @JsonProperty("id")
  private Integer id;
  @JsonProperty("video")
  private Boolean video;
  @JsonProperty("vote_average")
  private Long voteAverage;
  @JsonProperty("title")
  private String title;
  @JsonProperty("popularity")
  private Long popularity;
  @JsonProperty("poster_path")
  private String posterPath;
  @JsonProperty("original_language")
  private String originalLanguage;
  @JsonProperty("original_title")
  private String originalTitle;
  @JsonProperty("genre_ids")
  private List<Integer> genreIds = null;
  @JsonProperty("backdrop_path")
  private String backdropPath;
  @JsonProperty("adult")
  private Boolean adult;
  @JsonProperty("overview")
  private String overview;
  @JsonProperty("release_date")
  private String releaseDate;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("vote_count")
  public Integer getVoteCount() {
    return voteCount;
  }

  @JsonProperty("vote_count")
  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
  }

  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Integer id) {
    this.id = id;
  }

  @JsonProperty("video")
  public Boolean getVideo() {
    return video;
  }

  @JsonProperty("video")
  public void setVideo(Boolean video) {
    this.video = video;
  }

  @JsonProperty("vote_average")
  public Long getVoteAverage() {
    return voteAverage;
  }

  @JsonProperty("vote_average")
  public void setVoteAverage(Long voteAverage) {
    this.voteAverage = voteAverage;
  }

  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  @JsonProperty("title")
  public void setTitle(String title) {
    this.title = title;
  }

  @JsonProperty("popularity")
  public Long getPopularity() {
    return popularity;
  }

  @JsonProperty("popularity")
  public void setPopularity(Long popularity) {
    this.popularity = popularity;
  }

  @JsonProperty("poster_path")
  public String getPosterPath() {
    return posterPath;
  }

  @JsonProperty("poster_path")
  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  @JsonProperty("original_language")
  public String getOriginalLanguage() {
    return originalLanguage;
  }

  @JsonProperty("original_language")
  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  @JsonProperty("original_title")
  public String getOriginalTitle() {
    return originalTitle;
  }

  @JsonProperty("original_title")
  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  @JsonProperty("genre_ids")
  public List<Integer> getGenreIds() {
    return genreIds;
  }

  @JsonProperty("genre_ids")
  public void setGenreIds(List<Integer> genreIds) {
    this.genreIds = genreIds;
  }

  @JsonProperty("backdrop_path")
  public String getBackdropPath() {
    return backdropPath;
  }

  @JsonProperty("backdrop_path")
  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  @JsonProperty("adult")
  public Boolean getAdult() {
    return adult;
  }

  @JsonProperty("adult")
  public void setAdult(Boolean adult) {
    this.adult = adult;
  }

  @JsonProperty("overview")
  public String getOverview() {
    return overview;
  }

  @JsonProperty("overview")
  public void setOverview(String overview) {
    this.overview = overview;
  }

  @JsonProperty("release_date")
  public String getReleaseDate() {
    return releaseDate;
  }

  @JsonProperty("release_date")
  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}