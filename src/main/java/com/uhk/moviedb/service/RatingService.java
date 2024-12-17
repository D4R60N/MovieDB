package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Rating;
import com.uhk.moviedb.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RatingService {
    void createRating(Rating rating);
    void deleteRating(Long ratingId);

    Optional<Rating> findByMovieAndAuthor(Movie movie, User author);
    List<Rating> findByAuthor(User author, PageRequest pageRequest);
}
