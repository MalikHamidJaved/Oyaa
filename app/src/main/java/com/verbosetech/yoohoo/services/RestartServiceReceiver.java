package com.verbosetech.yoohoo.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.verbosetech.yoohoo.activities.ChatActivity;

/**
 * Created by a_man on 13-11-2017.
 */

public class RestartServiceReceiver extends BroadcastReceiver {
    public static final String INTENT_NAME = "RestartService";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            context.startForegroundService(new Intent(context, FirebaseChatService.class));
        } else {
            context.startService(new Intent(context, FirebaseChatService.class));
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(RestartServiceReceiver.INTENT_NAME);
        intent.setClass(context, RestartServiceReceiver.class);
        return intent;
    }
}
