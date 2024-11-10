package com.uhk.moviedb.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {
    public RegistrationView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        var loginForm = new LoginForm();
        loginForm.setAction("register");

        add(
                new H1("MovieDB"),
                loginForm
        );
    }
}
