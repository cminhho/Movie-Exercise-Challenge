package com.exercise.movie.user;

import com.exercise.movie.shared.repository.SoftDeleteRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users")
public interface UserRestRepository extends SoftDeleteRepository<User, Long> {
}