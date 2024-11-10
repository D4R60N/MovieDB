package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Rating;
import com.uhk.moviedb.repository.MovieRepository;
import com.uhk.moviedb.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private RatingRepository ratingRepository;

    private MovieRepository movieRepository;

    public MovieServiceImpl(RatingRepository ratingRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public double calculateAverageRating(Movie movie) {
        List<Rating> ratings = ratingRepository.findByMovie(movie);
        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0.0);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}

