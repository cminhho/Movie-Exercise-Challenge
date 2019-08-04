package com.exercise.movie.playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "playlist")
public interface PlaylistRestRepository extends JpaRepository<Playlist, Long> {
}