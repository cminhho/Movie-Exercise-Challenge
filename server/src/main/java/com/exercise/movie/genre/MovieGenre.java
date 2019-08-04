package com.exercise.movie.genre;

import com.exercise.movie.movie.domain.Movie;
import com.exercise.movie.shared.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "genre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovieGenre extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotEmpty(message = "Genre title cannot be empty")
    @Size(min = 2, max = 32, message = "Genre title must not be longer than 100 characters and "
        + "shorter than 2 characters")
    @Pattern(regexp = "[a-z-A-Z- ']*", message = "Genre title has invalid characters")
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(mappedBy = "genres")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    public MovieGenre title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public MovieGenre movies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public MovieGenre addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getGenres().add(this);
        return this;
    }

    public MovieGenre removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getGenres().remove(this);
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
        if (!(o instanceof MovieGenre)) {
            return false;
        }
        return id != null && id.equals(((MovieGenre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MovieGenre{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
