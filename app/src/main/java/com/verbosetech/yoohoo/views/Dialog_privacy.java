package com.verbosetech.yoohoo.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.utils.Constant;


/**
 * Created by Ravi on 3/29/2018.
 */

public class Dialog_privacy extends Dialog implements View.OnClickListener {

    Context mContext;
    TextView tv_tag;
    RadioGroup rg_option;
    RadioButton rb_everyone, rb_friends, rb_none;
    Button btn_cancel, btn_ok;
    Constant.Dialogcode dialogcode;
    CallbackDialog callbackDialog;
    boolean is_read;
    String selectedValue;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    int i;

    public interface CallbackDialog {
        void SaveButton(String SelectedValue, boolean Issave);
    }

    public <T> void Set(T fragment) {
        callbackDialog = (CallbackDialog) fragment;
    }

    public Dialog_privacy(Context context, Constant.Dialogcode dialogcode) {
        super(context);
        this.mContext = context;
        this.dialogcode = dialogcode;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_privacy);
        Window w = getWindow();
        w.setBackgroundDrawableResource(android.R.color.transparent);
        tv_tag = findViewById(R.id.tv_tag);
        rb_everyone = findViewById(R.id.rb_everyone);
        rb_friends = findViewById(R.id.rb_friends);
        rb_none = findViewById(R.id.rb_none);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);

        switch (dialogcode) {
            case ONLINE_STATUS:
                tv_tag.setText("Online Status");
                is_read = false;
                break;
            case MESSAGE_READ:
                tv_tag.setText("Message Read");
                is_read = true;
                break;
        }

        if (getI() == 0) {
            rb_everyone.setChecked(true);
        } else if (getI() == 1) {
            rb_friends.setChecked(true);
        } else {
            rb_none.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                if (rb_everyone.isChecked()) {
                    selectedValue = "Everyone";
                    rb_everyone.setChecked(true);
                } else if (rb_none.isChecked()) {
                    selectedValue = "None";
                    rb_none.setChecked(true);
                } else if (rb_friends.isChecked()) {
                    selectedValue = "Friends";
                    rb_friends.setChecked(true);
                }
                callbackDialog.SaveButton(selectedValue, is_read);
                dismiss();
                break;
        }
    }
}