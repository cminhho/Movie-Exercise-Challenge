package com.exercise.movie.crawler;

import com.exercise.movie.crawler.service.MovieContentCrawler;
import com.exercise.movie.crawler.vo.CrawlerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class CrawlerRestController {

  private final MovieContentCrawler movieContentCrawler;

  public CrawlerRestController(MovieContentCrawler movieContentCrawler) {
    this.movieContentCrawler = movieContentCrawler;
  }

  @PostMapping("/crawler/movie/popular")
  public ResponseEntity<CrawlerResponse> createMovie() throws Exception {
    log.debug("REST request to crawl movie items");
    int totalCrawledItem = movieContentCrawler.crawlMovieItems(1, 5);
    return ResponseEntity.ok().body(new CrawlerResponse(totalCrawledItem));
  }

}
