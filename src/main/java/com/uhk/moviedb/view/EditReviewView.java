package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Review;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.MovieService;
import com.uhk.moviedb.service.ReviewService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Route(value = "movie/review/edit", layout = MovieDBAppLayout.class)
@RolesAllowed({"ROLE_MODERATOR", "ROLE_CRITIC"})
public class EditReviewView extends VerticalLayout implements HasUrlParameter<Long> {
    private final MovieService movieService;
    private final ReviewService reviewService;
    private final SecurityService securityService;
    private Long reviewId;
    private Review review;


    public EditReviewView(MovieService movieService, SecurityService securityService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.securityService = securityService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        reviewId = aLong;
        review = reviewService.findFirstById(reviewId).orElse(null);
        if(review == null) {
            Notification notification = Notification.show("Review not found!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            getUI().ifPresent(ui -> ui.navigate(""));
        }
        if(review.getAuthor().getId() != securityService.getAuthenticatedUser().getId()) {
            Notification notification = Notification.show("You are not the author of this review!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            getUI().ifPresent(ui -> ui.navigate(""));
        }
        FormLayout form = new FormLayout();
        TextArea reviewText = new TextArea("Write your review...");
        reviewText.setValue(review.getContent());
        reviewText.getStyle().set("minHeight", "600px");
        reviewText.getStyle().set("minWidth", "600px");

        reviewText.setRequired(true);

        form.addFormItem(reviewText, "Review");

        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {
            List<String> errorList = new ArrayList<>();
            if(reviewText.isEmpty())
                errorList.add("Review");

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

            review.setContent(reviewText.getValue());
            review.setCreatedAt(Instant.now());
            reviewService.save(review);
            getUI().ifPresent(ui -> ui.navigate("movie/" + review.getMovie().getId()));
            Notification notification = Notification.show("Review Saved!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });


        add(
                new H1("Write review for " + review.getMovie().getTitle()),
                form,
                saveButton
        );
    }
}
