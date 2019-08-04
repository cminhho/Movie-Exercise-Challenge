package com.exercise.movie.crawler.movie.service;

import com.exercise.movie.crawler.movie.vo.MovieCrawler;
import com.exercise.movie.crawler.movie.helper.MovieCrawlerRequestHelper;
import com.exercise.movie.crawler.movie.mapper.MovieCrawlerMapper;
import com.exercise.movie.crawler.movie.vo.MovieCrawlerResponse;
import com.exercise.movie.genre.MovieGenre;
import com.exercise.movie.genre.MovieGenreRestRepository;
import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.movie.repository.MovieRepository;

import java.time.LocalDateTime;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@Slf4j
public class MovieCrawlerService {

  // TODO: Getting following values from spring boot profile properties instead
  private static String CRAWLER_AUTHOR = "system";
  private static String CRAWLER_LINK = "https://api.themoviedb.org/3/movie";
  private static String CRAWLER_API_KEY = "a7b3c9975791294647265c71224a88ad";

  private final MovieRepository movieRepository;
  private final MovieGenreRestRepository movieGenreRestRepository;
  private final MovieCrawlerRequestHelper movieCrawlerRequestHelper;
  private final MovieCrawlerMapper movieCrawlerMapper;

  public MovieCrawlerService(MovieRepository movieRepository,
                             MovieGenreRestRepository movieGenreRestRepository,
                             MovieCrawlerRequestHelper movieCrawlerRequestHelper,
                             MovieCrawlerMapper movieCrawlerMapper) {
    this.movieRepository = movieRepository;
    this.movieGenreRestRepository = movieGenreRestRepository;
    this.movieCrawlerRequestHelper = movieCrawlerRequestHelper;
    this.movieCrawlerMapper = movieCrawlerMapper;
  }



  //  TODO: Using AsyncTask
  @Transactional
  public int crawlMovieItems(String listType, int fromPage, int toPage) throws Exception {
    int totalCrawledItem = 0;
    for (int i = fromPage; i <= toPage; i++) {
      getMovies(listType, i);
      totalCrawledItem += crawlMovieItem(listType, i);
    }
    return totalCrawledItem;
  }

  protected void getMovies(String listType, int page) {
    final String uri = "https://api.themoviedb.org/3/movie/{listType}?api_key={apiKey}&language=en-US&page={page}";

    List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
    acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(acceptableMediaTypes);
    HttpEntity<MovieCrawlerResponse> entity = new HttpEntity<MovieCrawlerResponse>(headers);

    Map<String, String> params = new HashMap<String, String>();
    params.put("listType", listType);
    params.put("apiKey", CRAWLER_API_KEY);
    params.put("page", Integer.toString(page));

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<MovieCrawlerResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, MovieCrawlerResponse.class, params);

    HttpStatus statusCode = response.getStatusCode();
    System.out.println("Response Satus Code: " + statusCode);

    // Status Code: 200
    if (statusCode == HttpStatus.OK) {
      // Response Body Data
      MovieCrawlerResponse responseData = response.getBody();

      if (responseData != null) {
        for (MovieCrawler e : responseData.getResults()) {
          System.out.println("Movie: " + e.getId() + " - " + e.getTitle());
        }
      }
    }
  }

  protected int crawlMovieItem(String listType, int page) {
    try {
      String popularMoviePath = buildMovieCrawlerLink(listType, page);
      String apiOutput = movieCrawlerRequestHelper.getStringResponse(popularMoviePath);
      List<MovieCrawler> movieCrawlerList =
              movieCrawlerRequestHelper.getMovieCrawlerFromStringResponse("results", apiOutput);

      List<Movie> movieList = new ArrayList<>();
      for (MovieCrawler movieCrawler : movieCrawlerList) {
        Movie movie = createMovieEntity(movieCrawler);
        movieList.add(movie);
      }
      log.debug("Processing to save movies from themoviedb.org to database");
      movieRepository.saveAll(movieList);
      return movieList.size();
    } catch (Exception ex) {
      log.error("Error while crawling movies from themoviedb.org server to DB");
    }
    return 0;
  }

  protected Movie createMovieEntity(MovieCrawler movieCrawler) {
    Movie movie = movieCrawlerMapper.movieCrawlerToMovie(movieCrawler);
    addDefaultGenres(movie);
    addAuditInformation(movie);
    return movie;
  }

  protected Movie addDefaultGenres(Movie movie) {
    List<MovieGenre> movieGenreList = movieGenreRestRepository.findAll();
    movie.genres(new HashSet(movieGenreList));
    return movie;
  }

  protected String buildMovieCrawlerLink(String listType, int page) {
    return CRAWLER_LINK + '/' + listType + "?api_key=" + CRAWLER_API_KEY + "&language=en-US&page=" + page;
  }

  protected Movie addAuditInformation(Movie movie) {
    movie.setCreatedBy(CRAWLER_AUTHOR);
    movie.setCreatedDate(LocalDateTime.now());
    movie.setLastModifiedBy(CRAWLER_AUTHOR);
    movie.setLastModifiedDate(LocalDateTime.now());
    return movie;
  }
}
