package com.example.multithreading.Threads;

import android.util.Log;

import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RXJavaRepository implements DataRepository {

    private ContactDao contactDao;
    private CompositeDisposable disposeBag = new CompositeDisposable();

    public RXJavaRepository(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public void getAll(final RepositoryListener<List<Contact>> listener) {
        Disposable disposableGetAll = Single.create(new SingleOnSubscribe<List<Contact>>() {
            @Override
            public void subscribe(SingleEmitter<List<Contact>> emitter) throws Exception {
                List<Contact> contactList = contactDao.getAll();
                try {
                    emitter.onSuccess(contactList);
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<Contact>>() {
                    @Override
                    public void accept(List<Contact> list) {
                        listener.onDataReceived(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error", throwable.toString() + " in class: RXJavaRepository; method: getAll");
                    }
                });

        disposeBag.add(disposableGetAll);
    }

    @Override
    public void getById(String contactId, RepositoryListener<Contact> listener) {
        Disposable disposableGetById = Single.create(new SingleOnSubscribe<Contact>() {
            @Override
            public void subscribe(SingleEmitter<Contact> emitter) {
                Contact contact = contactDao.getById(contactId);
                try {
                    emitter.onSuccess(contact);
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) throws Exception {
                        listener.onDataReceived(contact);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error", throwable.toString() + " in class: RXJavaRepository; method: getById");
                    }
                });
        disposeBag.add(disposableGetById);
    }

    @Override
    public void addContact(Contact contact) {
        Disposable disposableAdd = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                try {
                    contactDao.insert(contact);
                    emitter.onComplete();
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }

            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
        disposeBag.add(disposableAdd);
    }

    @Override
    public void editContact(Contact contact) {
        Disposable disposableEdit = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                try {
                    contactDao.update(contact);
                    emitter.onComplete();
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }

            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
        disposeBag.add(disposableEdit);
    }

    @Override
    public void removeContact(Contact contact) {
        Disposable disposableRemove = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) {
                try {
                    contactDao.delete(contact);
                    emitter.onComplete();
                } catch (Throwable throwable) {
                    emitter.onError(throwable);
                }

            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
        disposeBag.add(disposableRemove);
    }


    @Override
    public void close() {
        disposeBag.clear();
    }
}
