package com.example.multithreading.Threads;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.storage.StorageManager;

import com.example.multithreading.R;

public class MultithreadingRegimeManager {

    private static final String SETTINGS_FILE_NAME = "MultithreadingRegimePreferences";
    private static final String KEY_PREFERENCE_MULTITHREADING_REGIME = "KEY_PREFERENCE_MULTITHREADING_REGIME";
    private static MultithreadingRegimeManager regimeManager;
    private static SharedPreferences preferences;
    private int firstLoadRegime = R.id.executorFuture;

    private MultithreadingRegimeManager(Context context){
        preferences = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }
    public static MultithreadingRegimeManager getRegimeManager(Context context) {
        if (regimeManager == null) {
            regimeManager = new MultithreadingRegimeManager(context);
        }
        return regimeManager;
    }
    public void saveStorageType(STORAGE_TYPE storageType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PREFERENCE_MULTITHREADING_REGIME, storageType.name());
        editor.apply();
    }
    public String loadStorageType() {
        return preferences.getString(KEY_PREFERENCE_MULTITHREADING_REGIME, String.valueOf(firstLoad_storageType));
    }


}
