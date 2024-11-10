package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    void deleteById(Long id);

    void save(Movie movie);

    double calculateAverageRating(Movie movie);

    List<Movie> findAll();
}
