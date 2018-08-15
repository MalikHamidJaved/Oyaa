package com.verbosetech.yoohoo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.models.Chat;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

/**
 * Created by a_man on 5/5/2017.
 */

public class Helper {
    private static final String USER = "USER";
    private static final String USER_MUTE = "USER_MUTE";
    private static final String SEND_OTP = "SEND_OTP";
    public static final String BROADCAST_USER = "com.verbosetech.oyapp.services.USER";
    public static final String BROADCAST_GROUP = "com.verbosetech.oyapp.services.GROUP";
    public static final String UPLOAD_AND_SEND = "com.verbosetech.oyapp.services.UPLOAD_N_SEND";
    public static final String REF_USER = "users";
    public static final String REF_CALLS = "calls";
    public static final String GROUP_CREATE = "group_create";
    public static final String GROUP_PREFIX = "group";
    public static final String GROUP_NOTIFIED = "group_notified";
    public static final String USER_NAME_CACHE = "usercachemap";
    public static final String REF_CHAT = "chats";
    public static final String REF_GROUP = "groups";
    public static String CURRENT_CHAT_ID;
    public static boolean CHAT_CAB = false, CHAT_NOTIFY = true;
    //    public static final String REF_STORAGE = "gs://b2bapp-7dd94.appspot.com";
    public static final String REF_STORAGE = "gs://oyapp-d429f.appspot.com";


    private SharedPreferenceHelper sharedPreferenceHelper;
    private Gson gson;
    private HashSet<String> muteUsersSet;
    private HashMap<String, User> myUsersNameInPhoneMap;

    public Helper(Context context) {
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
        gson = new Gson();
    }

    public static String getDateTime(Long milliseconds) {
        return new SimpleDateFormat("dd MMM kk:mm", Locale.getDefault()).format(new Date(milliseconds));
    }

    public static String getTime(Long milliseconds) {
        return new SimpleDateFormat("kk:mm", Locale.getDefault()).format(new Date(milliseconds));
    }

    public static boolean isImage(Context context, String url) {
        return getMimeType(context, url).startsWith("image");
    }

    public static String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static String getMimeType(Context context, String url) {
        String mimeType;
        Uri uri = Uri.parse(url);
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static String getChatChild(String userId, String myId) {
        //example: userId="9" and myId="5" -->> chat child = "5-9"
        String[] temp = {userId, myId};
        Arrays.sort(temp);
        return temp[0] + "-" + temp[1];
    }

    public User getLoggedInUser() {
        String savedUserPref = sharedPreferenceHelper.getStringPreference(USER);
        if (savedUserPref != null)
            return gson.fromJson(savedUserPref, new TypeToken<User>() {
            }.getType());
        return null;
    }

    public void setLoggedInUser(User user) {
        sharedPreferenceHelper.setStringPreference(USER, gson.toJson(user, new TypeToken<User>() {
        }.getType()));
    }

    public void logout() {
        sharedPreferenceHelper.clearPreference(SEND_OTP);
        sharedPreferenceHelper.clearPreference(USER);
    }

    public void setPhoneNumberForVerification(String phone) {
        sharedPreferenceHelper.setStringPreference(SEND_OTP, phone);
    }

    public String getPhoneNumberForVerification() {
        return sharedPreferenceHelper.getStringPreference(SEND_OTP);
    }

    public void clearPhoneNumberForVerification() {
        sharedPreferenceHelper.clearPreference(SEND_OTP);
    }

    public boolean isLoggedIn() {
        return sharedPreferenceHelper.getStringPreference(USER) != null;
    }

    public void setUserMute(String userId, boolean mute) {
        if (muteUsersSet == null) {
            String muteUsersPref = sharedPreferenceHelper.getStringPreference(USER_MUTE);
            if (muteUsersPref != null) {
                muteUsersSet = gson.fromJson(muteUsersPref, new TypeToken<HashSet<String>>() {
                }.getType());
            } else {
                muteUsersSet = new HashSet<>();
            }
        }

        if (mute)
            muteUsersSet.add(userId);
        else
            muteUsersSet.remove(userId);

        sharedPreferenceHelper.setStringPreference(USER_MUTE, gson.toJson(muteUsersSet, new TypeToken<HashSet<String>>() {
        }.getType()));
    }

    public boolean isUserMute(String userId) {
        String muteUsersPref = sharedPreferenceHelper.getStringPreference(USER_MUTE);
        if (muteUsersPref != null) {
            HashSet<String> muteUsersSet = gson.fromJson(muteUsersPref, new TypeToken<HashSet<String>>() {
            }.getType());
            return muteUsersSet.contains(userId);
        } else {
            return false;
        }
    }

    public static void loadUrl(Context context, String url) {
        Uri uri = Uri.parse(url);
// create an intent builder
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
// Begin customizing
// set toolbar colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        intentBuilder.addDefaultShareMenuItem();
        intentBuilder.enableUrlBarHiding();
// build custom tabs intent
        CustomTabsIntent customTabsIntent = intentBuilder.build();
// launch the url
        customTabsIntent.launchUrl(context, uri);
    }

    public static boolean contactMatches(String userPhone, String phoneNumber) {
        if (userPhone.length() < 8 || phoneNumber.length() < 8)
            return false;
//        String reverseUserNumber = new StringBuffer(userPhone).reverse().toString().substring(0, 7);
//        String reversePhoneNumber = new StringBuffer(phoneNumber).reverse().toString().substring(0, 7);
//        return reversePhoneNumber.equals(reverseUserNumber);
        return userPhone.substring(userPhone.length() - 7, userPhone.length()).equals(phoneNumber.substring(phoneNumber.length() - 7, phoneNumber.length()));

    }


    public static Realm getRealmInstance() {
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm realm = Realm.getInstance(config);
        return realm;
    }

    public static RealmQuery<Chat> getChat(Realm rChatDb, String myId, String userId) {
        return rChatDb.where(Chat.class).equalTo("myId", myId).equalTo(userId.startsWith(GROUP_PREFIX) ? "groupId" : "userId", userId);
    }

    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return sharedPreferenceHelper;
    }

