package com.example.uniprogramming;

import com.example.uniprogramming.security.MyUserDetails;
import com.example.uniprogramming.security.UserDTO;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ViewController {
    UsersRepository usersRepository;

    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    public ViewController(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @RequestMapping(value="/users")
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
                model.addAttribute("loggedUser", usersRepository.findByUserName(userDetails.getUsername()).orElse(null));

                return "users";
    }

}
