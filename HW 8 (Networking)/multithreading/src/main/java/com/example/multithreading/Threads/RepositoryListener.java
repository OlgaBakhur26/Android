package com.example.multithreading.Threads;

public interface RepositoryListener<T> {
    void onDataReceived(T data);
}
