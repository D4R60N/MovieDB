package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Review;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void save(Review review);
}
