package com.example.multithreading.Threads;

import com.example.multithreading.database.Contact;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Executor_CompletableFuture implements DatabaseMethods{

    // + обработка исключений (handler/exceptionally)

    ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void getAll(Supplier<List<Contact>> supplier, Consumer<List<Contact>> consumer) {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        CompletableFuture.supplyAsync(supplier, threadPoolExecutor)
                .thenAcceptAsync(consumer, threadPoolExecutor);

    }

    @Override
    public void getById(Supplier<Contact> supplier, Consumer<Contact> consumer) {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        CompletableFuture.supplyAsync(supplier, threadPoolExecutor)
                .thenAcceptAsync(consumer, threadPoolExecutor);
    }

    @Override
    public void addContact(Runnable runnable) {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        CompletableFuture.runAsync(runnable, threadPoolExecutor);
    }

    @Override
    public void editContact(Runnable runnable) {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        CompletableFuture.runAsync(runnable, threadPoolExecutor);
    }

    @Override
    public void removeContact(Runnable runnable) {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        CompletableFuture.runAsync(runnable, threadPoolExecutor);
    }
}
