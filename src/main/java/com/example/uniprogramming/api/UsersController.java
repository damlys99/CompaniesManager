package com.example.uniprogramming.api;


import com.example.uniprogramming.models.Role;
import com.example.uniprogramming.models.RolesRepository;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.models.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    UsersRepository usersRepository;
    RolesRepository rolesRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UsersRepository usersRepository, RolesRepository rolesRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getPage(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable userPage = PageRequest.of(currentPage - 1, pageSize);
        return usersRepository.findAllByIsDeletedIsFalse(userPage);

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAll() {
        return usersRepository.findAllByIsDeletedIsFalse();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable long id) {
        return usersRepository.findById(id);
    }


    @RequestMapping(value = "/delete/{idToDelete}", method = RequestMethod.PUT)
    public List<User> delUser(@PathVariable int idToDelete) {
        User userGettingDeleted = usersRepository.findById(idToDelete);
        userGettingDeleted.delete();
        usersRepository.save(userGettingDeleted);
        return usersRepository.findAllByIsDeletedIsFalse();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        //user.addToRoles(rolesRepository.findByName("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return user;
    }

    @RequestMapping(value = "/{id}/setroles", method = RequestMethod.POST)
    public User setRoles(@RequestBody Set<String> roles, @PathVariable int id){
        User user = usersRepository.findById(id);
        for(String role: roles){
            user.addToRoles(rolesRepository.findByName(role));
        }
        usersRepository.save(user);
        return user;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public long getCount() {
        return usersRepository.countByIsDeletedIsFalse();
    }

    @RequestMapping("/logged")
    public Optional<User> logged(@CurrentSecurityContext(expression = "authentication.principal") Principal principal) {
        return usersRepository.findByUserName(principal.getName());
    }


}
