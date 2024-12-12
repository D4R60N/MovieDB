package com.uhk.moviedb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String director;

    private String actors;

    private Integer duration;

    @ManyToOne
    @NotNull
    private Genre genre;

    private Instant releaseDate;
    private String trailer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
