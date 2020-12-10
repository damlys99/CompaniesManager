package com.example.uniprogramming.controllers;

import com.example.uniprogramming.services.UserService;
import com.example.uniprogramming.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ViewController {

    private final UserService userService;

    @Autowired
    public ViewController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users")
    public String users(
            Model model
            //@CurrentSecurityContext(expression = "authentication.principal") Principal principal
    ) {
        model.addAttribute("loggedUser", userService.getLoggedUser());
        return "users";
    }

    @RequestMapping(value = "/users/{id}")
    public String user(@PathVariable int id,
                       Model model
    ) {
        User logged = userService.getLoggedUser();
        if (!(logged.getRole().getName().equals("ROLE_ADMIN") || logged.getRole().getName().equals("ROLE_MODERATOR")) && !(logged.getId() == id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        model.addAttribute("loggedUser", userService.getLoggedUser());
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }

    @RequestMapping(value = "/companies")
    public String companies(
            Model model

    ) {
        model.addAttribute("loggedUser", userService.getLoggedUser());
        return "companies";
    }
}
