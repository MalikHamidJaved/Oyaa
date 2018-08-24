package com.verbosetech.yoohoo.activities;

import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.calling.CallState;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.models.Contact;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.services.AudioPlayer;
import com.verbosetech.yoohoo.services.SinchService;
import com.verbosetech.yoohoo.utils.FetchMyUsersTask;
import com.verbosetech.yoohoo.utils.Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallScreenActivity extends BaseActivity implements View.OnClickListener, FetchMyUsersTask.FetchMyUsersTaskListener {

    static final String TAG = CallScreenActivity.class.getSimpleName();

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;

    private String mCallId;
    private long mCallStart = 0;

    private boolean mAddedListener = false;
    private boolean mVideoViewsAdded = false;

    static final String CALL_START_TIME = "callStartTime";
    static final String ADDED_LISTENER = "addedListener";

    private TextView mCallDuration;
    private TextView mCallState;
    private TextView mCallerName;

    private Button btn_speaker, btn_mute, btn_rear_camera;
    AudioManager mAudioMgr;

    private TextView tv_name;
    boolean video;
    VideoController vc;

    Boolean aBoolean = true;


    ArrayList<User> users = new ArrayList<User>();

    //    ImageView iv_audio_call;
    CircleImageView iv_audio_call;

    Camera.CameraInfo currentCamInfo = new Camera.CameraInfo();
    int camBackId = Camera.CameraInfo.CAMERA_FACING_BACK;
    int camFrontId = Camera.CameraInfo.CAMERA_FACING_FRONT;


    private RelativeLayout rl_audio_call;
    private RelativeLayout rl_video_call;

    @Override
    public void fetchMyUsersResult(ArrayList<User> myUsers) {
        users.addAll(myUsers);
        Call call = getSinchServiceInterface().getCall(mCallId);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(call.getRemoteUserId())) {

                tv_name.setText(users.get(i).getNameInPhone());
                Glide.with(getApplicationContext()).load(users.get(i).getImage()).into(iv_audio_call);
                Log.d("Call activity", users.get(i).getNameInPhone());
            }

        }

    }

    @Override
    public void fetchMyContactsResult(ArrayList<Contact> myContacts) {

    }


    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            CallScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                    mCallDuration.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong(CALL_START_TIME, mCallStart);
        savedInstanceState.putBoolean(ADDED_LISTENER, mAddedListener);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mCallStart = savedInstanceState.getLong(CALL_START_TIME);
        mAddedListener = savedInstanceState.getBoolean(ADDED_LISTENER);
    }

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
        setContentView(R.layout.activity_call_screen);
        helper = new Helper(this);
        userMe = helper.getLoggedInUser();
        new FetchMyUsersTask(this, userMe.getId()).execute();

        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = (TextView) findViewById(R.id.callDuration);
        mCallerName = (TextView) findViewById(R.id.remoteUser);
        mCallState = (TextView) findViewById(R.id.callState);
        Button endCallButton = (Button) findViewById(R.id.hangupButton);

        btn_speaker = findViewById(R.id.btn_speaker);
        btn_speaker.setOnClickListener(this);
        btn_mute = findViewById(R.id.btn_mute);
        btn_mute.setOnClickListener(this);
        btn_rear_camera = findViewById(R.id.btn_rear_camera);
        btn_rear_camera.setOnClickListener(this);

        tv_name = findViewById(R.id.tv_name);


        iv_audio_call = findViewById(R.id.iv_audio_call);
        rl_video_call = findViewById(R.id.rl_video_call);

        mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        mAudioMgr.setMicrophoneMute(false);
        mAudioMgr.setSpeakerphoneOn(false);

//        rl_audio_call = findViewById(R.id.rl_audio_call);

//        rl_audio_call.setVisibility(View.GONE);
//        rl_video_call.setVisibility(View.GONE);

//
//        if (video) {
//            rl_video_call.setVisibility(View.VISIBLE);
//            rl_video_call.setVisibility(View.GONE);
//        } else {
//            rl_audio_call.setVisibility(View.VISIBLE);
//            rl_video_call.setVisibility(View.GONE);
//        }


        endCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endCallData(endCall());
            }
        });
