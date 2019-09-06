package com.exercise.movie.crawler.movie.mapper;

import com.exercise.movie.crawler.movie.vo.MovieCrawler;
import com.exercise.movie.movie.domain.Movie;
import java.util.Collections;
import org.springframework.stereotype.Service;

@Service
public class MovieCrawlerMapper {
  public Movie movieCrawlerToMovie(MovieCrawler movieCrawler) {

    return new Movie()
        .title(movieCrawler.getTitle())
        .backdropPath(movieCrawler.getBackdropPath())
        .posterPath(movieCrawler.getPosterPath())
        .adult(movieCrawler.getAdult())
        .originalTitle(movieCrawler.getOriginalTitle())
        .releaseDate(movieCrawler.getReleaseDate())
        .video(movieCrawler.getVideo())
        .genres(Collections.emptySet())
        .comments(Collections.emptySet())
        .voteAverage(movieCrawler.getVoteAverage())
        .popularity(movieCrawler.getPopularity());
  }
}
