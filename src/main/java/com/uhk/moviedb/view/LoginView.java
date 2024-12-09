package com.uhk.moviedb.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        var loginForm = new LoginForm();
        loginForm.setAction("login");
        Button registerButton = new Button("Sign Up");
        registerButton.addClickListener(e -> registerButton.getUI().ifPresent(ui -> ui.navigate("sign-up")));
        loginForm.setForgotPasswordButtonVisible(false);

        add(
                new H1("MovieDB"),
                loginForm,
                registerButton
        );
    }
}
