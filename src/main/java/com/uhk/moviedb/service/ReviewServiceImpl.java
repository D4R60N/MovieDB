package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Profile;
import com.uhk.moviedb.model.Review;
import com.uhk.moviedb.repository.ProfileRepository;
import com.uhk.moviedb.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }


    @Override
    public void save(Review review) {
        reviewRepository.save(review);
    }
}
