package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MovieService {
    void deleteById(Long id);

    void save(Movie movie);

    Double calculateAverageRating(Movie movie);

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

}
