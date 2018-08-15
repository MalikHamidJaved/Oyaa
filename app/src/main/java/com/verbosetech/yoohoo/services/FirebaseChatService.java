package com.verbosetech.yoohoo.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.activities.ChatActivity;
import com.verbosetech.yoohoo.models.Attachment;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Chat;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.MyString;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;

import java.io.File;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmList;

public class FirebaseChatService extends Service {
    private static final String CHANNEL_ID_MAIN = "my_channel_01";
    private static final String CHANNEL_ID_GROUP = "my_channel_02";
    private static final String CHANNEL_ID_USER = "my_channel_03";
    private DatabaseReference usersRef, chatRef, groupsRef;
    private Helper helper;
    private String myId;
    private Realm rChatDb;
    private HashMap<String, User> userHashMap = new HashMap<>();
    private HashMap<String, Group> groupHashMap = new HashMap<>();
    private User userMe;

    public FirebaseChatService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_MAIN, "Yoohoo chat service", NotificationManager.IMPORTANCE_MIN);
            channel.setSound(null, null);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_GROUP)
                    .setSmallIcon(R.drawable.noti_icon)
                    .setContentTitle("Yoohoo")
                    .setContentText("Chat service running")
                    .build();
            startForeground(1, notification);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(uploadAndSendReceiver, new IntentFilter(Helper.UPLOAD_AND_SEND));
    }

    private BroadcastReceiver uploadAndSendReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(Helper.UPLOAD_AND_SEND)) {
                Attachment attachment = intent.getParcelableExtra("attachment");
                int type = intent.getIntExtra("attachment_type", -1);
                String attachmentFilePath = intent.getStringExtra("attachment_file_path");
                String attachmentChatChild = intent.getStringExtra("attachment_chat_child");
                String attachmentRecipientId = intent.getStringExtra("attachment_recipient_id");
                uploadAndSend(new File(attachmentFilePath), attachment, type, attachmentChatChild, attachmentRecipientId);
            }
        }
    };

    private void uploadAndSend(final File fileToUpload, final Attachment attachment, final int attachmentType, final String chatChild, final String recipientId) {
        if (!fileToUpload.exists())
            return;
        final String fileName = Uri.fromFile(fileToUpload).getLastPathSegment();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Helper.REF_STORAGE)
                .child(getString(R.string.app_name)).child(AttachmentTypes.getTypeName(attachmentType)).child(fileName);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //If file is already uploaded
                Attachment attachment1 = attachment;
                if (attachment1 == null) attachment1 = new Attachment();
                attachment1.setName(fileName);
                attachment1.setUrl(uri.toString());
                attachment1.setBytesCount(fileToUpload.length());
                sendMessage(null, attachmentType, attachment1, chatChild, recipientId);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //Elase upload and then send message
                UploadTask uploadTask = storageReference.putFile(Uri.fromFile(fileToUpload));
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //noinspection VisibleForTests
                        StorageMetadata storageMetadata = taskSnapshot.getMetadata();
                        //noinspection VisibleForTests
                        Attachment attachment1 = attachment;
                        if (attachment1 == null) attachment1 = new Attachment();
                        attachment1.setName(storageMetadata.getName());
                        attachment1.setUrl(storageMetadata.getDownloadUrl().toString());
                        attachment1.setBytesCount(storageMetadata.getSizeBytes());
                        sendMessage(null, attachmentType, attachment1, chatChild, recipientId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DatabaseException", e.getMessage());
                    }
                });
            }
        });
    }

    private void sendMessage(String messageBody, @AttachmentTypes.AttachmentType int attachmentType, Attachment attachment, String chatChild, String userOrGroupId) {
        //Create message object
        Message message = new Message();
        message.setAttachmentType(attachmentType);
        if (attachmentType != AttachmentTypes.NONE_TEXT)
            message.setAttachment(attachment);
        message.setBody(messageBody);
        message.setDate(System.currentTimeMillis());
        message.setSenderId(userMe.getId());
        message.setSenderName(userMe.getName());
        message.setSent(true);
        message.setDelivered(false);
        message.setRecipientId(userOrGroupId);
        message.setId(chatRef.child(chatChild).push().getKey());

        //Add messages in chat child
        chatRef.child(chatChild).child(message.getId()).setValue(message);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("CHECK", "in service start command");
        initVars();
        myId = initVars();
        rChatDb = Helper.getRealmInstance();
        registerUserUpdates();
        registerGroupUpdates();
        return super.onStartCommand(intent, flags, startId);
    }

    private String initVars() {
        helper = new Helper(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference(Helper.REF_USER);
        chatRef = firebaseDatabase.getReference(Helper.REF_CHAT);
        groupsRef = firebaseDatabase.getReference(Helper.REF_GROUP);
        Realm.init(this);

        userMe = helper.getLoggedInUser();
        if (userMe == null) {
            stopSelf();
            return "";
        } else {
            return userMe.getId();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(uploadAndSendReceiver);
        if (helper.isLoggedIn())
            sendBroadcast(RestartServiceReceiver.newIntent(this));
//            startService(new Intent(this, this.getClass()));
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        //onDestroy();
    }

    private void registerGroupUpdates() {
        groupsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    Group group = dataSnapshot.getValue(Group.class);
                    if (Group.validate(group) && group.getUserIds().contains(new MyString(myId))) {
                        if (!groupHashMap.containsKey(group.getId())) {
                            groupHashMap.put(group.getId(), group);
                            broadcastGroup("added", group);
                            checkAndNotify(group);
                            registerChatUpdates(true, group.getId());
                        }
                    }
                } catch (Exception ex) {
                    Log.e("GROUP", "invalid group");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                try {
                    Group group = dataSnapshot.getValue(Group.class);
                    if (Group.validate(group)) {
                        if (group.getUserIds().contains(new MyString(myId))) {
                            broadcastGroup("changed", group);
                            updateGroupInDb(group);
                        } else if (groupHashMap.containsKey(group.getId())) {
                            registerChatUpdates(false, group.getId());
                            groupHashMap.remove(group.getId());
                            broadcastGroup("changed", group);
                            updateGroupInDb(group);
                        }
                    }
                } catch (Exception ex) {
                    Log.e("GROUP", "invalid group");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkAndNotify(Group group) {
        Chat thisGroupChat = rChatDb.where(Chat.class)
                .equalTo("myId", myId)
                .equalTo("groupId", group.getId()).findFirst();
        if (thisGroupChat == null) {
            if (!group.getUserIds().get(0).equals(new MyString(myId)) && !helper.getSharedPreferenceHelper().getBooleanPreference(Helper.GROUP_NOTIFIED, false)) { //if i am not admin and have'nt notified yet
                notifyNewGroup(group);
                helper.getSharedPreferenceHelper().setBooleanPreference(Helper.GROUP_NOTIFIED, true);
            }
            rChatDb.beginTransaction();
            thisGroupChat = rChatDb.createObject(Chat.class);
            thisGroupChat.setGroup(rChatDb.copyToRealm(groupHashMap.get(group.getId())));
            thisGroupChat.setGroupId(group.getId());
            thisGroupChat.setMessages(new RealmList<Message>());
            thisGroupChat.setMyId(myId);
            thisGroupChat.setRead(false);
            long millis = System.currentTimeMillis();
            thisGroupChat.setLastMessage("Created on " + Helper.getDateTime(millis));
            thisGroupChat.setTimeUpdated(millis);
            rChatDb.commitTransaction();
        }
    }

    private void notifyNewGroup(Group group) {
        // Construct the Intent you want to end up at
        Intent chatActivity = ChatActivity.newIntent(this, null, group,true);
        // Construct the PendingIntent for your Notification
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // This uses android:parentActivityName and
        // android.support.PARENT_ACTIVITY meta-data by default
        stackBuilder.addNextIntentWithParentStack(chatActivity);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(99, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_GROUP, "Yoohoo new group notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID_GROUP);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSmallIcon(R.drawable.noti_icon)
                .setContentTitle("Group: " + group.getName())
                .setContentText("You have been added to new group called " + group.getName())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        int msgId = Integer.parseInt(group.getId().substring(group.getId().length() - 4, group.getId().length() - 1));
        notificationManager.notify(msgId, notificationBuilder.build());
    }

    private void registerUserUpdates() {
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    if (User.validate(user)) {
                        if (!userHashMap.containsKey(user.getId())) {
                            userHashMap.put(user.getId(), user);
                            broadcastUser("added", user);
                            registerChatUpdates(true, user.getId());
                        }
                    }
                } catch (Exception ex) {
                    Log.e("USER", "invalid user");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    if (User.validate(user)) {
                        broadcastUser("changed", user);
                        updateUserInDb(user);
                    }
                } catch (Exception ex) {
                    Log.e("USER", "invalid user");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUserInDb(User value) {
        //update in database
        if (!TextUtils.isEmpty(myId)) {
            Chat chat = rChatDb.where(Chat.class).equalTo("myId", myId).equalTo("userId", value.getId()).findFirst();
            if (chat != null) {
                rChatDb.beginTransaction();
                User updated = rChatDb.copyToRealm(value);
                updated.setNameInPhone(chat.getUser().getNameInPhone());
                chat.setUser(updated);
                rChatDb.commitTransaction();
            }
        }
    }

    private void updateGroupInDb(Group group) {
        if (!TextUtils.isEmpty(myId)) {
            Chat chat = rChatDb.where(Chat.class).equalTo("myId", myId).equalTo("groupId", group.getId()).findFirst();
            if (chat != null) {
                rChatDb.beginTransaction();
                chat.setGroup(rChatDb.copyToRealm(group));
                rChatDb.commitTransaction();
            }
        }
    }


    private void registerChatUpdates(boolean register, String id) {
        if (!TextUtils.isEmpty(myId) && !TextUtils.isEmpty(id)) {
            DatabaseReference idChatRef = chatRef.child(id.startsWith(Helper.GROUP_PREFIX) ? id : Helper.getChatChild(myId, id));
            if (register) {
                idChatRef.addChildEventListener(chatUpdateListener);
            } else {
                idChatRef.removeEventListener(chatUpdateListener);
            }
        }
    }

    private ChildEventListener chatUpdateListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Message message = dataSnapshot.getValue(Message.class);

            //temporary fix for unable to remove chatupdate listener
            if (message.getRecipientId().startsWith(Helper.GROUP_PREFIX) && !groupHashMap.containsKey(message.getRecipientId()))
                return;

            Message result = rChatDb.where(Message.class).equalTo("id", message.getId()).findFirst();
            if (result == null && !TextUtils.isEmpty(myId) && helper.isLoggedIn()) {
                saveMessage(message);
                if (!message.getRecipientId().startsWith(Helper.GROUP_PREFIX) && !message.getSenderId().equals(myId) && !message.isDelivered())
                    chatRef.child(dataSnapshot.getRef().getParent().getKey()).child(message.getId()).child("delivered").setValue(true);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Message message = dataSnapshot.getValue(Message.class);
            Message result = rChatDb.where(Message.class).equalTo("id", message.getId()).findFirst();
            if (result != null) {
                rChatDb.beginTransaction();
                result.setDelivered(message.isDelivered());
                rChatDb.commitTransaction();
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Message message = dataSnapshot.getValue(Message.class);

            Helper.deleteMessageFromRealm(rChatDb, message.getId());

            String userOrGroupId = myId.equals(message.getSenderId()) ? message.getRecipientId() : message.getSenderId();
            Chat chat = Helper.getChat(rChatDb, myId, userOrGroupId).findFirst();
            if (chat != null) {
                rChatDb.beginTransaction();
                RealmList<Message> realmList = chat.getMessages();
                if (realmList.size() == 0)
                    chat.deleteFromRealm();
                else {
                    chat.setLastMessage(realmList.get(realmList.size() - 1).getBody());
                    chat.setTimeUpdated(realmList.get(realmList.size() - 1).getDate());
                }
                rChatDb.commitTransaction();
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void saveMessage(Message message) {
        if (message.getAttachment() != null && !TextUtils.isEmpty(message.getAttachment().getUrl()) && !TextUtils.isEmpty(message.getAttachment().getName())) {
            String idToCompare = "loading" + message.getAttachment().getBytesCount() + message.getAttachment().getName();
            Helper.deleteMessageFromRealm(rChatDb, idToCompare);
        }

        String userOrGroupId = message.getRecipientId().startsWith(Helper.GROUP_PREFIX) ? message.getRecipientId() : myId.equals(message.getSenderId()) ? message.getRecipientId() : message.getSenderId();
        Chat chat = Helper.getChat(rChatDb, myId, userOrGroupId).findFirst();
        rChatDb.beginTransaction();
        if (chat == null) {
            chat = rChatDb.createObject(Chat.class);
            if (userOrGroupId.startsWith(Helper.GROUP_PREFIX)) {
                chat.setGroup(rChatDb.copyToRealm(groupHashMap.get(userOrGroupId)));
                chat.setGroupId(userOrGroupId);
                chat.setUser(null);
                chat.setUserId(null);
            } else {
                chat.setUser(rChatDb.copyToRealm(userHashMap.get(userOrGroupId)));
                chat.setUserId(userOrGroupId);
                chat.setGroup(null);
                chat.setGroupId(null);
            }
            chat.setMessages(new RealmList<Message>());
            chat.setLastMessage(message.getBody());
            chat.setMyId(myId);
            chat.setTimeUpdated(message.getDate());
        }

        if (!message.getSenderId().equals(myId))
            chat.setRead(false);
        chat.setTimeUpdated(message.getDate());
        chat.getMessages().add(message);
        chat.setLastMessage(message.getBody());
        rChatDb.commitTransaction();

        if (Helper.CHAT_NOTIFY && !message.getSenderId().equals(myId) && !helper.isUserMute(message.getSenderId()) && (Helper.CURRENT_CHAT_ID == null || !Helper.CURRENT_CHAT_ID.equals(userOrGroupId))) {
            // Construct the Intent you want to end up at
            Intent chatActivity = null;// = ChatActivity.newIntent(this, null,  ? chat.getGroup() : chat.getUser());
            if (userOrGroupId.startsWith(Helper.GROUP_PREFIX))
                chatActivity = ChatActivity.newIntent(this, null, chat.getGroup(),true);
            else
                chatActivity = ChatActivity.newIntent(this, null, chat.getUser(),false);
            // Construct the PendingIntent for your Notification
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            // This uses android:parentActivityName and
            // android.support.PARENT_ACTIVITY meta-data by default
            stackBuilder.addNextIntentWithParentStack(chatActivity);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(99, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder notificationBuilder = null;
            String channelId = userOrGroupId.startsWith(Helper.GROUP_PREFIX) ? CHANNEL_ID_GROUP : CHANNEL_ID_USER;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Yoohoo new message notification", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                notificationBuilder = new NotificationCompat.Builder(this, channelId);
            } else {
                notificationBuilder = new NotificationCompat.Builder(this);
            }

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSmallIcon(R.drawable.noti_icon)
                    .setContentTitle(userOrGroupId.startsWith(Helper.GROUP_PREFIX) ? chat.getGroup().getName() : chat.getUser().getName())
                    .setContentText(message.getBody())
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            int msgId = 0;
            try {
                msgId = Integer.parseInt(message.getSenderId());
            } catch (NumberFormatException ex) {
                msgId = Integer.parseInt(message.getSenderId().substring(message.getSenderId().length() / 2));
            }
            notificationManager.notify(msgId, notificationBuilder.build());
        }
    }

    private void broadcastUser(String what, User value) {
        Intent intent = new Intent(Helper.BROADCAST_USER);
        intent.putExtra("data", value);
        intent.putExtra("what", what);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void broadcastGroup(String what, Group value) {
        Intent intent = new Intent(Helper.BROADCAST_GROUP);
        intent.putExtra("data", value);
        intent.putExtra("what", what);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);
    }

}
