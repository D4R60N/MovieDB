package com.uhk.moviedb.view;

import com.uhk.moviedb.model.User;
import com.uhk.moviedb.security.MyUserDetails;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.RoleService;
import com.uhk.moviedb.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@Route("change-password")
@PermitAll
public class ChangePasswordView extends VerticalLayout {

    SecurityService securityService;
    UserService userService;


    public ChangePasswordView(UserService userService, SecurityService securityService) {
        this.userService = userService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField passwordField = new TextField("New Password");
        TextField passwordField2 = new TextField("Repeat Your Password");

        passwordField.setRequired(true);
        passwordField2.setRequired(true);

        add(
                new H1("MovieDB"),
                passwordField,
                passwordField2,
                new Button("Confirm", event -> {
                    if(!checkPassword(passwordField.getValue(), passwordField2.getValue())) {
                        passwordField.setErrorMessage("Passwords do not match");
                        passwordField2.setErrorMessage("Passwords do not match");
                    } else {
                        User u = securityService.getAuthenticatedUser();

                        u.setPassword(passwordField.getValue());

                        userService.save(u);

                        getUI().ifPresent(ui -> ui.navigate("/"));
                    }
                })
        );
    }

    private boolean checkPassword(String password, String password2) {
        return password.equals(password2);
    }

}
