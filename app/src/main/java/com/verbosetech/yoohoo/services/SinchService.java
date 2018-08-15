package com.verbosetech.yoohoo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.video.VideoController;
import com.verbosetech.yoohoo.activities.IncomingCallScreenActivity;
import com.verbosetech.yoohoo.models.User;

import java.util.ArrayList;
import java.util.Map;

public class SinchService extends Service {

    private static final String APP_KEY = "d81c2dff-dbfe-4827-8c9f-0c8035618b29";
    private static final String APP_SECRET = "3YSr+ynkdUCl06wMIxIO2A==";
    private static final String ENVIRONMENT = "clientapi.sinch.com";

    public static final String LOCATION = "LOCATION";
    public static final String CALL_ID = "CALL_ID";
    static final String TAG = SinchService.class.getSimpleName();

    private SinchServiceInterface mSinchServiceInterface = new SinchServiceInterface();
    private SinchClient mSinchClient;
    private String mUserId;

    private StartFailedListener mListener;


    private ArrayList<User> myUsers1 = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (mSinchClient != null && mSinchClient.isStarted()) {
            mSinchClient.terminate();
        }
        super.onDestroy();
    }

    private void start(String userName) {
        if (mSinchClient == null) {
            mUserId = userName;
            mSinchClient = Sinch.getSinchClientBuilder().context(getApplicationContext()).userId(userName)
                    .applicationKey(APP_KEY)
                    .applicationSecret(APP_SECRET)
                    .environmentHost(ENVIRONMENT).build();

            mSinchClient.setSupportCalling(true);
            mSinchClient.startListeningOnActiveConnection();
            mSinchClient.setSupportActiveConnectionInBackground(true);

            mSinchClient.addSinchClientListener(new MySinchClientListener());
            mSinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
            mSinchClient.start();
        }
    }

    private void stop() {
        if (mSinchClient != null) {
            mSinchClient.terminate();
            mSinchClient = null;
        }
    }

    private boolean isStarted() {
        return (mSinchClient != null && mSinchClient.isStarted());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSinchServiceInterface;
    }


    public class SinchServiceInterface extends Binder {

        public Call callPhoneNumber(String phoneNumber) {
            return mSinchClient.getCallClient().callPhoneNumber(phoneNumber);
        }

        public Call callUser(String userId) {
            return mSinchClient.getCallClient().callUser(userId);
        }

        public Call callUser(String userId, Map<String, String> headers) {
            return mSinchClient.getCallClient().callUser(userId, headers);
        }

        public Call callUserVideo(String userId) {
            return mSinchClient.getCallClient().callUserVideo(userId);

        }

        public String getUserName() {
            return mUserId;
        }

        public boolean isStarted() {
            return SinchService.this.isStarted();
        }

        public void startClient(String userName) {
            start(userName);
        }

        public void stopClient() {
            stop();
        }

        public void setStartListener(StartFailedListener listener) {
            mListener = listener;
        }

        public Call getCall(String callId) {
            return mSinchClient.getCallClient().getCall(callId);
        }

        public VideoController getVideoController() {
            if (!isStarted()) {
                return null;
            }
            return mSinchClient.getVideoController();
        }

        public AudioController getAudioController() {
            if (!isStarted()) {
                return null;
            }
            return mSinchClient.getAudioController();
        }
    }

    public interface StartFailedListener {
        void onStartFailed(SinchError error);

        void onStarted();
    }

    private class MySinchClientListener implements SinchClientListener {

        @Override
        public void onClientFailed(SinchClient client, SinchError error) {
            if (mListener != null) {
                mListener.onStartFailed(error);
            }
            mSinchClient.terminate();
            mSinchClient = null;
        }

        @Override
        public void onClientStarted(SinchClient client) {
            Log.d(TAG, "SinchClient started");
            if (mListener != null) {
                mListener.onStarted();
            }
        }

        @Override
        public void onClientStopped(SinchClient client) {
            Log.d(TAG, "SinchClient stopped");
        }

        @Override
        public void onLogMessage(int level, String area, String message) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(area, message);
                    break;
                case Log.ERROR:
                    Log.e(area, message);
                    break;
                case Log.INFO:
                    Log.i(area, message);
                    break;
                case Log.VERBOSE:
                    Log.v(area, message);
                    break;
                case Log.WARN:
                    Log.w(area, message);
                    break;
            }
        }

        @Override
        public void onRegistrationCredentialsRequired(SinchClient client,
                                                      ClientRegistration clientRegistration) {
        }
    }

    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {

            if (call.getDetails().isVideoOffered()) {
                Log.d(TAG, "Incoming call");

                Intent intent = new Intent(SinchService.this, IncomingCallScreenActivity.class);
                intent.putExtra(CALL_ID, call.getCallId());
                intent.putExtra(LOCATION, call.getHeaders().get("location"));
                intent.putExtra("video", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (video) {
//                intent.getBooleanExtra("video", video);
//            }
                SinchService.this.startActivity(intent);
            } else {
                Log.d(TAG, "Incoming call");

                Intent intent = new Intent(SinchService.this, IncomingCallScreenActivity.class);
                intent.putExtra(CALL_ID, call.getCallId());
                intent.putExtra(LOCATION, call.getHeaders().get("location"));
                intent.putExtra("video", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (video) {
//                intent.getBooleanExtra("video", video);
//            }
                SinchService.this.startActivity(intent);
            }

        }
    }
}