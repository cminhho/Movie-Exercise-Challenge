package com.exercise.movie.playlist;

import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.shared.domain.BaseEntity;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "playlist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Playlist extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Playlist title cannot be empty")
    @Size(min = 2, max = 100, message = "Playlist title must not be longer than 100 characters and shorter than 2 characters")
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 256)
    @Column(name = "description")
    private String description;

    @Size(max = 256)
    @Column(name = "backdrop_path", length = 256)
    private String backdropPath;

    @Size(max = 256)
    @Column(name = "poster_path")
    private String posterPath;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "playlist_movie",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Playlist title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Playlist description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Playlist backdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Playlist posterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public Playlist movies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Playlist)) {
            return false;
        }
        return id != null && id.equals(((Playlist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MovieList{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", backdropPath='" + getBackdropPath() + "'" +
            ", posterPath='" + getPosterPath() + "'" +
            "}";
    }
}
