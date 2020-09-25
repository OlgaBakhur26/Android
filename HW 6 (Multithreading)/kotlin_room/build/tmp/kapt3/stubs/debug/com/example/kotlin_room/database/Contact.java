package com.example.kotlin_room.database;

import java.lang.System;

@androidx.room.Entity(tableName = "CONTACT_TABLE")
@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\n\"\u0004\b\u0013\u0010\f\u00a8\u0006 "}, d2 = {"Lcom/example/kotlin_room/database/Contact;", "Ljava/io/Serializable;", "personName", "", "contactType", "Lcom/example/kotlin_room/database/CONTACT_TYPE;", "contactDetails", "id", "(Ljava/lang/String;Lcom/example/kotlin_room/database/CONTACT_TYPE;Ljava/lang/String;Ljava/lang/String;)V", "getContactDetails", "()Ljava/lang/String;", "setContactDetails", "(Ljava/lang/String;)V", "getContactType", "()Lcom/example/kotlin_room/database/CONTACT_TYPE;", "setContactType", "(Lcom/example/kotlin_room/database/CONTACT_TYPE;)V", "getId", "getPersonName", "setPersonName", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "kotlin_room_debug"})
public final class Contact implements java.io.Serializable {
    @org.jetbrains.annotations.NotNull
    private java.lang.String personName;
    @org.jetbrains.annotations.NotNull
    private com.example.kotlin_room.database.CONTACT_TYPE contactType;
    @org.jetbrains.annotations.NotNull
    private java.lang.String contactDetails;
    @org.jetbrains.annotations.NotNull
    @androidx.room.ColumnInfo(index = true)
    @androidx.room.PrimaryKey
    private final java.lang.String id = null;
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPersonName() {
        return null;
    }
    
    public final void setPersonName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.kotlin_room.database.CONTACT_TYPE getContactType() {
        return null;
    }
    
    public final void setContactType(@org.jetbrains.annotations.NotNull
    com.example.kotlin_room.database.CONTACT_TYPE p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getContactDetails() {
        return null;
    }
    
    public final void setContactDetails(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    public Contact(@org.jetbrains.annotations.NotNull
    java.lang.String personName, @org.jetbrains.annotations.NotNull
    com.example.kotlin_room.database.CONTACT_TYPE contactType, @org.jetbrains.annotations.NotNull
    java.lang.String contactDetails, @org.jetbrains.annotations.NotNull
    java.lang.String id) {
        super();
    }
    
    public Contact() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.kotlin_room.database.CONTACT_TYPE component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.kotlin_room.database.Contact copy(@org.jetbrains.annotations.NotNull
    java.lang.String personName, @org.jetbrains.annotations.NotNull
    com.example.kotlin_room.database.CONTACT_TYPE contactType, @org.jetbrains.annotations.NotNull
    java.lang.String contactDetails, @org.jetbrains.annotations.NotNull
    java.lang.String id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object p0) {
        return false;
    }
}