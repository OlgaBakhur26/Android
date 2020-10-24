package com.example.contentresolver;

import java.util.UUID;

public class Contact {

    private String personName;
    private CONTACT_TYPE contactType;
    private String contactDetails;
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
}
