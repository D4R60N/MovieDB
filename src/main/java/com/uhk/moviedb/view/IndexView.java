package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Role;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.MovieServiceImpl;
import com.uhk.moviedb.view.component.MovieList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Route(value = "", layout = MovieDBAppLayout.class)
@PermitAll
@AnonymousAllowed
public class IndexView extends VerticalLayout {
    private final MovieServiceImpl movieService;
    private final SecurityService securityService;
    private List<Movie> movies;
    private PageRequest pageRequest;
    private boolean filtered = false;


    public IndexView(MovieServiceImpl movieService, SecurityService securityService) {
        this.securityService = securityService;
        this.movieService = movieService;
        pageRequest = PageRequest.of(0, 10);
        Button button = new Button("Add Movie", e -> {
            getUI().ifPresent(ui -> ui.navigate("addMovie"));
        });
        if (securityService.getAuthenticatedUser() == null || !securityService.getAuthenticatedUser().getRole().getRoleName().equals(Role.RoleEnum.MODERATOR)) {
            button.setVisible(false);
        }

        movies = movieService.findAll(pageRequest).stream().toList();

        H1 h1 = new H1("Welcome to MovieDB");
        H2 h2 = new H2("Movies");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search for movie");
        searchField.setMinWidth("300px");
        Button searchButton = new Button("Search");
        Button nextButton = new Button("Load more");
        nextButton.setVisible(!isDoneLoading());
        nextButton.addClickListener(e -> {
            pageRequest = PageRequest.of(pageRequest.getPageNumber() + 1, pageRequest.getPageSize());
            if (filtered) {
                List<Movie> newMovies = movieService.searchMovieByTitle(searchField.getValue(), pageRequest);
                if (!newMovies.isEmpty()) {
                    movies = new ArrayList<>(movies);
                    movies.addAll(newMovies);
                }
            } else {
                List<Movie> newMovies = movieService.findAll(pageRequest).stream().toList();
                if (!newMovies.isEmpty()) {
                    movies = new ArrayList<>(movies);
                    movies.addAll(newMovies);
                }
            }
            nextButton.setVisible(!isDoneLoading());
            removeAll();
            add(
                    h1,
                    button,
                    h2,
                    horizontalLayout,
                    new MovieList<Movie>(movies),
                    nextButton
            );
        });
        searchButton.addClickListener(e -> {
            if (searchField.getValue().isEmpty()) {
                filtered = false;
                movies = movieService.findAll(pageRequest).stream().toList();
            } else {
                filtered = true;
                pageRequest = PageRequest.of(0, 10);
                movies = movieService.searchMovieByTitle(searchField.getValue(), pageRequest);
            }
            removeAll();
            add(
                    h1,
                    button,
                    h2,
                    horizontalLayout,
                    new MovieList<Movie>(movies),
                    nextButton
            );
        });
        horizontalLayout.add(searchField, searchButton);


        add(
                h1,
                button,
                h2,
                horizontalLayout,
                new MovieList<Movie>(movies),
                nextButton
        );

    }

    private boolean isDoneLoading() {
        return movies.size() < pageRequest.getPageSize() * pageRequest.getPageNumber();
    }
}
