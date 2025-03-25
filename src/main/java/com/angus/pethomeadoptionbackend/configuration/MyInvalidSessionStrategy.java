package com.angus.pethomeadoptionbackend.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.InvalidSessionStrategy;

import java.io.IOException;

public class MyInvalidSessionStrategy implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        System.out.println("Invalid Session found");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("applciation/json;charset=utf-8");
        response.getWriter().write("{\"message\":\"Invalid Session, please login.\"}");
    }
}
