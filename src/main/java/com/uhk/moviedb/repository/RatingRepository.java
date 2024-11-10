package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByMovie(Movie movie);
}
