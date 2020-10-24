package com.example.multithreading.Threads;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class HandlerRepository implements DataRepository {

    private ContactDao contactDao;
    private ThreadPoolExecutor subsidiaryThreadPoolExecutor;
    private Handler handler;
    private Message message;

    public HandlerRepository(ContactDao contactDao, ThreadPoolExecutor subsidiaryThreadPoolExecutor) {
        this.contactDao = contactDao;
        this.subsidiaryThreadPoolExecutor = subsidiaryThreadPoolExecutor;
    }

    @Override
    public void getAll(final RepositoryListener<List<Contact>> listener) {
        handler = new Handler();
        subsidiaryThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                message = handler.obtainMessage(0, contactDao.getAll());
                handler.sendMessage(message);
            }
        });

        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 0) {
                    listener.onDataReceived((List<Contact>) msg.obj);
                }
                return false;
            }
        });
    }

    @Override
    public void getById(String contactId, RepositoryListener<Contact> listener) {
        handler = new Handler();
        subsidiaryThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                message = handler.obtainMessage(0, contactDao.getById(contactId));
                handler.sendMessage(message);
            }
        });

        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 0) {
                    listener.onDataReceived((Contact) msg.obj);
                }
                return false;
            }
        });
    }

    @Override
    public void addContact(Contact contact) {
        subsidiaryThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.insert(contact);
            }
        });
    }

    @Override
    public void editContact(Contact contact) {
        subsidiaryThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.update(contact);
            }
        });
    }

    @Override
    public void removeContact(Contact contact) {
        subsidiaryThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.delete(contact);
            }
        });
    }

    @Override
    public void close() {
        if (subsidiaryThreadPoolExecutor != null) {
            subsidiaryThreadPoolExecutor.shutdown();
        }
    }
}
