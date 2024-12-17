package com.uhk.moviedb.view;

import com.uhk.moviedb.model.*;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;

import java.util.List;


@Route(value = "profile", layout = MovieDBAppLayout.class)
@PermitAll
@AnonymousAllowed
public class ProfileView extends VerticalLayout implements HasUrlParameter<Long> {

    private final RatingService ratingService;
    private final SecurityService securityService;
    private final UserService userService;
    private final ReviewService reviewService;

    private Long userId;
    private User loggedUser;
    private PageRequest reviewPageRequest;
    private PageRequest ratingPageRequest;

    public ProfileView(RatingService ratingService, SecurityService securityService, UserService userService, ReviewService reviewService) {

        this.ratingService = ratingService;
        this.securityService = securityService;
        this.userService = userService;
        this.reviewService = reviewService;
        reviewPageRequest = PageRequest.of(0, 5);
        ratingPageRequest = PageRequest.of(0, 5);
        loggedUser = securityService.getAuthenticatedUser();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        removeAll();
        userId = aLong;
        User user = userService.findUserById(userId).orElse(null);
        if (user == null) {
            this.getUI().ifPresent(ui -> ui.navigate("profile/" + loggedUser.getId()));
            return;
        }
        Profile profile = user.getProfile();
        Button editProfileButton = new Button("Edit profile", new Icon(VaadinIcon.EDIT));
        editProfileButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editProfileButton.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("profile/edit"));
        });

        add(new H1("Profile of " + user.getUsername()),
                new VerticalLayout(
                        new Text("Email: " + user.getEmail()),
                        new Section(),
                        new Text("About Me: " + profile.getAboutMe()),
                        new Section(),
                        new Text("Contact: " + profile.getContact()),
                        new Section(),
                        new Text("Role: " + user.getRole().getRoleName())
                )
        );
        if (loggedUser != null && loggedUser.getId().equals(userId)) {
            add(editProfileButton);
        }
        TabSheet tabSheet = new TabSheet();
        List<Rating> ratings = ratingService.findByAuthor(user, ratingPageRequest);
        if (!ratings.isEmpty()) {
            VerticalLayout ratingsLayout = new VerticalLayout();
            addRating(ratingsLayout, ratings);
            Button nextButton = new Button("Load more");
            nextButton.setVisible(!isDoneLoadingRating(ratings));
            nextButton.addClickListener(e -> {
                ratingPageRequest = PageRequest.of(ratingPageRequest.getPageNumber() + 1, ratingPageRequest.getPageSize());
                List<Rating> newRatings = ratingService.findByAuthor(user, ratingPageRequest);
                addRating(ratingsLayout, newRatings);
                nextButton.setVisible(!isDoneLoadingRating(newRatings));
            });
            tabSheet.add("Ratings", new VerticalLayout(ratingsLayout, nextButton));
        } else {
            tabSheet.add("Ratings", new VerticalLayout(new Text("No ratings")));
        }
        List<Review> reviews = reviewService.findByAuthor(user, reviewPageRequest);
        if (!reviews.isEmpty()) {
            VerticalLayout reviewsLayout = new VerticalLayout();
            addReviews(reviewsLayout, reviews);
            Button nextButton = new Button("Load more");
            nextButton.setVisible(!isDoneLoadingReview(reviews));
            nextButton.addClickListener(e -> {
                reviewPageRequest = PageRequest.of(reviewPageRequest.getPageNumber() + 1, reviewPageRequest.getPageSize());
                List<Review> newReviews = reviewService.findByAuthor(user, reviewPageRequest);
                addReviews(reviewsLayout, newReviews);
                nextButton.setVisible(!isDoneLoadingReview(newReviews));
            });
            tabSheet.add("Reviews", new VerticalLayout(reviewsLayout, nextButton));
        } else {
            tabSheet.add("Reviews", new VerticalLayout(new Text("No reviews")));
        }
        add(tabSheet);

    }

    private boolean isDoneLoadingReview(List<Review> reviews) {
        return reviews.size() < reviewPageRequest.getPageSize();
    }
    private boolean isDoneLoadingRating(List<Rating> ratings) {
        return ratings.size() < ratingPageRequest.getPageSize();
    }

    private void addReviews(VerticalLayout reviewsLayout, List<Review> reviews) {
        if (!reviews.isEmpty()) {
            reviews.forEach(review -> {
                Movie movie = review.getMovie();
                Section section = new Section();
                Anchor movieLink = new Anchor("movie/" + movie.getId());
                movieLink.add(new H2(movie.getTitle()));
                TextArea content = new TextArea();
                content.setLabel("Review:");
                content.setValue(review.getContent());
                content.setReadOnly(true);
                section.add(movieLink);
                section.add(content);
                section.add(new Section());
                section.add(new Text("Date: " + review.getCreatedAt()));
                reviewsLayout.add(section);
            });
        }
    }
    private void addRating(VerticalLayout ratingsLayout, List<Rating> ratings) {
        if (!ratings.isEmpty()) {
            ratings.forEach(rating -> {
                Movie movie = rating.getMovie();
                Section section = new Section();
                Anchor movieLink = new Anchor("movie/" + movie.getId());
                movieLink.add(new H2(movie.getTitle()));
                section.add(movieLink);
                section.add(new Text("Rating: " + rating.getRating()));
                section.add(new Section());
                section.add(new Text("Date: " + rating.getCreatedAt()));
                ratingsLayout.add(section);
            });
        }
    }
}
