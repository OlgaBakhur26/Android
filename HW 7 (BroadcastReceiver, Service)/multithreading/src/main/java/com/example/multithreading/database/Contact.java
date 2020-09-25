package com.example.multithreading.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "CONTACT_TABLE")
public class Contact implements Serializable {

    private String personName;
    @TypeConverters({EnumTypeConverter.class})
    private CONTACT_TYPE contactType;
    private String contactDetails;
    @PrimaryKey @ColumnInfo(index = true) @NonNull
    private String id;

    public Contact(String personName, CONTACT_TYPE contactType, String contactDetails) {
        this.personName = personName;
        this.contactType = contactType;
        this.contactDetails = contactDetails;
        this.id = UUID.randomUUID().toString();
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public CONTACT_TYPE getContactType() {
        return contactType;
    }

    public void setContactType(CONTACT_TYPE contactType) {
        this.contactType = contactType;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(personName, contact.personName) &&
                contactType == contact.contactType &&
                Objects.equals(contactDetails, contact.contactDetails) &&
                Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName, contactType, contactDetails, id);
    }
}
