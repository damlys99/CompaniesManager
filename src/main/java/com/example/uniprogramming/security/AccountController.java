package com.example.uniprogramming.security;

import com.example.uniprogramming.models.UsersRepository;
import com.example.uniprogramming.ViewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class AccountController {
    UsersRepository usersRepository;

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);
    private final UserService userService;

    @Autowired
    public AccountController(UsersRepository usersRepository, UserService userService){
        this.usersRepository = usersRepository;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessge = null;
        if(error != null) {
            errorMessge = "Username or password is incorrect!";
        }
        if(logout != null) {
            errorMessge = "You have been successfully logged out!";
        }
        model.addAttribute("errorMessge", errorMessge);
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

        if(userService.userExists(userDTO.getUsername())){
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
        ra.addFlashAttribute("message", "Your account has been created! You can now log in.");
        userService.register(userDTO);
        return "redirect:/login";
    }
}