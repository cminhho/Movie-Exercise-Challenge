package com.exercise.movie.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "comment")
public interface MovieCommentRestRepository extends JpaRepository<MovieComment, Long> {
}