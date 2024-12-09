package com.uhk.moviedb.view;

import com.uhk.moviedb.model.User;
import com.uhk.moviedb.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.context.SecurityContextHolder;

public class MovieDBAppLayout extends AppLayout {

    SecurityService securityService;
    VerticalLayout drawer;
    User user;

    public MovieDBAppLayout(SecurityService securityService) {
        user = securityService.getAuthenticatedUser();
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("MovieDB");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        H2 username = new H2(user == null ? "Anonymous" : user.getUsername());
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        toggle.addClickListener(
          buttonClickEvent -> {
            drawer.setVisible(!drawer.isVisible());
          }
        );

        createDrawer();

        addToNavbar(toggle, title);
        addToDrawer(drawer);
    }
    private void createDrawer() {
        RouterLink indexView = new RouterLink("Main Page", IndexView.class);
        RouterLink addMovieView = new RouterLink("Add new Movie", AddMovieView.class);
        indexView.setHighlightCondition(HighlightConditions.sameLocation());


        drawer = new VerticalLayout();

        drawer.add(new HorizontalLayout(new Icon(VaadinIcon.MOVIE), indexView));
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CRITIC") || grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

        }
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MODERATOR"))) {
            drawer.add(new HorizontalLayout(new Icon(VaadinIcon.FILE_ADD), addMovieView));
        }
    }

}