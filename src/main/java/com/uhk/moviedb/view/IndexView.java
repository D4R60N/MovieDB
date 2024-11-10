package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.service.MovieServiceImpl;
import com.uhk.moviedb.view.component.MovieList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
public class IndexView extends VerticalLayout {
    MovieServiceImpl movieService;


    public IndexView(MovieServiceImpl movieService) {
        this.movieService = movieService;



        add(
                new H1("Welcome to MovieDB"),
                new Button("Add Movie", e -> {
                    getUI().ifPresent(ui -> ui.navigate("addMovie"));
                }),
                new H2("Movies"),
                new MovieList<Movie>(movieService.findAll())
        );

    }
}
