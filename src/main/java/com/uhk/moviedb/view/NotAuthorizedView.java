package com.uhk.moviedb.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@Route(value = "403", layout = MovieDBAppLayout.class)
@PermitAll
@AnonymousAllowed
public class NotAuthorizedView  extends VerticalLayout {
    public NotAuthorizedView() {
        add("You are not authorized to view this page.");
    }
}
