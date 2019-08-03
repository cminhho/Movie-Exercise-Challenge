package com.exercise.movie.user;

import com.exercise.movie.shared.repository.SoftDeleteRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "user")
@Repository
public interface UserRestRepository extends SoftDeleteRepository<User, Long> {
}