//        mCallStart = System.currentTimeMillis();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
        tv_name.setText(getIntent().getStringExtra("name"));
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("image")).into(iv_audio_call);
        video = getIntent().getBooleanExtra("video", true);
        Log.d("video value:", "" + video);


        btn_speaker.setVisibility(View.GONE);
        btn_rear_camera.setVisibility(View.GONE);

//        if (video) {
//            rl_video_call.setVisibility(View.VISIBLE);
//            iv_audio_call.setVisibility(View.GONE);
//            mVideoViewsAdded = true;
//            mAddedListener = true;
//            addVideoViews();
//
//        }

//        if (!video) {
//            btn_speaker.setVisibility(View.VISIBLE);
//        }
//        else {
//            btn_speaker.setVisibility(View.GONE);
//        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_mute:
//                mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (!mAudioMgr.isMicrophoneMute()) {
                    mAudioMgr.setMicrophoneMute(true);
                    btn_mute.setBackground(getResources().getDrawable(R.drawable.ic_mute_off));
                } else {
                    mAudioMgr.setMicrophoneMute(false);
                    btn_mute.setBackground(getResources().getDrawable(R.drawable.ic_mute));
                }
                break;
            case R.id.btn_speaker:
//                mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (mAudioMgr.isSpeakerphoneOn()) {
                    mAudioMgr.setSpeakerphoneOn(false);
                    btn_speaker.setBackground(getResources().getDrawable(R.drawable.ic_speaker_off));
                } else {
                    mAudioMgr.setSpeakerphoneOn(true);
                    btn_speaker.setBackground(getResources().getDrawable(R.drawable.ic_speaker));
                }
                break;
            case R.id.btn_rear_camera:
                if (aBoolean) {
                    btn_rear_camera.setBackground(getResources().getDrawable(R.drawable.ic_front_camera_off));
                    aBoolean = false;
                } else {
                    btn_rear_camera.setBackground(getResources().getDrawable(R.drawable.ic_front_camera_on));
                    aBoolean = true;
                }
                vc.toggleCaptureDevicePosition();
                break;
        }
    }

    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
