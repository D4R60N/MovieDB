package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
