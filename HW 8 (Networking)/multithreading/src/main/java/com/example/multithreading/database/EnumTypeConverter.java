package com.example.multithreading.database;

import androidx.room.TypeConverter;

public class EnumTypeConverter {

    @TypeConverter
    public String enumToString(CONTACT_TYPE contactType){
        return contactType.name();
    }

    @TypeConverter
    public CONTACT_TYPE stringToEnum(String contactType){
        return CONTACT_TYPE.valueOf(contactType);
    }
}
