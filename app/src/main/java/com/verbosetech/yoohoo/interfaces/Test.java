package com.verbosetech.yoohoo.interfaces;

import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.User;

import java.util.ArrayList;
import java.util.Map;

public interface Test {

    void TestClick(ArrayList<Contact> contactsData,ArrayList<User> myUsers,Map<String, ArrayList<String>> linkhmap);
}
