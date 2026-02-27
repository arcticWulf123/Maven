package com.phonebook.models;

public class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toCsvString() {
        return getName() + "," + getPhoneNumber() + "," + getEmail();
    }
    
}
