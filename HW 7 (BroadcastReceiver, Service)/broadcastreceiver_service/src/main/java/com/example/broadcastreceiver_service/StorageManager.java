package com.example.broadcastreceiver_service;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageManager {
    // 1. Этот класс не общается с остальным приложением. Ошибка возникает даже при его
    // упоминании в импортах в другихх классах.
    // Ошибка: MainActivity.kt: (15, 42): Unresolved reference: StorageManager и MyService.kt: (55, 34): Unresolved reference: getStorageManager

    // 2. И я не уверена, что правильно написала Singleton

    private static final String SETTINGS_FILE_NAME = "StoragePreferences";
    private static final String KEY_PREFERENCE_STORAGE_TYPE = "KEY_PREFERENCE_STORAGE_TYPE";
    private static StorageManager storageManager;
    private static SharedPreferences preferences;
    private STORAGE_TYPE firstLoad_storageType = STORAGE_TYPE.INTERNAL;

    private StorageManager(Context context){
        preferences = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static StorageManager getStorageManager(Context context) {
        preferences = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
        if (storageManager == null) {
            storageManager = new StorageManager(context.getApplicationContext());
        }
        return storageManager;
    }

    public void saveStorageType(STORAGE_TYPE storageType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PREFERENCE_STORAGE_TYPE, storageType.name());
        editor.apply();
    }

    public String loadStorageType() {
        return preferences.getString(KEY_PREFERENCE_STORAGE_TYPE, String.valueOf(firstLoad_storageType));
    }
}
