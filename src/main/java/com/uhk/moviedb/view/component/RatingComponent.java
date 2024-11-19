package com.uhk.moviedb.view.component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class RatingComponent extends HorizontalLayout {

    private static final int MAX_STARS = 5;
    private int rating = 5;

    public RatingComponent() {
        for (int i = 1; i <= MAX_STARS; i++) {
            add(createStar(i));
        }
    }

    private Button createStar(int starNumber) {
        Button star = new Button(VaadinIcon.STAR.create());
        star.addClickListener(event -> setRating(starNumber));
        return star;
    }

    public void setRating(int rating) {
        this.rating = rating;
        updateStars();
    }

    private void updateStars() {
        int index = 0;
        for (com.vaadin.flow.component.Component child : getChildren().toList()) {
            if (child instanceof Button) {
                VaadinIcon icon = (index < rating) ? VaadinIcon.STAR : VaadinIcon.STAR_O;
                ((Button) child).setIcon(icon.create());
                index++;
            }
        }
    }
    public int getRating() {
        return rating;
    }
}
