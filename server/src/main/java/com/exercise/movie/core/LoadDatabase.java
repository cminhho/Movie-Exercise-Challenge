package com.exercise.movie.core;

import static com.exercise.movie.core.Constants.SPRING_PROFILE_DEV;
import static com.exercise.movie.core.Constants.SPRING_PROFILE_LOCAL;

import com.exercise.movie.comment.MovieComment;
import com.exercise.movie.comment.MovieCommentRestRepository;
import com.exercise.movie.genre.MovieGenre;
import com.exercise.movie.genre.MovieGenreRestRepository;
import com.exercise.movie.list.MovieList;
import com.exercise.movie.list.MovieListRestRepository;
import com.exercise.movie.movie.Movie;
import com.exercise.movie.movie.MovieRestRepository;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
@Profile({SPRING_PROFILE_LOCAL, SPRING_PROFILE_DEV})
public class LoadDatabase {

  private static String CREATED_BY_SYSTEM = "system";
  private static String LAST_MODIFIED_BY_SYSTEM = "system";

  private MovieList movieList1;
  private MovieList movieList2;
  private MovieList movieList3;

  private MovieGenre actionGenre;
  private MovieGenre comedyGenre;

  private Movie movie1;
  private Movie movie2;
  private Movie movie3;

  @Bean
  CommandLineRunner initDatabase(MovieRestRepository movieRestRepository,
      MovieCommentRestRepository movieCommentRestRepository,
      MovieGenreRestRepository movieGenreRestRepository,
      MovieListRestRepository movieListRestRepository) {
    return args -> {
      log.debug("Initial db records");
      initMovieGenresRecords(movieGenreRestRepository);
      initMovieRecords(movieRestRepository);
      initMovieCommentsRecords(movieCommentRestRepository);
      initMovieListRecords(movieListRestRepository);
    };
  }

  private void initMovieRecords(MovieRestRepository movieRestRepository) {
    movie1 = new Movie()
        .title("The Lion King")
        .backdropPath("dzBtMocZuJbjLOXvrl4zGYigDzh.jpg")
        .posterPath("dzBtMocZuJbjLOXvrl4zGYigDzh.jpg")
        .genres(Collections.singleton(actionGenre))
        .comments(Collections.emptySet())
        .movielists(Collections.emptySet())
        .voteAverage(1L)
        .popularity(1L);
    movie2 = new Movie()
        .title("Spider-Man: Far from Home")
        .backdropPath("rjbNpRMoVvqHmhmksbokcyCr7wn.jpg")
        .posterPath("rjbNpRMoVvqHmhmksbokcyCr7wn.jpg")
        .genres(Collections.emptySet())
        .comments(Collections.emptySet())
        .movielists(Collections.emptySet())
        .voteAverage(2L)
        .popularity(2L);
    movie3 = new Movie()
        .title("Alita: Battle Angel")
        .backdropPath("xRWht48C2V8XNfzvPehyClOvDni.jpg")
        .posterPath("xRWht48C2V8XNfzvPehyClOvDni.jpg")
        .genres(Collections.emptySet())
        .comments(Collections.emptySet())
        .movielists(Collections.emptySet())
        .voteAverage(3L)
        .popularity(3L);

    log.debug("Initial movie records");
    movieRestRepository.saveAll(Arrays.asList(movie1, movie2, movie3));
  }

  void initMovieCommentsRecords(MovieCommentRestRepository movieCommentRestRepository) {
    log.debug("Create movie comments and assign to specific movie");
    MovieComment comment1 = new MovieComment().review("The movie 1 was exciting");
    comment1.setMovie(movie1);

    MovieComment comment2 = new MovieComment().review("The movie 2 was exciting");
    comment2.setMovie(movie2);

    MovieComment comment3 = new MovieComment().review("The movie 3 was exciting");
    comment3.setMovie(movie3);

    log.debug("Initial movie comment records");
    movieCommentRestRepository.save(comment1);
    movieCommentRestRepository.save(comment2);
    movieCommentRestRepository.save(comment3);
  }

  private void initMovieGenresRecords(MovieGenreRestRepository movieGenreRestRepository) {
    log.debug("Create movie genre records");
    actionGenre = new MovieGenre().title("Action");
    comedyGenre = new MovieGenre().title("Comedy");
    movieGenreRestRepository.saveAll(Arrays.asList(actionGenre, comedyGenre));
  }

  private void initMovieListRecords(MovieListRestRepository movieListRestRepository) {
    log.debug("Create movie list records");
    movieList1 = new MovieList().title("Movie 1ist 1");
    movieList2 = new MovieList().title("Movie 1ist 2");
    movieList3 = new MovieList().title("Movie 1ist 3");
    movieListRestRepository.saveAll(Arrays.asList(movieList1, movieList2, movieList3));

    log.debug("Assign a move to specific lists");
    movieList1.setMovies(Collections.singleton(movie1));
    movieListRestRepository.saveAll(Arrays.asList(movieList1));
  }
}
