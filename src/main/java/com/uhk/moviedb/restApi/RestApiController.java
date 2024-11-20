package com.uhk.moviedb.restApi;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("movieRestApi")
public class RestApiController {
    MovieService movieService;

    @Autowired
    public RestApiController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("getMovies")
    public List<Movie> getMovies() {
        return movieService.findAll();
    }

}
