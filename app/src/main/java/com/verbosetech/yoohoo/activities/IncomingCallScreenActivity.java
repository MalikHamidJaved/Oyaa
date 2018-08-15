package com.verbosetech.yoohoo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.video.VideoCallListener;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.models.Attachment;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.services.AudioPlayer;
import com.verbosetech.yoohoo.services.SinchService;
import com.verbosetech.yoohoo.utils.FetchMyUsersTask;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.viewHolders.BaseMessageViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IncomingCallScreenActivity extends BaseActivity implements FetchMyUsersTask.FetchMyUsersTaskListener {

    static final String TAG = IncomingCallScreenActivity.class.getSimpleName();
    private String mCallId;
    private String mCallLocation;
    private AudioPlayer mAudioPlayer;
    //    private ImageView iv_incoming_user;
    private CircleImageView iv_incoming_user;
    private TextView name_calling;
    ArrayList<User> users = new ArrayList<User>();
    Call call;
    boolean video;
    Button answer;
    Button decline;
    private String callChild;


    @Override
    void userAdded(User valueUser) {

    }

    @Override
    void groupAdded(Group valueGroup) {

    }

    @Override
    public void userUpdated(User valueUser) {

    }

    @Override
    void groupUpdated(Group valueGroup) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call_screen);
        helper = new Helper(this);
        userMe = helper.getLoggedInUser();
        new FetchMyUsersTask(this, userMe.getId()).execute();


        answer = (Button) findViewById(R.id.answerButton);
        answer.setOnClickListener(mClickListener);
        decline = (Button) findViewById(R.id.declineButton);
        decline.setOnClickListener(mClickListener);
        video = getIntent().getBooleanExtra("video", true);

//        video = myPref.getPref(MyPref.AUDIO_VIDEO_CHECK, false);


        if (video) {
            answer.setBackground(getResources().getDrawable(R.drawable.ic_video_call_rounded));
        } else {
            answer.setBackground(getResources().getDrawable(R.drawable.ic_call_answer));
        }

        mAudioPlayer = new AudioPlayer(this);
        mAudioPlayer.playRingtone();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
        mCallLocation = getIntent().getStringExtra(SinchService.LOCATION);

        iv_incoming_user = findViewById(R.id.iv_incoming_user);

        name_calling = findViewById(R.id.name_calling);
    }

    @Override
    protected void onServiceConnected() {
        call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.addCallListener(new SinchCallListener());
            TextView remoteUser = (TextView) findViewById(R.id.remoteUser);
            remoteUser.setText(call.getRemoteUserId());
//            if (video) {
//                answer.setBackground(getResources().getDrawable(R.drawable.ic_video_call));
//            } else {
//                answer.setBackground(getResources().getDrawable(R.drawable.ic_call_answer));
//            }
//            if (video) {
//                answer.setBackground(getResources().getDrawable(R.drawable.ic_video_call));
//            } else {
//                answer.setBackground(getResources().getDrawable(R.drawable.ic_call_answer));
//            }
//            TextView remoteUserLocation = (TextView) findViewById(R.id.remoteUserLocation);
//            remoteUserLocation.setText("Calling from " + mCallLocation);
        } else {
            Log.e(TAG, "Started with invalid callId, aborting");
            finish();
        }
    }

    private void answerClicked() {
        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.answer();
            Intent intent = new Intent(this, CallScreenActivity.class);
            intent.putExtra(SinchService.CALL_ID, mCallId);
            intent.putExtra("name", userMe.getNameInPhone());
            if (video) {
                intent.putExtra("video", video);
            }
            startActivity(intent);
        } else {
            finish();
        }
    }

    private void declineClicked() {
        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }

    @Override
    public void fetchMyUsersResult(ArrayList<User> myUsers) {
        users.addAll(myUsers);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(call.getRemoteUserId())) {
                name_calling.setText(users.get(i).getNameInPhone());
                if (users.get(i).getImage() != null) {
                    Glide.with(getApplicationContext()).load(users.get(i).getImage()).into(iv_incoming_user);
                }
            }

        }
    }

    @Override
    public void fetchMyContactsResult(ArrayList<Contact> myContacts) {

    }

    private class SinchCallListener implements CallListener, VideoCallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended, cause: " + cause.toString());
            mAudioPlayer.stopRingtone();
            finish();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

        @Override
        public void onVideoTrackAdded(Call call) {

        }

        @Override
        public void onVideoTrackPaused(Call call) {

        }

        @Override
        public void onVideoTrackResumed(Call call) {

        }
    }

    private void sendMessage(String messageBody, @AttachmentTypes.AttachmentType int attachmentType, Attachment attachment) {
        //Create message object
        com.verbosetech.yoohoo.models.Call message = new com.verbosetech.yoohoo.models.Call();
//        message.setAttachmentType(attachmentType);
//        if (attachmentType != AttachmentTypes.NONE_TEXT)
//            message.setAttachment(attachment);
//        else
//            BaseMessageViewHolder.animate = true;
        message.setFrom(messageBody);
//        message.setId(System.currentTimeMillis());
        message.setImage(userMe.getId());
        message.setDuration(userMe.getName());
        message.setId(callsRef.child(callChild).push().getKey());

        //Add messages in chat child
        chatRef.child(callChild).child(message.getId()).setValue(message);

    }


    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.answerButton:
                    answerClicked();
                    break;
                case R.id.declineButton:
                    declineClicked();

                    break;
            }
        }
    };
}
