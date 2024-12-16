package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MovieService {
    void deleteById(Long id);

    void save(Movie movie);

    Double calculateAverageRating(Movie movie);

    Page<Movie> findAll(PageRequest pageRequest);

    Optional<Movie> findById(Long id);

    List<Movie> searchMovieByTitle(String value, PageRequest pageRequest);
}
