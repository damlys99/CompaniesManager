package com.example.uniprogramming.controllers;

import com.example.uniprogramming.models.Note;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.services.NoteService;
import com.example.uniprogramming.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Note> getAll(
            @RequestParam("user") Optional<Integer> userid,
            @RequestParam("company") Optional<Integer> companyid
    ){
        if(userid.isPresent()){
            return noteService.getByUser(userid.get());
        }
        if(companyid.isPresent()){
            return noteService.getByCompany(companyid.get());
        }
        return noteService.getAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Note addNote(@RequestBody Note note){
        return noteService.saveNote(note);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Note getNote(@PathVariable long id){
        return noteService.getNote(id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.PUT)
        public Note deleteNote(@PathVariable long id){
        Note note = noteService.getNote(id);
        User logged = userService.getLoggedUser();
        if (!logged.getRole().getName().equals("ROLE_ADMIN") && logged.getId() != note.getUser().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        return noteService.deleteNote(id);
    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.PUT)
    public Note modifyNote(@PathVariable long id, @RequestBody Note note){
        User logged = userService.getLoggedUser();
        if (!logged.getRole().getName().equals("ROLE_ADMIN") && logged.getId() != note.getUser().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't do that!");
        }
        return noteService.saveNote(note);
    }



}
