package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Genre;
import com.uhk.moviedb.service.GenreService;
import com.uhk.moviedb.service.MovieServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.ZoneId;

@Route(value = "movie/edit", layout = MovieDBAppLayout.class)
@RolesAllowed("ROLE_MODERATOR")
public class EditMovieView extends VerticalLayout implements HasUrlParameter<Long> {
    MovieServiceImpl movieService;
    GenreService genreService;

    private Long movieId;


    public EditMovieView(MovieServiceImpl movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;


    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        movieId = aLong;
        movieService.findById(movieId).ifPresent(movie -> {
            TextField title = new TextField("Title");
            title.setValue(movie.getTitle());
            TextArea description = new TextArea("Description");
            description.setValue(movie.getDescription());
            ComboBox<Genre> genre = new ComboBox<Genre>("Genre", genreService.findAll());
            genre.setItemLabelGenerator(Genre::getName);
            genre.setValue(movie.getGenre());
            TextField director = new TextField("Director");
            director.setValue(movie.getDirector());
            TextArea actors = new TextArea("Actors");
            actors.setValue(movie.getActors());
            NumberField duration = new NumberField("Duration (minutes)");
            duration.setValue(movie.getDuration().doubleValue());
            DatePicker releaseDate = new DatePicker("Release Date");
            releaseDate.setValue(movie.getReleaseDate().atZone(ZoneId.systemDefault()).toLocalDate());
            NumberField rating = new NumberField("Rating");
            rating.setValue(movieService.calculateAverageRating(movie));
            rating.setReadOnly(true);

            duration.setStep(1);
            duration.setMin(1);
            duration.setStepButtonsVisible(true);
            duration.setI18n(new NumberField.NumberFieldI18n()
                    .setBadInputErrorMessage("Invalid number format")
                    .setStepErrorMessage("Duration must be rounded to the nearest minute"));

            title.setRequired(true);
            description.setRequired(true);
            releaseDate.setRequired(true);
            director.setRequired(true);
            actors.setRequired(true);
            duration.setRequired(true);
            genre.setRequired(true);
            add(
                    new H1("Edit Movie"),
                    new FormLayout(
                            title,
                            description,
                            genre,
                            director,
                            actors,
                            releaseDate,
                            duration,
                            rating
                    ),
                    new Button("Save", e -> {
                        movie.setTitle(title.getValue());
                        movie.setDescription(description.getValue());
                        movie.setReleaseDate(releaseDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        movie.setDirector(director.getValue());
                        movie.setActors(actors.getValue());
                        movie.setDuration(duration.getValue().intValue());
                        movie.setGenre(genre.getValue());
                        movieService.save(movie);
                        Notification notification = Notification.show("Movie Saved!");
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    })
            );
        });
    }
}
