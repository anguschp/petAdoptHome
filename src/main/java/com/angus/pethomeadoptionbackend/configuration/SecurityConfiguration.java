package com.angus.pethomeadoptionbackend.configuration;


import com.angus.pethomeadoptionbackend.model.MyNewUserDetails;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.angus.pethomeadoptionbackend.service.Impl.MyUserDetailsService;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Autowired
    private MyUserDetailsService myUserDetailsService; // Inject the component

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, ServletRequest httpServletRequest) throws Exception {
        return httpSecurity

                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/user/register").permitAll()
                        .requestMatchers("/user/testvalid").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/user/logout").permitAll()
                        .requestMatchers("/pet/petlist").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/error").permitAll() // Add this line
                        .requestMatchers("/admin/approve").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login->login.loginProcessingUrl("/user/login")
                        .successHandler(loginSuccessHandler())
                        .failureHandler(loginFailureHandler())
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionStrategy(new MyInvalidSessionStrategy()) // Disable redirect for invalid sessions
                        .sessionFixation().newSession() // Changed from migrateSession
                        .maximumSessions(1)
                        .expiredSessionStrategy(event -> {
                            event.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
                            event.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
                            new ObjectMapper().writeValue(event.getResponse().getWriter(), Map.of(
                                    "error", "Session expired",
                                    "message", "Please login again"
                            ));
                        }) // Use sessions

                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {

                            HttpSession session = request.getSession(false);
                            if(session != null)
                            {
                                session.invalidate();

                                Cookie cookie = new Cookie("JSESSIONIDhaha", null);
                                cookie.setPath("/");
                                cookie.setHttpOnly(true);
                                cookie.setSecure(true);  // If using HTTPS
                                cookie.setMaxAge(0);     // Immediately expire
                                response.addCookie(cookie);
                            }

                            response.setStatus(HttpStatus.OK.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            new ObjectMapper().writeValue(response.getWriter(), Map.of(
                                    "message", "Logout successful"
                            ));
                        })
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(){
        return (request, response, authentication ) ->{

            MyNewUserDetails userDetails = (MyNewUserDetails) authentication.getPrincipal();

            logger.info("User login[Success] | " + userDetails.getUsername());

            HttpSession session = request.getSession(); // Get or create the session
            String sessionId = session.getId();

            Cookie sessionCookie = new Cookie("JSESSIONID", sessionId); // Use your custom name
            sessionCookie.setPath("/");
            sessionCookie.setHttpOnly(true);
            sessionCookie.setSecure(false); // Set to "true" in production (HTTPS)
            sessionCookie.setMaxAge((int) session.getMaxInactiveInterval()); // Match session timeout
            sessionCookie.setAttribute("SameSite", "Lax"); // For SameSite policy
            response.addCookie(sessionCookie);


            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getWriter(), Map.of(
                    "user_Id" , userDetails.getUserId(),
                    "isAuthenticated", true,
                    "username", userDetails.getUsername(),
                    "message", "Login Successfully",
                    "assigned_role" , userDetails.getAuthorities()
            ));
        };
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {

            logger.info("User login[Fail] | " + request.getParameterNames());


            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getWriter(), Map.of(
                    "isAuthenticated", false,
                    "message", "Invalid username or password"
            ));
        };
    }


}
