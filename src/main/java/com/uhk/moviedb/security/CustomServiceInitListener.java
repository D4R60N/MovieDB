package com.uhk.moviedb.security;

import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component
public class CustomServiceInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addSessionInitListener(sessionInitEvent -> {
            VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
        });
    }
}
