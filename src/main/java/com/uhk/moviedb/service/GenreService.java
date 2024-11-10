package com.uhk.moviedb.service;

import com.uhk.moviedb.model.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {
    List<Genre> findAll();
}
