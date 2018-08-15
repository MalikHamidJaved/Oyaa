package com.verbosetech.yoohoo.models;

/**
 * Created by a_man on 10-Aug-17.
 */

public class Contact {
    private String phoneNumber, name;

    public Contact(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return getPhoneNumber().equals(contact.getPhoneNumber());

    }

    @Override
    public int hashCode() {
        return getPhoneNumber().hashCode();
    }
}
