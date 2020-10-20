package com.example.multithreading.Threads;

import com.example.multithreading.database.Contact;

import java.util.List;

public interface DataRepository {
    void getAll(RepositoryListener<List<Contact>> listener);

    void getById(String contactId, RepositoryListener<Contact> listener);

    void addContact(Contact contact);

    void editContact(Contact contact);

    void removeContact(Contact contact);

    void close();
}
