package com.angus.pethomeadoptionbackend.controller;

import com.angus.pethomeadoptionbackend.dto.LoginResponse;
import com.angus.pethomeadoptionbackend.dto.RegisterRequest;
import com.angus.pethomeadoptionbackend.dto.UserLoginRequest;
import com.angus.pethomeadoptionbackend.service.UserService;
import com.angus.pethomeadoptionbackend.model.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001/")
@Validated
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId)
    {
        User user = userService.getUserById(userId);

        if(user != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/email/{userEmail}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String userEmail)
    {
        User user = userService.getUserByUsername(userEmail);

        if(user != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username)
    {
        User user = userService.getUserByUsername(username);

        if(user != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerNewUser(@RequestBody @Valid RegisterRequest reg)
    {
            User user = userService.createUser(reg);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

//    @PostMapping("/user/login")
//    public ResponseEntity<?> userlogin(@RequestBody @Valid UserLoginRequest login , HttpSession session)
//    {
//        System.out.println("Login checking");
//        try{
//            boolean result = userService.authenticateUser(login);
//            if(result)
//            {
//                session.setAttribute("user", login.getUsername());
//                return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(true , "Login successfully"));
//            }else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Invalid username or password"));
//            }
//        }
//        catch(Exception ex)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.fillInStackTrace());
//        }
//
//
//    }
//
//
//    @PostMapping("/user/logout")
//    public ResponseEntity<?> userlogout(HttpServletResponse response , HttpSession session){
//
//
//        session.invalidate();
//        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", null)
//                .path("/")
//                .maxAge(0)
//                .secure(true) // Enable for HTTPS
//                .httpOnly(true)
//                .sameSite("Lax")
//                .build();
//
//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//
//        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(false, "Logout successfully"));
//    }


}
