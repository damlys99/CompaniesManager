package com.example.uniprogramming.services;

import com.example.uniprogramming.models.Contact;
import com.example.uniprogramming.repositories.ContactsRepository;
import com.example.uniprogramming.search.CompanyDateOnly;
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

    public List<Contact> getAll(long userId, long companyId, String surname){
        return contactsRepository.getFiltered(userId,companyId, surname);
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

