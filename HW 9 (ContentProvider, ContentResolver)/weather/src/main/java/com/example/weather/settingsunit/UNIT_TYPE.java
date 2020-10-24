package com.example.weather.settingsunit;

public enum UNIT_TYPE {
    CELSIUS("metric", "C"),
    FAHRENHEIT("imperial", "F");

    private String measurement;
    private String letterIdentifier;

    UNIT_TYPE(String measurement, String letterIdentifier) {
        this.measurement = measurement;
        this.letterIdentifier = letterIdentifier;
    }

    public String getMeasurement() {
        return measurement;
    }

    public String getLetterIdentifier() {
        return letterIdentifier;
    }
}
