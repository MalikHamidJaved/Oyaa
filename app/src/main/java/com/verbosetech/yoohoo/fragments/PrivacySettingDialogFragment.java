package com.verbosetech.yoohoo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.views.Dialog_privacy;

import static com.verbosetech.yoohoo.utils.Constant.Dialogcode.MESSAGE_READ;
import static com.verbosetech.yoohoo.utils.Constant.Dialogcode.ONLINE_STATUS;

public class PrivacySettingDialogFragment extends BaseFullDialogFragment implements Dialog_privacy.CallbackDialog {
    private RelativeLayout rl_online_status, rl_message_read;
    private TextView tv_online_status_value, tv_message_read_value;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgament_privacy_setting, container);
        rl_online_status = view.findViewById(R.id.rl_online_status);
        rl_message_read = view.findViewById(R.id.rl_message_read);
        tv_online_status_value = view.findViewById(R.id.tv_online_status_value);
        tv_message_read_value = view.findViewById(R.id.tv_message_read_value);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rl_online_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_privacy dialog_privacy = new Dialog_privacy(getContext(), ONLINE_STATUS);
                dialog_privacy.Set(PrivacySettingDialogFragment.this);
                if (tv_online_status_value.getText().toString().equals("Everyone")) {
                    dialog_privacy.setI(0);
                } else if (tv_online_status_value.getText().toString().equals("Friends")) {
                    dialog_privacy.setI(1);
                } else {
                    dialog_privacy.setI(2);
                }
                dialog_privacy.show();
            }
        });
        rl_message_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_privacy dialog_privacy = new Dialog_privacy(getContext(), MESSAGE_READ);
                dialog_privacy.Set(PrivacySettingDialogFragment.this);
                if (tv_message_read_value.getText().toString().equals("Everyone")) {
                    dialog_privacy.setI(0);
                } else if (tv_message_read_value.getText().toString().equals("Friends")) {
                    dialog_privacy.setI(1);
                } else {
                    dialog_privacy.setI(2);
                }
                dialog_privacy.show();
            }
        });
        return view;
    }


    @Override
    public void SaveButton(String SelectedValue, boolean Issave) {
        if (Issave) {
            tv_message_read_value.setText(SelectedValue);
        } else {
            tv_online_status_value.setText(SelectedValue);
        }
    }
}
