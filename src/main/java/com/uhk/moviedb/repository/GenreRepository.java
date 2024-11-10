package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Genre;
import com.uhk.moviedb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
