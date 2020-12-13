package com.example.uniprogramming.controllers;

import com.example.uniprogramming.services.CompanyService;
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
    private final CompanyService companyService;

    @Autowired
    public ViewController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @RequestMapping("/")
    public String index(){
        return "redirect:/companies";
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

    @RequestMapping(value = "/companies/{id}")
    public String company(@PathVariable int id,
                       Model model
    ) {
        model.addAttribute("loggedUser", userService.getLoggedUser());
        model.addAttribute("company", companyService.getCompany(id));
        return "company";
    }
}
