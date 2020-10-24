package com.example.broadcastreceiver_service;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageManager {

    private static final String SETTINGS_FILE_NAME = "StoragePreferences";
    private static final String KEY_PREFERENCE_STORAGE_TYPE = "KEY_PREFERENCE_STORAGE_TYPE";
    private static StorageManager storageManager;
    private static SharedPreferences preferences;
    private STORAGE_TYPE firstLoad_storageType = STORAGE_TYPE.INTERNAL;

    private StorageManager(Context context){
        preferences = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static StorageManager getStorageManager(Context context) {
        if (storageManager == null) {
            storageManager = new StorageManager(context);
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
