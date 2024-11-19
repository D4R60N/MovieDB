package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Rating;
import com.uhk.moviedb.model.User;
import com.uhk.moviedb.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void createRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public Optional<Rating> findByMovieAndAuthor(Movie movie, User author) {
        return ratingRepository.findByMovieAndAuthor(movie, author);
    }
}
