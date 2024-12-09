package com.uhk.moviedb.view;

import com.uhk.moviedb.model.Role;
import com.uhk.moviedb.model.User;
import com.uhk.moviedb.service.RoleService;
import com.uhk.moviedb.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("sign-up")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    UserService userService;
    RoleService roleService;

    public RegistrationView(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField emailField = new TextField("Email");
        TextField usernameField = new TextField("Username");
        TextField passwordField = new TextField("Password");
        TextField passwordField2 = new TextField("Repeat Your Password");

        emailField.setRequired(true);
        usernameField.setRequired(true);
        passwordField.setRequired(true);
        passwordField2.setRequired(true);

        User u = new User();

        emailField.addValueChangeListener(event -> {
            u.setEmail(event.getValue());
        });
        usernameField.addValueChangeListener(event -> {
            u.setUsername(event.getValue());
        });
        passwordField.addValueChangeListener(event -> {
            u.setPassword(event.getValue());
        });

        add(
                new H1("MovieDB"),
                emailField,
                usernameField,
                passwordField,
                new Button("Sign Up", event -> {
                    if (!checkPassword(passwordField.getValue(), passwordField2.getValue())) {
                        passwordField.setErrorMessage("Passwords do not match");
                        passwordField2.setErrorMessage("Passwords do not match");
                        return;
                    }
                    if(!userService.checkUsername(usernameField.getValue())) {
                        usernameField.setErrorMessage("Username already exists");
                        return;
                    }

                    u.setRole(roleService.getRoleByEnum(Role.RoleEnum.USER));
                    userService.save(u);
                    getUI().ifPresent(ui -> ui.navigate("login"));

                })
        );
    }

    private boolean checkPassword(String password, String password2) {
        return password.equals(password2);
    }
}
