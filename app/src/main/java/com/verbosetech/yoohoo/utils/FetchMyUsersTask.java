package com.verbosetech.yoohoo.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by a_man on 01-12-2017.
 */

public class FetchMyUsersTask extends AsyncTask<Void, Void, ArrayList<Contact>> {
    private Context context;
    private FetchMyUsersTaskListener taskListener;
    private String myId;

    public FetchMyUsersTask(Context context, String myId) {
        if (context instanceof FetchMyUsersTaskListener) {
            this.taskListener = (FetchMyUsersTaskListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FetchMyUsersTaskListener");
        }

        this.context = context;
        this.myId = myId;
    }

    @Override
    protected ArrayList<Contact> doInBackground(Void... voids) {
        final ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null && !cursor.isClosed()) {
            cursor.getCount();
            while (cursor.moveToNext()) {
                int hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhoneNumber == 1) {
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s+", "");
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    if (Patterns.PHONE.matcher(number).matches()) {
                        contacts.add(new Contact(number, name));
                    }
                }
            }
            cursor.close();
        }
        return contacts;
    }

    @Override
    protected void onPostExecute(final ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        taskListener.fetchMyContactsResult(contacts);

        final ArrayList<User> toReturnUsers = new ArrayList<>();
//        final ArrayList<Contact> contactArrayList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = firebaseDatabase.getReference(Helper.REF_USER);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot usersSnapshot : dataSnapshot.getChildren()) {
                    User user = usersSnapshot.getValue(User.class);
                    if (user == null || user.getId() == null || user.getId().equals(myId))
                        continue;
                    for (Contact savedContact : contacts) {
                        if (Helper.contactMatches(user.getId(), savedContact.getPhoneNumber())) {
                            user.setNameInPhone(savedContact.getName());
                            toReturnUsers.add(user);
                           break;
                        }
//                        else {
//                            contactArrayList.add(savedContact);
//                        }
                    }
                }
                Collections.sort(toReturnUsers, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {
                        return user1.getNameToDisplay().compareToIgnoreCase(user2.getNameToDisplay());
                    }
                });
                taskListener.fetchMyUsersResult(toReturnUsers);
//                taskListener.fetchMyContactsResult(contactArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public interface FetchMyUsersTaskListener {
        void fetchMyUsersResult(ArrayList<User> myUsers);

        void fetchMyContactsResult(ArrayList<Contact> myContacts);
    }
}
