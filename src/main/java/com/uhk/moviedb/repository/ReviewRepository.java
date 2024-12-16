package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Profile;
import com.uhk.moviedb.model.Review;
import com.uhk.moviedb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovie(Movie movie);

    Optional<Review> findFirstByAuthorAndMovie(User user, Movie movie);

    Optional<Review> findFirstById(Long id);
}
