package com.example.uniprogramming.services;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.Note;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.repositories.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NotesRepository notesRepository;
    private  final CompanyService companyService;
    private final UserService userService;


    @Autowired
    public NoteService(NotesRepository notesRepository, CompanyService companyService, UserService userService) {
        this.notesRepository = notesRepository;
        this.companyService = companyService;
        this.userService = userService;
    }


    public Note getNote(long id){
        return notesRepository.findById(id);
    }

    public List<Note> getAll(){
        return notesRepository.findAllByIsDeletedIsFalse();
    }

    public List<Note> getByCompany(long companyid){
        return notesRepository.findAllByIsDeletedIsFalseAndCompany(companyService.getCompany(companyid));
    }

    public List<Note> getByUser(long userid){
        return notesRepository.findAllByIsDeletedIsFalseAndUser(userService.getUser(userid));
    }

    public Note saveNote(Note note){
        notesRepository.save(note);
        return note;
    }

    public Note deleteNote(long id){
        Note note = notesRepository.findById(id);
        note.setDeleted(true);
        return saveNote(note);
    }

}

