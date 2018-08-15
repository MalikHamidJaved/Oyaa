package com.verbosetech.yoohoo.viewHolders;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.adapters.ContactsAdapter;
import com.verbosetech.yoohoo.interfaces.OnMessageItemClick;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Contact_Model;
import com.verbosetech.yoohoo.models.DownloadFileEvent;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.MyFileProvider;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import ezvcard.Ezvcard;
import ezvcard.VCard;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageAttachmentContactViewHolder extends BaseMessageViewHolder {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.container)
    LinearLayout ll;
    private VCard vcard;



    private Dialog myDialog1;
    private ImageView contactImage;
    private TextView contactName, addToContactText;
    private RecyclerView contactPhones, contactEmails;

    private Message message;

    public MessageAttachmentContactViewHolder(View itemView, OnMessageItemClick itemClickListener) {
        super(itemView, itemClickListener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put under some check
                if (!Helper.CHAT_CAB)
                    dialogVCardDetail();
                onItemClick(true);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClick(false);
                return true;
            }
        });
    }

    @Override
    public void setData(Message message, int position, HashMap<String, User> myUsers) {
        super.setData(message, position, myUsers);
        this.message = message;
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, message.isSelected() ? R.color.colorPrimary : R.color.colorBgLight));
        ll.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorPrimary) : isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));
        vcard = Ezvcard.parse(message.getAttachment().getData()).first();
        text.setText(vcard.getFormattedName() != null ? vcard.getFormattedName().getValue() : "Contact");
    }

    private void dialogVCardDetail() {
        if (myDialog1 == null) {
            myDialog1 = new Dialog(context, R.style.DialogBox);
            myDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            myDialog1.setCancelable(true);
            myDialog1.setContentView(R.layout.dialog_v_card_detail);

            contactImage = (ImageView) myDialog1.findViewById(R.id.contactImage);
            contactName = (TextView) myDialog1.findViewById(R.id.contactName);
            addToContactText = (TextView) myDialog1.findViewById(R.id.addToContactText);
            contactPhones = (RecyclerView) myDialog1.findViewById(R.id.recyclerPhone);
            contactEmails = (RecyclerView) myDialog1.findViewById(R.id.recyclerEmail);

            contactPhones.setLayoutManager(new LinearLayoutManager(context));
            contactEmails.setLayoutManager(new LinearLayoutManager(context));

            myDialog1.findViewById(R.id.contactAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (message != null) {
                        File file = new File(Environment.getExternalStorageDirectory() + "/"
                                +
                                context.getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(message.getAttachmentType()) + (isMine() ? "/.sent/" : "")
                                , message.getAttachment().getName());
                        if (file.exists()) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri uri = MyFileProvider.getUriForFile(context,
                                    context.getString(R.string.authority),
                                    file);
                            intent.setDataAndType(uri, Helper.getMimeType(context, uri)); //storage path is path of your vcf file and vFile is name of that file.
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            context.startActivity(intent);
                        } else if (!isMine())
                            EventBus.getDefault().post(new DownloadFileEvent(message.getAttachmentType(), message.getAttachment(), getAdapterPosition()));
                        else
                            Toast.makeText(context, "File unavailable", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(file), "text/x-vcard"); //storage path is path of your vcf file and vFile is name of that file.
//                        context.startActivity(intent);
                    }
                }
            });

            myDialog1.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog1.dismiss();
                }
            });
        }



        if (vcard.getPhotos().size() > 0)
            Glide.with(context).load(vcard.getPhotos().get(0).getData()).apply(new RequestOptions().dontAnimate()).into(contactImage);

        contactName.setText(vcard.getFormattedName().getValue());

        contactPhones.setAdapter(new ContactsAdapter(context, vcard.getTelephoneNumbers(), null));
        contactEmails.setAdapter(new ContactsAdapter(context, null, vcard.getEmails()));

        myDialog1.show();
    }

