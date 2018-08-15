package com.verbosetech.yoohoo.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.services.SinchService;
import com.verbosetech.yoohoo.utils.Helper;

import io.realm.Realm;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by a_man on 01-01-2018.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment implements ServiceConnection {
    protected User userMe, user;
    protected Group group;
    protected Helper helper;
    protected Realm rChatDb;

    private SinchService.SinchServiceInterface mSinchServiceInterface;


    protected DatabaseReference usersRef, groupRef, chatRef, callsRef;

    //Group updates receiver(new or updated)
    private BroadcastReceiver groupReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(Helper.BROADCAST_GROUP)) {
                Group group = intent.getParcelableExtra("data");
                String what = intent.getStringExtra("what");
                switch (what) {
                    case "added":
                        groupAdded(group);
                        break;
                    case "changed":
                        groupUpdated(group);
                        break;
                }
            }
        }
    };

    //User updates receiver(new or updated)
    private BroadcastReceiver userReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(Helper.BROADCAST_USER)) {
                User user = intent.getParcelableExtra("data");
                String what = intent.getStringExtra("what");
                switch (what) {
                    case "added":
                        userAdded(user);
                        break;
                    case "changed":
                        userUpdated(user);
                        break;
                }
            }
        }
    };

    public abstract void userAdded(User valueUser);

    public abstract void groupAdded(Group valueGroup);

    public abstract void userUpdated(User valueUser);

    public abstract void groupUpdated(Group valueGroup);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helper = new Helper(getActivity());
        userMe = helper.getLoggedInUser();
        Realm.init(getActivity());
        rChatDb = Helper.getRealmInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();//get firebase instance
        usersRef = firebaseDatabase.getReference(Helper.REF_USER);//instantiate user's firebase reference
        groupRef = firebaseDatabase.getReference(Helper.REF_GROUP);//instantiate group's firebase reference
        chatRef = firebaseDatabase.getReference(Helper.REF_CHAT);//instantiate chat's firebase reference
        callsRef = firebaseDatabase.getReference(Helper.REF_CALLS);//instantiate call's firebase reference


        getActivity().bindService(new Intent(getActivity(), SinchService.class), this,
                BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(userReceiver, new IntentFilter(Helper.BROADCAST_USER));
        localBroadcastManager.registerReceiver(groupReceiver, new IntentFilter(Helper.BROADCAST_GROUP));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.unregisterReceiver(userReceiver);
        localBroadcastManager.unregisterReceiver(groupReceiver);
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
//        localBroadcastManager.registerReceiver(userReceiver, new IntentFilter(Helper.BROADCAST_USER));
//        localBroadcastManager.registerReceiver(groupReceiver, new IntentFilter(Helper.BROADCAST_GROUP));
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
//        localBroadcastManager.unregisterReceiver(userReceiver);
//        localBroadcastManager.unregisterReceiver(groupReceiver);
//    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = (SinchService.SinchServiceInterface) iBinder;
            onServiceConnected();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = null;
            onServiceDisconnected();
        }
    }

    protected void onServiceConnected() {
        // for subclasses
    }

    protected void onServiceDisconnected() {
        // for subclasses
    }

    protected SinchService.SinchServiceInterface getSinchServiceInterface() {
        return mSinchServiceInterface;
    }


}
