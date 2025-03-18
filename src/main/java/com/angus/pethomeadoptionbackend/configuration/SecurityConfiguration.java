package com.angus.pethomeadoptionbackend.configuration;


import com.angus.pethomeadoptionbackend.model.MyNewUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.angus.pethomeadoptionbackend.service.Impl.MyUserDetailsService;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private MyUserDetailsService myUserDetailsService; // Inject the component

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
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
                        .anyRequest().hasAuthority("USER")
                )
                .formLogin(login->login.loginProcessingUrl("/user/login")
                        .successHandler(loginSuccessHandler())
                        .failureHandler(loginFailureHandler())
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Use sessions
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpStatus.OK.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            new ObjectMapper().writeValue(response.getWriter(), Map.of(
                                    "message", "Logout successful"
                            ));
                        }) // Return JSON on logout
                        
                        .deleteCookies("JSESSIONID")
                )
                .httpBasic(Customizer.withDefaults())
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
