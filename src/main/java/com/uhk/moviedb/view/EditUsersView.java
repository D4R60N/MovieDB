package com.uhk.moviedb.view;

import com.uhk.moviedb.model.*;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.MovieService;
import com.uhk.moviedb.service.ReviewService;
import com.uhk.moviedb.service.RoleService;
import com.uhk.moviedb.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Route(value = "users", layout = MovieDBAppLayout.class)
@RolesAllowed({"ROLE_MODERATOR"})
public class EditUsersView extends VerticalLayout {
    private final UserService userService;
    private final RoleService roleService;
    private List<Role> roles;


    public EditUsersView(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        roles = roleService.getAllRoles();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button searchButton = new Button("Search");
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search for user");
        searchField.setMinWidth("300px");
        VerticalLayout userLayout = new VerticalLayout();
        searchButton.addClickListener(e -> {
            if (searchField.getValue().isEmpty()) {
                Notification.show("Please enter a search term", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
            } else {
                List<User> users = userService.searchUserByUsername(searchField.getValue());
                if (users.isEmpty()) {
                    Notification.show("No users found", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
                } else {
                    userLayout.removeAll();
                    for (User user : users) {
                        HorizontalLayout userRow = new HorizontalLayout();
                        Text text = new Text(user.getUsername());
                        ComboBox<Role> roleCB = new ComboBox<>();
                        roleCB.setItems(roles);
                        roleCB.setItemLabelGenerator(role -> role.getRoleName().toString());
                        roleCB.setValue(user.getRole());
                        Button userButton = new Button("Save");
                        userButton.addClickListener(event -> {
                            user.setRole(roleCB.getValue());
                            userService.save(user);
                            Notification.show("User saved", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        });
                        userRow.add(text, roleCB, userButton);
                        userLayout.add(userRow);
                    }
                }
            }
        });
        horizontalLayout.add(searchField,searchButton);

        add(
                new H1("Edit Users"),
                horizontalLayout,
                userLayout
        );
    }
}
