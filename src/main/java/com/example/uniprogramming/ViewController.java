package com.example.uniprogramming;

import com.example.uniprogramming.models.UsersRepository;
import com.example.uniprogramming.security.MyUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
