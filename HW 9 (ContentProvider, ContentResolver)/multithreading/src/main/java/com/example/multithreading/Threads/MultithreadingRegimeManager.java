package com.example.multithreading.Threads;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.multithreading.R;

public class MultithreadingRegimeManager {

    private static final String SETTINGS_FILE_NAME = "MultithreadingRegimePreferences";
    private static final String KEY_PREFERENCE_MULTITHREADING_REGIME = "KEY_PREFERENCE_MULTITHREADING_REGIME";
    private static MultithreadingRegimeManager regimeManager;
    private static SharedPreferences preferences;
    private int firstLoadRegime = R.id.executorFuture;

    private MultithreadingRegimeManager(Context context) {
        preferences = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static MultithreadingRegimeManager getRegimeManager(Context context) {
        if (regimeManager == null) {
            regimeManager = new MultithreadingRegimeManager(context);
        }
        return regimeManager;
    }

    public void saveMultithreadingRegime(int regimeID) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_PREFERENCE_MULTITHREADING_REGIME, regimeID);
        editor.apply();
    }

    public int loadMultithreadingRegime() {
        return preferences.getInt(KEY_PREFERENCE_MULTITHREADING_REGIME, firstLoadRegime);
    }


}
