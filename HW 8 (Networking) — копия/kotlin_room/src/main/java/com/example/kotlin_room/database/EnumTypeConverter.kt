package com.example.kotlin_room.database

import androidx.room.TypeConverter

class EnumTypeConverter {
    @TypeConverter
    fun enumToString(contactType: CONTACT_TYPE): String {
        return contactType.name
    }

    @TypeConverter
    fun stringToEnum(contactType: String): CONTACT_TYPE {
        return CONTACT_TYPE.valueOf(contactType)
    }
}