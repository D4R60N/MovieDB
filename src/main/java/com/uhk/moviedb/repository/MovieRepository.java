package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>, PagingAndSortingRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String value, Pageable pageable);
}
