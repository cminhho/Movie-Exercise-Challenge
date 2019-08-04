package com.exercise.movie.movie.service;

import com.exercise.movie.movie.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MovieService {

    Movie save(Movie movie);

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findAllWithEagerRelationships(Pageable pageable);

    Page<Movie> findPopularMovies(Pageable pageable);

    Page<Movie> findTopRatedMovies(Pageable pageable);

    Page<Movie> findUpcomingMovies(Pageable pageable);

    Optional<Movie> findOne(Long id);

    void delete(Long id);

    void delete(Movie movie);
}
