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

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.utils.Constant;

public class WorkingInProgressDialog extends Dialog implements View.OnClickListener {

    Context mContext;

    Button btn_cancel;





    public WorkingInProgressDialog(Context context) {
        super(context);
        this.mContext = context;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_working_in_progress);
        Window w = getWindow();
        w.setBackgroundDrawableResource(android.R.color.transparent);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;

        }
    }
}