//    private ArrayList<Contact_Model> readContacts () {
//        ArrayList<Contact_Model> contactList = new ArrayList<Contact_Model>();
//
//        Uri uri = ContactsContract.Contacts.CONTENT_URI; // Contact URI
//        Cursor contactsCursor = context.getContentResolver().query(uri, null, null,
//                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC "); // Return
//        // all
//        // contacts
//        // name
//        // containing
//        // in
//        // URI
//        // in
//        // ascending
//        // order
//        // Move cursor at starting
//        if (contactsCursor.moveToFirst()) {
//            do {
//                long contctId = contactsCursor.getLong(contactsCursor
//                        .getColumnIndex("_ID")); // Get contact ID
//                Uri dataUri = ContactsContract.Data.CONTENT_URI; // URI to get
//                // data of
//                // contacts
//                Cursor dataCursor = context.getContentResolver().query(dataUri, null,
//                        ContactsContract.Data.CONTACT_ID + " = " + contctId,
//                        null, null);// Retrun data cusror represntative to
//                // contact ID
//
//                // Strings to get all details
//                String displayName = "";
//                String nickName = "";
//                String homePhone = "";
//                String mobilePhone = "";
//                String workPhone = "";
//                String photoPath = "" + R.drawable.ic_add_circle; // Photo path
//                byte[] photoByte = null;// Byte to get photo since it will come
//                // in BLOB
//                String homeEmail = "";
//                String workEmail = "";
//                String companyName = "";
//                String title = "";
//
//                // This strings stores all contact numbers, email and other
//                // details like nick name, company etc.
//                String contactNumbers = "";
//                String contactEmailAddresses = "";
//                String contactOtherDetails = "";
//
//                // Now start the cusrsor
//                if (dataCursor.moveToFirst()) {
//                    displayName = dataCursor
//                            .getString(dataCursor
//                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));// get
//                    // the
//                    // contact
//                    // name
//                    do {
//                        if (dataCursor
//                                .getString(
//                                        dataCursor.getColumnIndex("mimetype"))
//                                .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)) {
//                            nickName = dataCursor.getString(dataCursor
//                                    .getColumnIndex("data1")); // Get Nick Name
//                            contactOtherDetails += "NickName : " + nickName
//                                    + "\n";// Add the nick name to string
//
//                        }
//
//                        if (dataCursor
//                                .getString(
//                                        dataCursor.getColumnIndex("mimetype"))
//                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
//
//                            // In this get All contact numbers like home,
//                            // mobile, work, etc and add them to numbers string
//                            switch (dataCursor.getInt(dataCursor
//                                    .getColumnIndex("data2"))) {
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
//                                    homePhone = dataCursor.getString(dataCursor
//                                            .getColumnIndex("data1"));
//                                    contactNumbers += "Home Phone : " + homePhone
//                                            + "\n";
//                                    break;
//
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
//                                    workPhone = dataCursor.getString(dataCursor
//                                            .getColumnIndex("data1"));
//                                    contactNumbers += "Work Phone : " + workPhone
//                                            + "\n";
//                                    break;
//
//                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
//                                    mobilePhone = dataCursor.getString(dataCursor
//                                            .getColumnIndex("data1"));
//                                    contactNumbers += "Mobile Phone : "
//                                            + mobilePhone + "\n";
//                                    break;
//
//                            }
//                        }
//                        if (dataCursor
//                                .getString(
//                                        dataCursor.getColumnIndex("mimetype"))
//                                .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
//
//                            // In this get all Emails like home, work etc and
//                            // add them to email string
//                            switch (dataCursor.getInt(dataCursor
//                                    .getColumnIndex("data2"))) {
//                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
//                                    homeEmail = dataCursor.getString(dataCursor
//                                            .getColumnIndex("data1"));
//                                    contactEmailAddresses += "Home Email : "
//                                            + homeEmail + "\n";
//                                    break;
//                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
//                                    workEmail = dataCursor.getString(dataCursor
//                                            .getColumnIndex("data1"));
//                                    contactEmailAddresses += "Work Email : "
//                                            + workEmail + "\n";
//                                    break;
//
//                            }
//                        }
//
//                        if (dataCursor
//                                .getString(
//                                        dataCursor.getColumnIndex("mimetype"))
//                                .equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
//                            companyName = dataCursor.getString(dataCursor
//                                    .getColumnIndex("data1"));// get company
//                            // name
//                            contactOtherDetails += "Coompany Name : "
//                                    + companyName + "\n";
//                            title = dataCursor.getString(dataCursor
//                                    .getColumnIndex("data4"));// get Company
//                            // title
//                            contactOtherDetails += "Title : " + title + "\n";
//
//                        }
//
//                        if (dataCursor
//                                .getString(
//                                        dataCursor.getColumnIndex("mimetype"))
//                                .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
//                            photoByte = dataCursor.getBlob(dataCursor
//                                    .getColumnIndex("data15")); // get photo in
//                            // byte
//
//                            if (photoByte != null) {
//
//                                // Now make a cache folder in file manager to
//                                // make cache of contacts images and save them
//                                // in .png
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(
//                                        photoByte, 0, photoByte.length);
//                                File cacheDirectory = context
//                                        .getCacheDir();
//                                File tmp = new File(cacheDirectory.getPath()
//                                        + "/_androhub" + contctId + ".png");
//                                try {
//                                    FileOutputStream fileOutputStream = new FileOutputStream(
//                                            tmp);
//                                    bitmap.compress(Bitmap.CompressFormat.PNG,
//                                            100, fileOutputStream);
//                                    fileOutputStream.flush();
//                                    fileOutputStream.close();
//                                } catch (Exception e) {
//                                    // TODO: handle exception
//                                    e.printStackTrace();
//                                }
//                                photoPath = tmp.getPath();// finally get the
//                                // saved path of
//                                // image
//                            }
//
//                        }
//
//                    } while (dataCursor.moveToNext()); // Now move to next
//                    // cursor
//
//                    contactList.add(new Contact_Model(Long.toString(contctId),
//                            displayName, contactNumbers, contactEmailAddresses,
//                            photoPath, contactOtherDetails));// Finally add
//                    // items to
//                    // array list
//                }
//
//            } while (contactsCursor.moveToNext());
//        }
//        return contactList;
//    }

}
