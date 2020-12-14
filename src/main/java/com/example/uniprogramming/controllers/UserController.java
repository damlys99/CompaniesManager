package com.example.uniprogramming.controllers;


import com.example.uniprogramming.models.User;
import com.example.uniprogramming.services.UserService;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/{id}/setrole", method = RequestMethod.PUT)
    public User setRoles(@RequestBody TextNode role, @PathVariable int id) {
        if (!logged().getRole().getName().equals("ROLE_ADMIN") && !(logged().getId() == id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        return userService.setRole(id, role.asText());
    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.PUT)
    public User modifyCompany(@RequestBody User user, @PathVariable int id) {
        User logged = userService.getLoggedUser();
        if (id < 4) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!");
        }
        if (!logged.getRole().getName().equals("ROLE_ADMIN") && logged.getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        if(userService.userExists(user.getName()) && user.getId() != id){
            throw new ResponseStatusException(HttpStatus.FOUND, "Company Already exists");
        }
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public long count() {
        return userService.count();
    }

    @RequestMapping("/logged")
    public User logged() {
        return userService.getLoggedUser();
    }

}
