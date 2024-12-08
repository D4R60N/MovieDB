package com.uhk.moviedb.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        var loginForm = new LoginForm();
        loginForm.setAction("login");
        Button registerButton = new Button("Register");
        registerButton.addClickListener(e -> registerButton.getUI().ifPresent(ui -> ui.navigate("registration")));

        add(
                new H1("MovieDB"),
                loginForm,
                registerButton
        );
    }
}
