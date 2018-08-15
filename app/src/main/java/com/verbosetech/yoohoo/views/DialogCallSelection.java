package com.verbosetech.yoohoo.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.verbosetech.yoohoo.R;


public class DialogCallSelection extends Dialog implements View.OnClickListener {

    Context mContext;
    Activity activity;
    ImageView ic_voice_call, ic_video_call;
    TextView tv_voice_call, tv_video_call;


    CallbackDialog callbackDialog;

    public interface CallbackDialog {
        void SaveButton(boolean Issave);
    }

    public <T> void Set(T Context) {
        callbackDialog = (CallbackDialog) Context;
    }

    public DialogCallSelection(Activity context) {
        super(context);
        this.activity = context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_call_selection);
        Window w = getWindow();
        w.setBackgroundDrawableResource(android.R.color.transparent);
        ic_voice_call = findViewById(R.id.ic_voice_call);
        ic_voice_call.setOnClickListener(this);
        ic_video_call = findViewById(R.id.ic_video_call);
        ic_video_call.setOnClickListener(this);
        tv_voice_call = findViewById(R.id.tv_voice_call);
        tv_voice_call.setOnClickListener(this);
        tv_video_call = findViewById(R.id.tv_video_call);
        tv_video_call.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_voice_call:
            case R.id.tv_voice_call:
                callbackDialog.SaveButton(true);
                break;
            case R.id.ic_video_call:
            case R.id.tv_video_call:
                callbackDialog.SaveButton(false);
                break;
        }

    }
}
