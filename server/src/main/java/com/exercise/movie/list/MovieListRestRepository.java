package com.exercise.movie.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "movie_list")
@Repository
@SuppressWarnings("unused")
public interface MovieListRestRepository extends JpaRepository<MovieList, Long> {
}