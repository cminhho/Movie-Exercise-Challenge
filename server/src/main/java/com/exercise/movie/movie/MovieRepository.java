package com.exercise.movie.movie;

import com.exercise.movie.movie.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
  @Query(value = "select distinct movie from Movie movie left join fetch movie.genres",
      countQuery = "select count(distinct movie) from Movie movie")
  Page<Movie> findAllWithEagerRelationships(Pageable pageable);

  @Query("select distinct movie from Movie movie left join fetch movie.genres")
  List<Movie> findAllWithEagerRelationships();

  @Query("select movie from Movie movie left join fetch movie.genres where movie.id =:id")
  Optional<Movie> findOneWithEagerRelationships(@Param("id") Long id);
}