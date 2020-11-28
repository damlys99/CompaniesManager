package com.example.uniprogramming.controllers;

import com.example.uniprogramming.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
public class ViewController {

    private final UserService userService;

    @Autowired
    public ViewController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value="/users")
    public String index(
            Model model
            //@CurrentSecurityContext(expression = "authentication.principal") Principal principal
            ){
                model.addAttribute("loggedUser", userService.getLoggedUser());
                return "users";
    }

}
