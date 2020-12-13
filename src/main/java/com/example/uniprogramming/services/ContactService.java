package com.example.uniprogramming.services;

import com.example.uniprogramming.models.Company;
import com.example.uniprogramming.models.Contact;
import com.example.uniprogramming.models.User;
import com.example.uniprogramming.repositories.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactsRepository contactsRepository;
    private  final CompanyService companyService;
    private final UserService userService;


    @Autowired
    public ContactService(ContactsRepository contactsRepository, CompanyService companyService, UserService userService) {
        this.contactsRepository = contactsRepository;
        this.companyService = companyService;
        this.userService = userService;
    }


    public Contact getContact(long id){
        return contactsRepository.findById(id);
    }

    public List<Contact> getAll(){
        return contactsRepository.findAllByIsDeletedIsFalse();
    }

    public List<Contact> getByCompany(long companyid){
        return contactsRepository.findAllByIsDeletedIsFalseAndCompany(companyService.getCompany(companyid));
    }

    public List<Contact> getByUser(long userid){
        return contactsRepository.findAllByIsDeletedIsFalseAndUser(userService.getUser(userid));
    }

    public Contact saveContact(Contact contact){
        contactsRepository.save(contact);
        return contact;
    }

    public Contact deleteContact(long id){
        Contact contact = contactsRepository.findById(id);
        contact.setDeleted(true);
        return saveContact(contact);
    }

}

