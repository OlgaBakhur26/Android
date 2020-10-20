package com.example.multithreading.Threads;

import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FutureRepository implements DataRepository {

    // Денис, в методах нужна обработка исключений (handle/exceptionally)?

    private ContactDao contactDao;
    private Executor mainThreadPoolExecutor;
    private ThreadPoolExecutor subsidiaryThreadPoolExecutor;

    public FutureRepository(ContactDao contactDao, Executor mainThreadPoolExecutor,
                            ThreadPoolExecutor subsidiaryThreadPoolExecutor) {
        this.contactDao = contactDao;
        this.mainThreadPoolExecutor = mainThreadPoolExecutor;
        this.subsidiaryThreadPoolExecutor = subsidiaryThreadPoolExecutor;
    }

    @Override
    public void getAll(final RepositoryListener<List<Contact>> listener) {
        CompletableFuture.supplyAsync(new Supplier<List<Contact>>() {
            @Override
            public List<Contact> get() {
                return contactDao.getAll();
            }
        }, subsidiaryThreadPoolExecutor)
                .thenAcceptAsync(new Consumer<List<Contact>>() {
                    @Override
                    public void accept(List<Contact> contactList) {
                        listener.onDataReceived(contactList);
                    }
                }, mainThreadPoolExecutor);
    }

    @Override
    public void getById(String contactId, RepositoryListener<Contact> listener) {
        CompletableFuture.supplyAsync(new Supplier<Contact>() {
            @Override
            public Contact get() {
                return contactDao.getById(contactId);
            }
        }, subsidiaryThreadPoolExecutor)
                .thenAcceptAsync(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) {
                        listener.onDataReceived(contact);
                    }
                }, mainThreadPoolExecutor);
    }

    @Override
    public void addContact(Contact contact) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                contactDao.insert(contact);
            }
        }, subsidiaryThreadPoolExecutor);
    }

    @Override
    public void editContact(Contact contact) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                contactDao.update(contact);
            }
        }, subsidiaryThreadPoolExecutor);
    }

    @Override
    public void removeContact(Contact contact) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                contactDao.delete(contact);
            }
        });
    }

    @Override
    public void close() {
        mainThreadPoolExecutor = null;
        if (subsidiaryThreadPoolExecutor != null){
            subsidiaryThreadPoolExecutor.shutdown();
        }
    }
}