//            getSinchServiceInterface().Audio_Video(false);
            btn_speaker.setVisibility(View.VISIBLE);
            btn_rear_camera.setVisibility(View.GONE);
            call.addCallListener(new SinchCallListener());
            mCallerName.setText(call.getRemoteUserId());
            mCallState.setText(call.getState().toString());
            mAddedListener = true;
        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }

        if (video) {
//            getSinchServiceInterface().Audio_Video(true);
            btn_speaker.setVisibility(View.GONE);
            btn_rear_camera.setVisibility(View.GONE);
//            rl_video_call.setVisibility(View.VISIBLE);
//            iv_audio_call.setVisibility(View.VISIBLE);
//            rl_audio_call.setVisibility(View.GONE);
            updateUI();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mDurationTask.cancel();
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    private Call endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
        return call;



    }

    public void endCallData( Call call ){

        com.verbosetech.yoohoo.models.Call message = new com.verbosetech.yoohoo.models.Call();
        message.setBody(call.getCallId());
        message.setSenderId(userMe.getId());
        message.setSenderName(userMe.getName());
        message.setDuration(Integer.toString(call.getDetails().getDuration()) );
        message.setDelivered(btn_speaker.getVisibility() == View.VISIBLE);
        message.setDate(new Date().getTime());
        message.setRecipientId(call.getRemoteUserId());
        message.setRecipientName(tv_name.getText().toString());
        message.setId(Helper.getChatChild(userMe.getId(),call.getRemoteUserId()));

        //Add messages in chat child
        Date date = new Date();
        callsRef.child(Long.toString(date.getTime())).setValue(message);
    }

    private String formatTimespan(long timespan) {
        long totalSeconds = timespan / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration() {
        if (mCallStart > 0) {
            mCallDuration.setVisibility(View.GONE);
            mCallDuration.setText(formatTimespan(System.currentTimeMillis() - mCallStart));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (video) {
            mTimer = new Timer();
            mDurationTask = new UpdateCallDurationTask();
            mTimer.schedule(mDurationTask, 0, 500);
            updateUI();
        }
    }

    //method to update video feeds in the UI
    private void updateUI() {
        if (video) {
            if (getSinchServiceInterface() == null) {
                return; // early
            }

            Call call = getSinchServiceInterface().getCall(mCallId);
            if (call != null) {
                mCallerName.setText(call.getRemoteUserId());
                mCallState.setText(call.getState().toString());
                if (call.getState() == CallState.ESTABLISHED) {
                    //when the call is established, addVideoViews configures the video to  be shown
                    addVideoViews();
                }
            }

            btn_speaker.setVisibility(View.GONE);
//            btn_rear_camera.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (video) {
            mDurationTask.cancel();
            mTimer.cancel();
            removeVideoViews();
        }
    }

    //method which sets up the video feeds from the server to the UI of the activity
    private void addVideoViews() {
        if (mVideoViewsAdded || getSinchServiceInterface() == null) {
            return; //early
        }

//        final VideoController vc = getSinchServiceInterface().getVideoController();
        vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            RelativeLayout localView = (RelativeLayout) findViewById(R.id.localVideo);
            localView.addView(vc.getLocalView());

            localView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //this toggles the front camera to rear camera and vice versa
//                    vc.toggleCaptureDevicePosition();
                }
            });


            LinearLayout view = (LinearLayout) findViewById(R.id.remoteVideo);
            view.addView(vc.getRemoteView());
            iv_audio_call.setVisibility(View.GONE);
            rl_video_call.setVisibility(View.VISIBLE);
            btn_speaker.setVisibility(View.GONE);
            btn_rear_camera.setVisibility(View.VISIBLE);
            mVideoViewsAdded = true;
        }
    }

    //removes video feeds from the app once the call is terminated
    private void removeVideoViews() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            LinearLayout view = (LinearLayout) findViewById(R.id.remoteVideo);
            view.removeView(vc.getRemoteView());

            RelativeLayout localView = (RelativeLayout) findViewById(R.id.localVideo);
            localView.removeView(vc.getLocalView());
            mVideoViewsAdded = false;
        }
    }

    private class SinchCallListener implements CallListener, VideoCallListener {

        @Override
        public void onCallEnded(Call call) {
            if (!video) {
                CallEndCause cause = call.getDetails().getEndCause();
                Log.d(TAG, "Call ended. Reason: " + cause.toString());
                mAudioPlayer.stopProgressTone();
                setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
//                String endMsg = "Call ended: " + call.getDetails().toString();
//                Log.d("Call End:", endMsg);
//            Toast.makeText(CallScreenActivity.this, endMsg, Toast.LENGTH_LONG).show();
                endCall();
            } else {
                CallEndCause cause = call.getDetails().getEndCause();
                Log.d(TAG, "Call ended. Reason: " + cause.toString());
                mAudioPlayer.stopProgressTone();
                setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
//                String endMsg = "Call ended: " + call.getDetails().toString();
//                Toast.makeText(CallScreenActivity.this, endMsg, Toast.LENGTH_LONG).show();

                endCall();
            }
        }

        @Override
        public void onCallEstablished(Call call) {
            if (!video) {
                Log.d(TAG, "Call established");
                mAudioPlayer.stopProgressTone();
                mCallState.setText(call.getState().toString());
                setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                mCallStart = System.currentTimeMillis();
            } else {
                btn_speaker.setVisibility(View.VISIBLE);
                btn_rear_camera.setVisibility(View.GONE);
                mAudioPlayer.stopProgressTone();
                mCallState.setText(call.getState().toString());
                setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                AudioController audioController = getSinchServiceInterface().getAudioController();
                audioController.enableSpeaker();
                mCallStart = System.currentTimeMillis();
                Log.d(TAG, "Call offered video: " + call.getDetails().isVideoOffered());
            }
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();

        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

        @Override
        public void onVideoTrackAdded(Call call) {
            addVideoViews();

        }

        @Override
        public void onVideoTrackPaused(Call call) {

        }

        @Override
        public void onVideoTrackResumed(Call call) {


        }
    }


}
