package com.exercise.movie.manga;

import com.exercise.movie.movie.domain.Movie;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MangaService {

  Logger logger = LoggerFactory.getLogger(MangaService.class);
  private static final String MANGA_SEARCH_URL="http://api.jikan.moe/search/manga/";
  private static final String MOVIE_SEARCH_URL="http://localhost:9080/api/movie/1?api_key"
      + "=a7b3c9975791294647265c71224a88ad";

  private final RestTemplate restTemplate;

  public MangaService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  public Movie getMovie() {
    Movie movie = restTemplate.getForObject(MOVIE_SEARCH_URL, Movie.class);
    return movie;
  }

  public List<Manga> getMangasByTitle(String title) {
    return restTemplate.getForEntity(MANGA_SEARCH_URL+title, MangaResult.class).getBody().getResult();
  }

}