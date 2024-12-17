package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Rating;
import com.uhk.moviedb.model.Review;
import com.uhk.moviedb.model.Role;
import com.uhk.moviedb.model.User;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.*;
import com.uhk.moviedb.view.component.RatingComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Section;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Route(value = "movie", layout = MovieDBAppLayout.class)
@PermitAll
@AnonymousAllowed
public class MovieView extends VerticalLayout implements HasUrlParameter<Long> {
    private final MovieService movieService;
    private final GenreService genreService;
    private final RatingService ratingService;
    private final SecurityService securityService;
    private final ProfileService profileService;
    private final ReviewService reviewService;

    private Long movieId;
    private User author;
    private PageRequest pageRequest;


    public MovieView(MovieServiceImpl movieService, GenreService genreService, RatingService ratingService, SecurityService securityService, ProfileService profileService, ReviewService reviewService) {
        this.movieService = movieService;
        this.genreService = genreService;
        this.ratingService = ratingService;
        this.securityService = securityService;
        this.profileService = profileService;
        this.reviewService = reviewService;
        pageRequest = PageRequest.of(0, 5);

        author = securityService.getAuthenticatedUser();
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
            VerticalLayout trailer = new VerticalLayout();
            if(movie.getTrailer() != null) {
                trailer.add(new Text("Trailer: "));
                IFrame iFrame = new IFrame("https://www.youtube.com/embed/" + movie.getTrailer());
                iFrame.setHeight("315px");
                iFrame.setWidth("560px");
                iFrame.setAllow("accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture");
                iFrame.getElement().setAttribute("allowfullscreen", true);
                iFrame.getElement().setAttribute("frameborder", "0");
                trailer.add(iFrame);
            }

            SplitLayout splitLayout = new SplitLayout(
                    new VerticalLayout(genre,
                            director,
                            actors,
                            duration,
                            releaseDate),
                    trailer
            );
            splitLayout.setSplitterPosition(50);
            splitLayout.setHeightFull();
            splitLayout.setWidthFull();

            Section rating = new Section();
            Rating userRating =author == null ? new Rating() : ratingService.findByMovieAndAuthor(movie, author).orElse(new Rating());
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
            if (author == null) {
                rating.setEnabled(false);
            }
            Button editButton = new Button("Edit", e -> {
                getUI().ifPresent(ui -> ui.navigate("movie/edit/" + movieId));
            });
            Button reviewButton = new Button("Review", e -> {
                getUI().ifPresent(ui -> ui.navigate("movie/review/" + movieId));
            });
            HorizontalLayout buttons = new HorizontalLayout(editButton, reviewButton);

            Optional<Review> myReview = reviewService.findFirstByAuthorAndMovie(author, movie);

            if(author == null) {
                editButton.setVisible(false);
                reviewButton.setVisible(false);
            }
            else if (author.getRole() != null && !author.getRole().getRoleName().equals(Role.RoleEnum.MODERATOR)) {
                editButton.setVisible(false);
                if(!author.getRole().getRoleName().equals(Role.RoleEnum.CRITIC) || myReview.isPresent()) {
                    reviewButton.setVisible(false);
                }
            }

            Text review = new Text("Reviews");
            VerticalLayout reviewLayout = new VerticalLayout();

            List<Review> reviews = reviewService.findByMovie(movie, pageRequest);
            reviews.forEach(r -> addReview(reviewLayout, r, reviewButton, myReview));
            Button nextButton = new Button("Load more");
            nextButton.setVisible(!isDoneLoadingReview(reviews));
            nextButton.addClickListener(e -> {
                pageRequest = PageRequest.of(pageRequest.getPageNumber() + 1, pageRequest.getPageSize());
                List<Review> newReviews = reviewService.findByMovie(movie, pageRequest);
                newReviews.forEach(r -> addReview(reviewLayout, r, reviewButton, myReview));
                nextButton.setVisible(!isDoneLoadingReview(newReviews));
            });


            add(
                    new HorizontalLayout(new  H1(movie.getTitle())),
                    description,
                    splitLayout,
                    rating,
                    buttons,
                    new Section(),
                    review,
                    reviewLayout,
                    nextButton
            );
        });
    }
    private void addReview(VerticalLayout reviewLayout, Review r, Button reviewButton, Optional<Review> myReview) {
        {
            HorizontalLayout reviewInnerLayout = new HorizontalLayout();
            RouterLink t = new RouterLink(r.getAuthor().getUsername() + ": ", ProfileView.class, r.getAuthor().getId());
            TextArea content =  new TextArea();
            content.setValue(r.getContent());
            content.setReadOnly(true);
            content.setLabel("Review:");
            reviewInnerLayout.add(new VerticalLayout(t, content));
            if(myReview.isPresent() && myReview.get().getId().equals(r.getId())) {
                Button editReviewButton = new Button("Edit", e -> {
                    getUI().ifPresent(ui -> ui.navigate("movie/review/edit/" + r.getId()));
                });
                editReviewButton.addThemeVariants(ButtonVariant.LUMO_ICON);
                reviewInnerLayout.add(editReviewButton);

                Button deleteReviewButton = new Button("Delete", e -> {
                    reviewService.delete(r);
                    reviewInnerLayout.removeAll();
                    reviewInnerLayout.setVisible(false);
                    reviewButton.setVisible(true);
                });
                reviewInnerLayout.add(deleteReviewButton);
            }

            reviewLayout.add(reviewInnerLayout);
        }
    }
    private boolean isDoneLoadingReview(List<Review> reviews) {
        return reviews.size() < pageRequest.getPageSize();
    }
}
