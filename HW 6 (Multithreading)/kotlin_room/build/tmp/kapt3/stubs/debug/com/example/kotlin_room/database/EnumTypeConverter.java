package com.example.kotlin_room.database;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0007\u00a8\u0006\b"}, d2 = {"Lcom/example/kotlin_room/database/EnumTypeConverter;", "", "()V", "enumToString", "", "contactType", "Lcom/example/kotlin_room/database/CONTACT_TYPE;", "stringToEnum", "kotlin_room_debug"})
public final class EnumTypeConverter {
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.TypeConverter
    public final java.lang.String enumToString(@org.jetbrains.annotations.NotNull
    com.example.kotlin_room.database.CONTACT_TYPE contactType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.TypeConverter
    public final com.example.kotlin_room.database.CONTACT_TYPE stringToEnum(@org.jetbrains.annotations.NotNull
    java.lang.String contactType) {
        return null;
    }
    
    public EnumTypeConverter() {
        super();
    }
}