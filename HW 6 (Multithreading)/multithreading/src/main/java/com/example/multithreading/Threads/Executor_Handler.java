package com.example.multithreading.Threads;

import android.os.Handler;
import android.os.HandlerThread;

import com.example.multithreading.database.Contact;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Executor_Handler extends HandlerThread implements DatabaseMethods{

    private Handler handler;

    public Executor_Handler() {
        super("Executor_Handler");
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = new Handler(getLooper());
    }

    public void post(Runnable runnable){
        handler.post(runnable);
    }


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
