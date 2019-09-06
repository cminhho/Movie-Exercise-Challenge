package com.exercise.movie.movie.repository;

import com.exercise.movie.movie.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
  @Transactional(readOnly = true)
  @Query(value = "SELECT distinct movie FROM Movie movie LEFT JOIN FETCH movie.genres",
      countQuery = "SELECT count(distinct movie) FROM Movie movie")
  Page<Movie> findAllWithEagerRelationships(Pageable pageable);

  @Transactional(readOnly = true)
  @Query("SELECT distinct movie FROM Movie movie LEFT JOIN FETCH movie.genres")
  List<Movie> findAllWithEagerRelationships();

  @Transactional(readOnly = true)
  @Query("SELECT movie FROM Movie movie LEFT JOIN FETCH movie.genres WHERE movie.id =:id")
  Optional<Movie> findOneWithEagerRelationships(@Param("id") Long id);

  @Transactional(readOnly = true)
  @Query("SELECT m FROM Movie m JOIN FETCH m.comments WHERE m.id = :id")
  Optional<Movie> findByIdAndGetComments(@Param("id") Long id);

  @Query("select movie from Movie movie left join fetch movie.genres where movie.id =:id")
  Optional<Movie> findByIdAndGetGeneres(@Param("id") Long id);

}