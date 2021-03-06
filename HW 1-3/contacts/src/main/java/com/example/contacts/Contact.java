package com.example.contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class Contact implements Serializable{

    private String personName;
    private String phone;
    private String email;
    private String id;

    public static List<Contact> contactsList = new ArrayList<>();

    private Contact(String personName, String phone, String email) {
        this.personName = personName;
        this.phone = phone;
        this.email = email;
        this.id = UUID.randomUUID().toString();
    }

    public String getPersonName() {
        return personName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(personName, contact.personName) &&
                Objects.equals(phone, contact.phone) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName, phone, email, id);
    }


    public static class Builder{

        private String personName;
        private String phone;
        private String email;
        private String id;


        public Builder setPersonName(String personName) {
            this.personName = personName;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Contact build(){
            return new Contact(personName, phone, email);
        }
    }
}
