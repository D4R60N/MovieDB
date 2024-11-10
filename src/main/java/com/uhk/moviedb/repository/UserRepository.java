package com.uhk.moviedb.repository;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
