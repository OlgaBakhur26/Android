package com.example.weather.settingsunit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.weather.R;

import static com.example.weather.settingsunit.UNIT_TYPE.CELSIUS;
import static com.example.weather.settingsunit.UNIT_TYPE.FAHRENHEIT;
import static com.example.weather.settingsunit.UnitSettingsPrefs.getUnitSettings;

public class SettingsActivity extends AppCompatActivity {

    private UnitSettingsPrefs unitSettings;
    private RadioButton viewRadioCelsius;
    private RadioButton viewRadioFahrenheit;
    private Toolbar toolbar;
    private UNIT_TYPE unitType;

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        viewRadioCelsius = findViewById(R.id.viewRadioCelsius);
        viewRadioFahrenheit = findViewById(R.id.viewRadioFahrenheit);
        toolbar = findViewById(R.id.toolbarSettings);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setPrimaryRadioButtonsState();
    }

    private void setPrimaryRadioButtonsState() {
        unitSettings = getUnitSettings(getApplicationContext());
        unitType = UNIT_TYPE.valueOf(unitSettings.loadUnitType());
        switch (unitType) {
            case CELSIUS:
                viewRadioCelsius.setChecked(true);
                break;
            case FAHRENHEIT:
                viewRadioFahrenheit.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void updateUnitSettings() {
        if (viewRadioCelsius.isChecked()) {
            unitSettings.saveUnitType(CELSIUS);
        } else if (viewRadioFahrenheit.isChecked()) {
            unitSettings.saveUnitType(FAHRENHEIT);
        }
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            updateUnitSettings();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}