package com.exercise.movie.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "genres")
public interface MovieGenreRestRepository extends JpaRepository<MovieGenre, Long> {
}