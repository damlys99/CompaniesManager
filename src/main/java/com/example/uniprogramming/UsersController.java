package com.example.uniprogramming;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    
    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public List<User> getPage(@PathVariable int pageNumber){
        return usersRepository.findByIdGreaterThanEqualAndIdLessThan( (long)(pageNumber-1) * 10, (long)pageNumber * 10);
    }

}
