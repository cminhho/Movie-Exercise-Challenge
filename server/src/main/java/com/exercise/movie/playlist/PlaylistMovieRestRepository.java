package com.exercise.movie.playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "playlist_movie")
@Repository
@SuppressWarnings("unused")
public interface PlaylistMovieRestRepository extends JpaRepository<PlaylistMovie, Long> {
}