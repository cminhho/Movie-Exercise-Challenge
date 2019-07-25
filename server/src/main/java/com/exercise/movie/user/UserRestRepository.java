package com.exercise.movie.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "user")
@Repository
public interface UserRestRepository extends JpaRepository<User, Long> {
}