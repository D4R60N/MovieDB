package com.uhk.moviedb.view;

import com.uhk.moviedb.model.*;
import com.uhk.moviedb.security.SecurityService;
import com.uhk.moviedb.service.RatingService;
import com.uhk.moviedb.service.ReviewService;
import com.uhk.moviedb.service.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;

import java.util.List;


@Route(value = "profile/edit", layout = MovieDBAppLayout.class)
@PermitAll
public class EditProfileView extends VerticalLayout{



    public EditProfileView(SecurityService securityService, UserService userService) {
        User user = securityService.getAuthenticatedUser();
        if (user == null) {
            getUI().ifPresent(ui -> ui.navigate(""));
        }
        assert user != null;
        Profile profile = user.getProfile();

        FormLayout formLayout = new FormLayout();
        TextField email = new TextField("Email");
        email.setValue(user.getEmail());
        TextArea aboutMe = new TextArea("About Me");
        aboutMe.setValue(profile.getAboutMe());
        aboutMe.setHeight("200px");
        aboutMe.setWidth("400px");
        TextField contact = new TextField("Contact");
        contact.setValue(profile.getContact());
        Button saveBtn = new Button("Save", e -> {
            user.setEmail(email.getValue());
            profile.setAboutMe(aboutMe.getValue());
            profile.setContact(contact.getValue());
            userService.save(user);
            getUI().ifPresent(ui -> ui.navigate("profile/" + user.getId()));
        });
        Button changePasswordBtn = new Button("Change Password", e -> {
            getUI().ifPresent(ui -> ui.navigate("change-password"));
        });
        changePasswordBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        formLayout.add(email, contact, aboutMe);

        add(new H1("Profile of " + user.getUsername()),
                formLayout,
                changePasswordBtn,
                new HorizontalLayout(
                        new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate("profile/" + user.getId()))),
                        saveBtn
                )
        );

    }


}
