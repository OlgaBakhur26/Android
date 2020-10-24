package com.example.customview;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationManager {

    private static final String SETTINGS_NAME = "NotificationPrefs";
    private static final String KEY_PREFERENCE_CHECKED = "KEY_PREFERENCE_CHECKED";
    private static NotificationManager notificationManager;
    private static SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private NOTIFICATION_TYPE firstLoad_notificationType = NOTIFICATION_TYPE.SNACKBAR;

    private NotificationManager(Context context) {
        preferences = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public static NotificationManager getNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = new NotificationManager(context.getApplicationContext());
        }
        return notificationManager;
    }

    public void saveChoice(NOTIFICATION_TYPE notificationType) {
        editor = preferences.edit();
        editor.putString(KEY_PREFERENCE_CHECKED, notificationType.name());
        editor.apply();
    }

    public String loadChoice() {
        return preferences.getString(KEY_PREFERENCE_CHECKED, String.valueOf(firstLoad_notificationType));
    }
}
