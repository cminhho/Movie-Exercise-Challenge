package com.exercise.movie.playlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query(value = "select distinct playlist from Playlist playlist left join fetch playlist.movies",
        countQuery = "select count(distinct playlist) from Playlist playlist")
    Page<Playlist> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct playlist from Playlist playlist left join fetch playlist.movies")
    List<Playlist> findAllWithEagerRelationships();

    @Query("select playlist from Playlist playlist left join fetch playlist.movies where playlist.id =:id")
    Optional<Playlist> findOneWithEagerRelationships(@Param("id") Long id);

}