package com.example.uniprogramming.security.controllers;

import com.example.uniprogramming.security.models.UserDTO;
import com.example.uniprogramming.security.services.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class AccountController {

    private final UserDTOService userDTOService;

    @Autowired
    public AccountController(UserDTOService userDTOService){
        this.userDTOService = userDTOService;
    }


    @RequestMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "registered", required = false) String registered,
                            Model model) {
        String errorMessge = null;
        String loginAlert = null;
        if(error != null) {
            errorMessge = "Username or password is incorrect!";
        }
        if(logout != null) {
            errorMessge = "You have been successfully logged out!";
        }
        if(registered != null) {
            loginAlert = "Your account has been created! You can now log in.";
        }
        model.addAttribute("errorMessge", errorMessge);
        model.addAttribute("loginAlert", loginAlert);
        return "login";
    }

    @RequestMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }

    @GetMapping(value="/register")
    public String register(@ModelAttribute UserDTO userDTO, Model model){
        model.addAttribute(userDTO);
        return "register";
    }

    @PostMapping(value="/register")
    public String save(@Valid UserDTO userDTO, BindingResult bindingResult, RedirectAttributes ra){

        if(userDTOService.userExists(userDTO.getUsername())){
            bindingResult.addError(new FieldError("userDTO", "username",
                    "User with that username already exists"));
        }
        if(userDTO.getPassword() != null && userDTO.getRepassword() != null){
            if(!userDTO.getPassword().equals(userDTO.getRepassword())){
                bindingResult.addError(new FieldError("userDTO", "repassword",
                        "Passwords do not match"));
            }
        }
        if(bindingResult.hasErrors()){
            return "register";
        }
        userDTOService.register(userDTO);
        return "redirect:/login?registered=true";
    }
}
