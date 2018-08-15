package com.verbosetech.yoohoo.interfaces;

import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.User;

import java.util.ArrayList;

/**
 * Created by a_man on 01-01-2018.
 */

public interface HomeIneractor {
    User getUserMe();

    ArrayList<Contact> getLocalContacts();

}
