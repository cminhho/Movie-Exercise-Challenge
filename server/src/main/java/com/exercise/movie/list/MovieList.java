package com.exercise.movie.list;

import com.exercise.movie.movie.Movie;
import com.exercise.movie.shared.domain.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovieList extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "backdrop_path")
    private String backdropPath;

    @Column(name = "poster_path")
    private String posterPath;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.ALL})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "movie_list",
        joinColumns = @JoinColumn(name = "movie_list_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
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

    public MovieList title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public MovieList description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public MovieList backdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public MovieList posterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public MovieList movies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public MovieList addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getMovielists().add(this);
        return this;
    }

    public MovieList removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getMovielists().remove(this);
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
        if (!(o instanceof MovieList)) {
            return false;
        }
        return id != null && id.equals(((MovieList) o).id);
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
