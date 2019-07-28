package com.exercise.movie.crawler.service;

import com.exercise.movie.crawler.vo.MovieCrawler;
import com.exercise.movie.crawler.helper.MovieCrawlerRequestHelper;
import com.exercise.movie.crawler.mapper.MovieCrawlerMapper;
import com.exercise.movie.movie.Movie;
import com.exercise.movie.movie.MovieRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class MovieContentCrawler {

  // TODO: Getting following values from spring boot profile properties instead
  private static String CRAWLER_AUTHOR = "system";
  private static String CRAWLER_LINK = "https://api.themoviedb.org/3/movie";
  private static String POPULAR_MOVIE_PATH = CRAWLER_LINK + "/popular";
  private static String CRAWLER_API_KEY = "a7b3c9975791294647265c71224a88ad";

  private final MovieRepository movieRepository;
  private final MovieCrawlerRequestHelper movieCrawlerRequestHelper;
  private final MovieCrawlerMapper movieCrawlerMapper;

  public MovieContentCrawler(MovieRepository movieRepository,
      MovieCrawlerRequestHelper movieCrawlerRequestHelper,
      MovieCrawlerMapper movieCrawlerMapper) {
    this.movieRepository = movieRepository;
    this.movieCrawlerRequestHelper = movieCrawlerRequestHelper;
    this.movieCrawlerMapper = movieCrawlerMapper;
  }

  //  TODO: Using AsyncTask
  @Transactional
  public int crawlMovieItems(int fromPage, int toPage) throws Exception {
    int totalCrawledItem = 0;
    for (int i = fromPage; i <= toPage; i++) {
      totalCrawledItem += crawlMovieItem(i);
    }
    return totalCrawledItem;
  }

  protected int crawlMovieItem(int page) {
    try {
      String popularMoviePath = buildCrawlerLink(page);
      String apiOutput = movieCrawlerRequestHelper.getStringResponse(popularMoviePath);
      List<MovieCrawler> movieCrawlerList =
          movieCrawlerRequestHelper.getMovieCrawlerFromStringResponse(apiOutput);

      List<Movie> movieList = new ArrayList<>();
      for (MovieCrawler movieCrawler : movieCrawlerList) {
        Movie movie = movieCrawlerMapper.movieCrawlerToMovie(movieCrawler);
        Movie movieWithAuditInformation = addAuditInformation(movie);
        movieList.add(movieWithAuditInformation);
      }
      log.debug("Processing to save movies from themoviedb.org to database");

      movieRepository.saveAll(movieList);
      return movieList.size();
    } catch (Exception ex) {
      log.error("Error while crawling movies from themoviedb.org server to DB");
    }
    return 0;
  }

  protected String buildCrawlerLink(int page) {
    return POPULAR_MOVIE_PATH + "?api_key=" + CRAWLER_API_KEY + "&language=en-US&page=" + page;
  }

  protected Movie addAuditInformation(Movie movie) {
    movie.setCreatedBy(CRAWLER_AUTHOR);
    movie.setCreatedDate(new Date());
    movie.setLastModifiedBy(CRAWLER_AUTHOR);
    movie.setLastModifiedDate(new Date());
    return movie;
  }
}
