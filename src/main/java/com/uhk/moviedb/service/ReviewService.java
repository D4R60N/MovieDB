package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Profile;
import com.uhk.moviedb.model.Review;
import com.uhk.moviedb.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReviewService {
    void save(Review review);
    List<Review> findByMovie(Movie movie);

    Optional<Review> findFirstByAuthorAndMovie(User user, Movie movie);

    void delete(Review r);

    Optional<Review> findFirstById(Long id);
}
