package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Profile;
import com.uhk.moviedb.model.Review;
import com.uhk.moviedb.model.User;
import com.uhk.moviedb.repository.ReviewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    @Override
    public List<Review> findByMovie(Movie movie, PageRequest pageRequest) {
        return reviewRepository.findByMovie(movie, pageRequest);
    }
    @Override
    public Optional<Review> findFirstByAuthorAndMovie(User user, Movie movie) {
        return reviewRepository.findFirstByAuthorAndMovie(user, movie);
    }

    @Override
    public List<Review> findByAuthor(User author, PageRequest pageRequest) {
        return reviewRepository.findByAuthor(author, pageRequest);
    }

    @Override
    public void delete(Review r) {
        reviewRepository.delete(r);
    }

    @Override
    public Optional<Review> findFirstById(Long id) {
        return reviewRepository.findFirstById(id);
    }
}
