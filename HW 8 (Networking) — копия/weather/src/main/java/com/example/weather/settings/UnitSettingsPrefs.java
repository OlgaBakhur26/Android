package com.example.weather.settings;

import android.content.Context;
import android.content.SharedPreferences;
import static com.example.weather.settings.UNIT_TYPE.CELSIUS;

public class UnitSettingsPrefs {

    private static final String SETTINGS_FILE_NAME = "SETTINGS_FILE_NAME";
    private static final String KEY_PREFERENCE_UNIT_TYPE = "KEY_PREFERENCE_UNIT_TYPE";
    private static UnitSettingsPrefs unitSettingsPrefs;
    private static SharedPreferences preferences;
    private UNIT_TYPE firstLoad_unitType = CELSIUS;

    private UnitSettingsPrefs(Context context) {
        preferences = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static UnitSettingsPrefs getUnitSettings(Context context) {
        if (unitSettingsPrefs == null) {
            unitSettingsPrefs = new UnitSettingsPrefs(context);
        }
        return unitSettingsPrefs;
    }

    public static UnitSettingsPrefs getUnitSettings() {
        return unitSettingsPrefs;
    }

    public void saveUnitType(UNIT_TYPE unitType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PREFERENCE_UNIT_TYPE, unitType.name());
        editor.apply();
    }

    public String loadUnitType() {
        return preferences.getString(KEY_PREFERENCE_UNIT_TYPE, firstLoad_unitType.name());
    }
}
