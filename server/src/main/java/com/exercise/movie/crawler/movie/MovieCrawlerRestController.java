package com.exercise.movie.crawler.movie;

import com.exercise.movie.crawler.movie.service.MovieCrawlerService;
import com.exercise.movie.crawler.movie.vo.CrawlerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class MovieCrawlerRestController {

  private final MovieCrawlerService movieContentCrawler;

  public MovieCrawlerRestController(MovieCrawlerService movieContentCrawler) {
    this.movieContentCrawler = movieContentCrawler;
  }

  @PostMapping("/crawler/movie")
  public ResponseEntity<CrawlerResponse> crawlMovies(
      @RequestParam("listType") String listType,
      @RequestParam("fromPage") Integer fromPage,
      @RequestParam("toPage") Integer toPage) throws Exception {
    log.debug("REST request to crawl movie items");

    int totalCrawledItem = movieContentCrawler.crawlMovieItems(listType, fromPage, toPage);
    return ResponseEntity.ok().body(new CrawlerResponse(totalCrawledItem));
  }
}
