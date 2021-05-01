package com.edu.javasport.controllers;

import com.edu.javasport.bll.constants.TeamConstants;
import com.edu.javasport.bll.constants.UserConstants;
import com.edu.javasport.bll.errors.GeneralErrors;
import com.edu.javasport.bll.errors.TeamErrors;
import com.edu.javasport.bll.errors.UserErrors;
import com.edu.javasport.bll.service.UserService;
import com.edu.javasport.dto.response.Response;
import com.edu.javasport.dto.user.AuthorizeUserDto;
import com.edu.javasport.dto.user.RegisterUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) {

        String result = userService.registerUser(registerUserDto);
        final Response response = new Response();

        if (result == UserConstants.USER_REGISTERED) {
            response.message = result;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (result == UserErrors.USER_ALREADY_EXISTS) {
            response.error= result;
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } else {
            response.error = GeneralErrors.UNKNOWN_ERROR;
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("user/authorize")
    public ResponseEntity<?> authorizeUser(@RequestBody AuthorizeUserDto authorizeUserDto) {
        String result = userService.authorizeUser(authorizeUserDto);
        final Response response = new Response();

        if (result == UserConstants.USER_AUTHORIZED) {
            response.message = result;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (result == UserErrors.USER_DOES_NOT_EXIST) {
            response.error= result;
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } else {
            response.error = GeneralErrors.UNKNOWN_ERROR;
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
