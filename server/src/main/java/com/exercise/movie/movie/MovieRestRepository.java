package com.exercise.movie.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "movie")
@Repository("movieRestRepository")
public interface MovieRestRepository extends JpaRepository<Movie, Long> {
}