package com.example.uniprogramming.controllers;

import com.example.uniprogramming.models.Contact;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.services.ContactService;
import com.example.uniprogramming.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    @Autowired
    public ContactController(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Contact> getAll(
            @RequestParam("user") Optional<Integer> userid,
            @RequestParam("company") Optional<Integer> companyid
    ){
        if(userid.isPresent()){
            return contactService.getByUser(userid.get());
        }
        if(companyid.isPresent()){
            return contactService.getByCompany(companyid.get());
        }
        return contactService.getAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Contact addContact(@RequestBody Contact contact){
        return contactService.saveContact(contact);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Contact getContact(@PathVariable long id){
        return contactService.getContact(id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.PUT)
    public Contact deleteContact(@PathVariable long id){
        Contact contact = contactService.getContact(id);
        User logged = userService.getLoggedUser();
        if (!logged.getRole().getName().equals("ROLE_ADMIN") && logged.getId() != contact.getUser().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        return contactService.deleteContact(id);
    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.PUT)
    public Contact modifyContact(@PathVariable long id, @RequestBody Contact contact){
        User logged = userService.getLoggedUser();
        if (!logged.getRole().getName().equals("ROLE_ADMIN") && logged.getId() != contact.getUser().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        return contactService.saveContact(contact);
    }



}
