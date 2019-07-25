package com.exercise.movie.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "movie_genre")
@Repository
@SuppressWarnings("unused")
public interface MovieGenreRestRepository extends JpaRepository<MovieGenre, Long> {
}