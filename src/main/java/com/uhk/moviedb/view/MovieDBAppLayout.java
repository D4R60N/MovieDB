package com.uhk.moviedb.view;

import com.uhk.moviedb.model.User;
import com.uhk.moviedb.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.context.SecurityContextHolder;

public class MovieDBAppLayout extends AppLayout {

    SecurityService securityService;
    VerticalLayout drawer;
    HorizontalLayout header;

    User user;

    public MovieDBAppLayout(SecurityService securityService) {
        this.securityService = securityService;
        user = securityService.getAuthenticatedUser();
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("MovieDB");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        toggle.addClickListener(
                buttonClickEvent -> {
                    drawer.setVisible(!drawer.isVisible());
                }
        );

        createHeader();
        createDrawer();

        addToNavbar(toggle, header);
        addToDrawer(drawer);
    }

    private void createHeader() {
        H1 logo = new H1("MovieDB");
        Anchor movieDBLink = new Anchor("", logo);

        logo.addClassNames("text-l", "m-m");
        HorizontalLayout accountBar = new HorizontalLayout();
        Icon icon = new Icon(VaadinIcon.COG);

        if (user != null) {
            Avatar avatar = new Avatar(user.getUsername());
            Anchor anchor = new Anchor("/profile", icon);
            anchor.getStyle().set("margin-top", "4px");
            accountBar.add(avatar, anchor);
            Button logoutBtn = new Button("Log Out", e -> securityService.logout());
            header = new HorizontalLayout(movieDBLink, logoutBtn, accountBar);
        } else {
            Anchor loginLink = new Anchor("login", "Sign In");
            Anchor registerLink = new Anchor("sign-up", "Sign Up");
            accountBar.add(loginLink, registerLink);
            header = new HorizontalLayout(movieDBLink, accountBar);
        }


        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(movieDBLink);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");
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