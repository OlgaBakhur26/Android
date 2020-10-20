package com.example.weather.citieslistmvvm;

import android.content.Context;
import android.content.SharedPreferences;

public class CityNamePrefs {

    private static final String CITY_NAME_PREFS_FILE_NAME = "CITY_NAME_PREFS_FILE_NAME";
    private static final String KEY_PREFERENCE_CITY_NAME = "KEY_PREFERENCE_CITY_NAME";
    private static CityNamePrefs cityNamePrefs;
    private static SharedPreferences preferences;
    private String firstLoad_cityName = "Minsk";

    private CityNamePrefs(Context context) {
        preferences = context.getSharedPreferences(CITY_NAME_PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static CityNamePrefs getCityNamePrefs(Context context) {
        if (cityNamePrefs == null) {
            cityNamePrefs = new CityNamePrefs(context);
        }
        return cityNamePrefs;
    }

    public static CityNamePrefs getCityNamePrefs() {
        return cityNamePrefs;
    }

    public void saveCityName(String cityName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PREFERENCE_CITY_NAME, cityName);
        editor.apply();
    }

    public String loadCityName() {
        return preferences.getString(KEY_PREFERENCE_CITY_NAME, firstLoad_cityName);
    }
}
