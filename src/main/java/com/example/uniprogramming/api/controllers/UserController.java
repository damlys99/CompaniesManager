package com.example.uniprogramming.api.controllers;


import com.example.uniprogramming.api.services.UserService;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.security.MyUserDetailsService;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getPage(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        return userService.getPage(currentPage, pageSize);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAll() {
        return userService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id) {
        if (!(logged().getRole().getName().equals("ROLE_ADMIN") || logged().getRole().getName().equals("ROLE_MODERATOR")) && !(logged().getId() == id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        return userService.getUser(id);
    }


    @RequestMapping(value = "/{id}/delete", method = RequestMethod.PUT)
    public User deleteUser(@PathVariable int id) {
        if (id < 4) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!");
        }
        if (!logged().getRole().getName().equals("ROLE_ADMIN") && !(logged().getId() == id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        return userService.deleteUser(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        if(userService.getUser(user.getUserName()).isPresent()){
            throw new ResponseStatusException(HttpStatus.FOUND, "User Already exists");
        }
        return userService.addUser(user);
    }

    @RequestMapping(value = "/{id}/setrole", method = RequestMethod.PUT)
    public User setRoles(@RequestBody TextNode role, @PathVariable int id) {
        if (!logged().getRole().getName().equals("ROLE_ADMIN") && !(logged().getId() == id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        return userService.setRole(id, role.asText());
    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.PUT)
    public User modifyUser(@RequestBody User user, @PathVariable int id) {
        if (id < 4) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!");
        }
        if (!logged().getRole().getName().equals("ROLE_ADMIN") && !(logged().getId() == id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        Optional<User> optionalUser = userService.getUser(user.getUserName());
        if(optionalUser.isPresent() && optionalUser.get().getId() != id){
            throw new ResponseStatusException(HttpStatus.FOUND, "User Already exists");
        }
        return userService.modifyUser(id, user);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public long count() {
        return userService.count();
    }

    @RequestMapping("/logged")
    public User logged() {//@CurrentSecurityContext(expression = "authentication.principal") Principal principal) {
        return userService.getLoggedUser();
    }


}
