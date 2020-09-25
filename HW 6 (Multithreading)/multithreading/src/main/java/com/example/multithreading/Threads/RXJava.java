package com.example.multithreading.Threads;

import com.example.multithreading.database.Contact;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RXJava implements DatabaseMethods{

    @Override
    public void getAll(Supplier<List<Contact>> supplier, Consumer<List<Contact>> consumer) {

    }

    @Override
    public void getById(Supplier<Contact> supplier, Consumer<Contact> consumer) {

    }

    @Override
    public void addContact(Runnable runnable) {

    }

    @Override
    public void editContact(Runnable runnable) {

    }

    @Override
    public void removeContact(Runnable runnable) {

    }
}
