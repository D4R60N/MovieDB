package com.uhk.moviedb.view.component;

import com.uhk.moviedb.model.Movie;
import com.vaadin.flow.component.grid.Grid;


import java.util.List;

public class MovieList<T extends Movie> extends Grid<T> {
    public MovieList(List<T> movies) {
        setItems(movies);

        addColumn(Movie::getTitle).setHeader("Title");
        addColumn(movie -> {
            if (movie != null) {
                return movie.getGenre().getName();
            }
            else
                return "";
        }) .setHeader("Genre");
        addColumn(Movie::getDirector).setHeader("Director");
        addColumn(Movie::getReleaseDate).setHeader("Release Date");

    }
}
