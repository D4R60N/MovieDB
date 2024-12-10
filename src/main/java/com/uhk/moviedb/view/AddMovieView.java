package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Genre;
import com.uhk.moviedb.model.Movie;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.GenreService;
import com.uhk.moviedb.service.MovieServiceImpl;
import com.uhk.moviedb.service.ProfileService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.flow.component.textfield.NumberField.*;

@Route(value = "addMovie", layout = MovieDBAppLayout.class)
@RolesAllowed("ROLE_MODERATOR")
public class AddMovieView extends VerticalLayout {
    private final MovieServiceImpl movieService;
    private final GenreService genreService;


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
        TextField trailer = new TextField("Trailer");
        trailer.setPlaceholder("Youtube Link");

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
        form.addFormItem(trailer, "trailer");
        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {
            List<String> errorList = new ArrayList<>();
            if(title.isEmpty())
                errorList.add("Title");
            if(description.isEmpty())
                errorList.add("Description");
            if(releaseDate.isEmpty())
                errorList.add("Release Date");
            if(director.isEmpty())
                errorList.add("Director");
            if(actors.isEmpty())
                errorList.add("Actors");
            if(duration.isEmpty())
                errorList.add("Duration");
            if(genreCB.isEmpty())
                errorList.add("Genre");
            if(!errorList.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Missing values: ");
                int i = 0;
                for(String error : errorList) {
                    String end = ", ";
                    if(i >= errorList.size() - 1)
                        end = ".";
                    stringBuilder.append(error + end);
                    i++;
                }
                Notification notification = Notification.show(stringBuilder.toString());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            Movie movie = new Movie();
            movie.setTitle(title.getValue());
            movie.setDescription(description.getValue());
            movie.setReleaseDate(releaseDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            movie.setDirector(director.getValue());
            movie.setActors(actors.getValue());
            movie.setDuration(duration.getValue().intValue());
            movie.setGenre(genreCB.getValue());
            movie.setTrailer(trailer.getValue());
            movieService.save(movie);
            getUI().ifPresent(ui -> ui.navigate(""));
            Notification notification = Notification.show("Movie Saved!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });


        add(
                new H1("Add Movie"),
                form,
                saveButton
        );

    }
}
