package com.example.multithreading.Threads;

import com.example.multithreading.database.Contact;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface DatabaseMethods {
    void getAll(Supplier<List<Contact>> supplier, Consumer<List<Contact>> consumer);
    void getById(Supplier<Contact> supplier, Consumer<Contact> consumer);
    void addContact(Runnable runnable);
    void editContact(Runnable runnable);
    void removeContact(Runnable runnable);
}
