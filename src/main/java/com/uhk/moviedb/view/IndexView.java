package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.model.Role;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.MovieServiceImpl;
import com.uhk.moviedb.view.component.MovieList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MovieDBAppLayout.class)
@PermitAll
@AnonymousAllowed
public class IndexView extends VerticalLayout {
    MovieServiceImpl movieService;
    SecurityService securityService;


    public IndexView(MovieServiceImpl movieService, SecurityService securityService) {
        this.movieService = movieService;
        Button button = new Button("Add Movie", e -> {
            getUI().ifPresent(ui -> ui.navigate("addMovie"));
        });
        if (securityService.getAuthenticatedUser() == null || !securityService.getAuthenticatedUser().getRole().getRoleName().equals(Role.RoleEnum.MODERATOR)) {
            button.setVisible(false);
        }
        add(
                new H1("Welcome to MovieDB"),
                button,
                new H2("Movies"),
                new MovieList<Movie>(movieService.findAll())
        );

    }
}
