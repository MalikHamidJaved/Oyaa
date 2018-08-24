package com.verbosetech.yoohoo.viewHolders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.activities.MainActivity;
import com.verbosetech.yoohoo.interfaces.OnCallHistoryItemCleckListener;
import com.verbosetech.yoohoo.models.Call;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CallHistoryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.rl_one)
    RelativeLayout contAppointment;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time_date)
    TextView tvChecklist;
    @BindView(R.id.userImage)
    CircleImageView ivProfile;
    @BindView(R.id.iv_answer)
    ImageView ivAnswer;
    @BindView(R.id.iv_call_type)
    ImageView ivCallType;

    private boolean typeBasedItems;
    private int idx;
    private WeakReference<OnCallHistoryItemCleckListener> onAppointmentItemClickListener;

    Call object;
    Context context;

    public CallHistoryItemViewHolder(View view,  OnCallHistoryItemCleckListener onChecklistItemCleckListener) {

        super(view);
        ButterKnife.bind(this, view);

        contAppointment.setOnClickListener(this);
        context = view.getContext();
        this.typeBasedItems = typeBasedItems;
        this.onAppointmentItemClickListener = new WeakReference<>(onChecklistItemCleckListener);
        ivCallType.setOnClickListener(this);
    }

    public void setData(Call checklist, int idx) {

        object = checklist;
        this.idx = idx;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.yoohoo_placeholder);

        Glide.with(context).load(object.getImage()).apply(requestOptions).into(ivProfile);


        if(object.getSenderId().equals(((MainActivity)context).getUserMe().getId())){
            tvName.setText(object.getRecipientName());
            ivAnswer.setRotation(90);
        }else {
            tvName.setText(object.getSenderName());
        }

        if(object.getDuration()!=null)
        if(object.getDuration() .equals("0") ){
            ivAnswer.setColorFilter(Color.RED);
        }else {
            ivAnswer.setColorFilter(Color.GREEN);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        String date = df.format(object.getDate());

        tvChecklist.setText(date);

        if(object.isDelivered()){
            ivCallType.setImageResource(R.drawable.ic_app_call_header_blue);
        }else {
            ivCallType.setImageResource(R.drawable.ic_video_call_blue);
        }


    }

    @Override
    public void onClick(View view) {

        if (onAppointmentItemClickListener == null || onAppointmentItemClickListener.get() == null) {
            return;
        }
        switch (view.getId()){
            case R.id.iv_call_type:
                onAppointmentItemClickListener.get().onCallHistoryItemCallClickListerner(idx,!object.isDelivered());
                break;

                default:
                    onAppointmentItemClickListener.get().onCallHistoryItemClickListerner(idx);
                    break;
        }


    }
}

