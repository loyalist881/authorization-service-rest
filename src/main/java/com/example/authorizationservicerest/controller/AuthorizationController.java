package com.example.authorizationservicerest.controller;

import com.example.authorizationservicerest.permission.Authorities;
import com.example.authorizationservicerest.domain.User;
import com.example.authorizationservicerest.service.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorizationController {
    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@Valid User user) {
        return service.getAuthorities(user);
    }
}
