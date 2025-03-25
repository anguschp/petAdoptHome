package com.angus.pethomeadoptionbackend.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {

        HttpServletResponse response = event.getResponse();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("applciation/json;charset=utf-8");
        response.getWriter().write("{\"message\":\"Expired Session, please login.\"}");

    }
}
