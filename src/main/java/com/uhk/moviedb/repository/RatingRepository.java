package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Rating;
import com.uhk.moviedb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByMovie(Movie movie);

    Optional<Rating> findByMovieAndAuthor(Movie movie, User author);
}
