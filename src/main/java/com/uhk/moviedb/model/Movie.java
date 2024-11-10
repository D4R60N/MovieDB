package com.uhk.moviedb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

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
}