    public HashMap<String, User> getCacheMyUsers() {
        if (this.myUsersNameInPhoneMap != null) {
            return this.myUsersNameInPhoneMap;
        } else {
            String inPrefs = sharedPreferenceHelper.getStringPreference(USER_NAME_CACHE);
            if (inPrefs != null) {
                this.myUsersNameInPhoneMap = new Gson().fromJson(inPrefs, new TypeToken<HashMap<String, User>>() {
                }.getType());
                return this.myUsersNameInPhoneMap;
            } else {
                return null;
            }
        }
    }

    public static List<String> mediaPermissions(Context context) {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        List<String> missingPermissions = new ArrayList<>();
        for (String permission : PERMISSIONS_STORAGE) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        return missingPermissions;
    }

    public void setCacheMyUsers(ArrayList<User> myUsers) {
        if (this.myUsersNameInPhoneMap == null) {
            this.myUsersNameInPhoneMap = new HashMap<>();
        }
        this.myUsersNameInPhoneMap.clear();
        User me = getLoggedInUser();
        this.myUsersNameInPhoneMap.put(me.getId(), me);
        for (User user : myUsers) {
            this.myUsersNameInPhoneMap.put(user.getId(), user);
        }
        sharedPreferenceHelper.setStringPreference(USER_NAME_CACHE, new Gson().toJson(this.myUsersNameInPhoneMap, new TypeToken<HashMap<String, User>>() {
        }.getType()));
    }

    public static void openShareIntent(Context context, @Nullable View itemview, String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (itemview != null) {
            try {
                Uri imageUri = getImageUri(context, itemview, "postBitmap.jpeg");
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (IOException e) {
                intent.setType("text/plain");
                e.printStackTrace();
            }
        } else {
            intent.setType("text/plain");
        }
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(intent, "Share Via:"));
    }

    private static Uri getImageUri(Context context, View view, String fileName) throws IOException {
        Bitmap bitmap = loadBitmapFromView(view);
        File pictureFile = new File(context.getExternalCacheDir(), fileName);
        FileOutputStream fos = new FileOutputStream(pictureFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        fos.close();
        return Uri.parse("file://" + pictureFile.getAbsolutePath());
    }

    private static Bitmap loadBitmapFromView(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            v.setDrawingCacheEnabled(true);
            return v.getDrawingCache();
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    public static void openPlayStore(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void openSupportMail(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "shoxrux.tj@bk.ru", null));
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static int getDisplayWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static void closeKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void deleteMessageFromRealm(Realm rChatDb, String msgId) {
        Message result = rChatDb.where(Message.class).equalTo("id", msgId).findFirst();
        if (result != null) {
            rChatDb.beginTransaction();
            result.deleteFromRealm();
            rChatDb.commitTransaction();
        }
    }

    public static String timeFormater(float time) {
        Long secs = (long) (time / 1000);
        Long mins = (long) ((time / 1000) / 60);
        Long hrs = (long) (((time / 1000) / 60) / 60); /* Convert the seconds to String * and format to ensure it has * a leading zero when required */
        secs = secs % 60;
        String seconds = String.valueOf(secs);
        if (secs == 0) {
            seconds = "00";
        }
        if (secs < 10 && secs > 0) {
            seconds = "0" + seconds;
        } /* Convert the minutes to String and format the String */
        mins = mins % 60;
        String minutes = String.valueOf(mins);
        if (mins == 0) {
            minutes = "00";
        }
        if (mins < 10 && mins > 0) {
            minutes = "0" + minutes;
        } /* Convert the hours to String and format the String */
        String hours = String.valueOf(hrs);
        if (hrs == 0) {
            hours = "00";
        }
        if (hrs < 10 && hrs > 0) {
            hours = "0" + hours;
        }

        return hours + ":" + minutes + ":" + seconds;
//        String milliseconds = String.valueOf((long) time);
//        if (milliseconds.length() == 2) {
//            milliseconds = "0" + milliseconds;
//        }
//        if (milliseconds.length() <= 1) {
//            milliseconds = "00";
//        }
//        milliseconds = milliseconds.substring(milliseconds.length() - 3, milliseconds.length() - 2); /* Setting the timer text to the elapsed time */
    }
}