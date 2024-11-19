package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Rating;
import com.uhk.moviedb.model.User;
import com.uhk.moviedb.service.*;
import com.uhk.moviedb.view.component.RatingComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Section;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

import java.time.Instant;
import java.util.Optional;


@Route("movie")
@PermitAll
@AnonymousAllowed
public class MovieView extends VerticalLayout implements HasUrlParameter<Long> {
    MovieService movieService;
    GenreService genreService;
    RatingService ratingService;
    UserService userService;

    private Long movieId;
    private String actors;
    private User author;


    public MovieView(MovieServiceImpl movieService, GenreService genreService, RatingService ratingService, UserService userService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.ratingService = ratingService;
        this.userService = userService;
        //ošetřit
        author = userService.findUserById(1L).orElse(null);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        movieId = aLong;
        movieService.findById(movieId).ifPresent(movie -> {

            Section description = new Section();
            description.add(new Text("Description: " + movie.getDescription()));
            Section genre = new Section();
            genre.add(new Text("Genre: " + movie.getGenre().getName()));
            Section director = new Section();
            director.add(new Text("Director: " + movie.getDirector()));
            Section actors = new Section();
            actors.add(new Text("Actors: " + movie.getActors()));
            Section duration = new Section();
            duration.add(new Text("Duration: " + movie.getDuration() + " minutes"));
            Section releaseDate = new Section();
            releaseDate.add(new Text("Release Date: " + movie.getReleaseDate()));
            Section rating = new Section();
            Rating userRating = ratingService.findByMovieAndAuthor(movie, author).orElse(new Rating());
            rating.add(new Text("Rating: " + movieService.calculateAverageRating(movie)));
            RatingComponent ratingComponent = new RatingComponent();
            if (userRating.getRating() != null)
                ratingComponent.setRating(userRating.getRating());
            rating.add(ratingComponent);

            rating.add(new Button("Rate", e -> {
                if (author == null) {
                    return;
                }
                userRating.setRating(ratingComponent.getRating());
                userRating.setMovie(movie);
                userRating.setAuthor(author);
                userRating.setCreatedAt(Instant.now());
                ratingService.createRating(userRating);
                getUI().ifPresent(ui -> ui.refreshCurrentRoute(false));
            }));


            add(
                    new H1(movie.getTitle()),
                    description,
                    genre,
                    director,
                    actors,
                    duration,
                    releaseDate,
                    rating,

                    new Button("Edit", e -> {
                        getUI().ifPresent(ui -> ui.navigate("movie/edit/" + movieId));
                    })
            );
        });
    }
}
