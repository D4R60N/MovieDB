package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Genre;
import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.service.GenreService;
import com.uhk.moviedb.service.MovieServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

import java.time.ZoneId;
import java.util.List;

import static com.vaadin.flow.component.textfield.NumberField.*;

@Route("addMovie")
@PermitAll
@AnonymousAllowed
public class AddMovieView extends VerticalLayout {
    MovieServiceImpl movieService;
    GenreService genreService;


    public AddMovieView(MovieServiceImpl movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
        FormLayout form = new FormLayout();
        TextField title = new TextField("Title");
        TextArea description = new TextArea("Description");
        DatePicker releaseDate = new DatePicker("Release Date");
        TextField director = new TextField("Director");
        TextArea actors = new TextArea("Actors");
        NumberField duration = new NumberField("Duration (minutes)");
        ComboBox<Genre> genreCB = new ComboBox<>();

        title.setRequired(true);
        description.setRequired(true);
        releaseDate.setRequired(true);
        director.setRequired(true);
        actors.setRequired(true);
        duration.setRequired(true);
        genreCB.setRequired(true);

        duration.setStep(1);
        duration.setMin(1);
        duration.setStepButtonsVisible(true);
        duration.setI18n(new NumberFieldI18n()
                .setBadInputErrorMessage("Invalid number format")
                .setStepErrorMessage("Duration must be rounded to the nearest minute"));


        form.addFormItem(title, "title");
        form.addFormItem(description, "description");
        form.addFormItem(releaseDate, "releaseDate");

        List<Genre> genres = genreService.findAll();
        genreCB.setItems(genres);
        genreCB.setItemLabelGenerator(Genre::getName);

        form.addFormItem(genreCB, "genre");
        form.addFormItem(director, "director");
        form.addFormItem(actors, "actors");
        form.addFormItem(duration, "duration");
        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {
            Movie movie = new Movie();
            movie.setTitle(title.getValue());
            movie.setDescription(description.getValue());
            movie.setReleaseDate(releaseDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            movie.setDirector(director.getValue());
            movie.setActors(actors.getValue());
            movie.setDuration(duration.getValue().intValue());
            movie.setGenre(genreCB.getValue());
            movieService.save(movie);
            getUI().ifPresent(ui -> ui.navigate(""));
        });


        add(
                new H1("Add Movie"),
                form,
                saveButton
        );

    }
}
