package com.exercise.movie.genre;

import com.exercise.movie.shared.domain.BaseEntity;
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
