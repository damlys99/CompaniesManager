package com.example.uniprogramming.search;

import com.example.uniprogramming.models.Contact;

import java.util.List;

public interface CustomContactsRepository {

    public List<Contact> getFiltered(long userId, long companyId, String surname);
}
