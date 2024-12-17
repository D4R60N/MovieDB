package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Profile;
import org.springframework.stereotype.Service;


@Service
public interface ProfileService {
    void save(Profile profile);
    void delete(Profile profile);
}
