package com.uhk.moviedb.security;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.AuthenticationException;

public class CustomErrorHandler implements ErrorHandler {
    @Override
    public void error(ErrorEvent event) {
        Throwable throwable = event.getThrowable();
        if (throwable instanceof AuthenticationException) {
            UI.getCurrent().navigate("401");
        } else {
            Notification.show("An unexpected error occurred.");
        }
    }
}
