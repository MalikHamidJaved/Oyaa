package com.verbosetech.yoohoo.viewHolders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.interfaces.OnMessageItemClick;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.GeneralUtils;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.LinkTransformationMethod;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.verbosetech.yoohoo.adapters.MessageAdapter.OTHER;

/**
 * Created by mayank on 11/5/17.
 */

public class BaseMessageViewHolder extends RecyclerView.ViewHolder {
    protected static int lastPosition;
    public static boolean animate;
    protected static View newMessageView;
    private int attachmentType;
    protected Context context;

    private static int _48dpInPx = -1;
    private Message message;
    private OnMessageItemClick itemClickListener;

    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.senderName)
    TextView senderName;

    public BaseMessageViewHolder(View itemView, OnMessageItemClick itemClickListener) {
        super(itemView);
        if (itemClickListener != null)
            this.itemClickListener = itemClickListener;
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
        if (_48dpInPx == -1) _48dpInPx = GeneralUtils.dpToPx(itemView.getContext(), 48);
    }

    public BaseMessageViewHolder(View itemView, int attachmentType, OnMessageItemClick itemClickListener) {
        super(itemView);
        this.itemClickListener = itemClickListener;
        this.attachmentType = attachmentType;
    }

    public BaseMessageViewHolder(View itemView, View newMessage, OnMessageItemClick itemClickListener) {
        this(itemView, itemClickListener);
        this.itemClickListener = itemClickListener;
        if (newMessageView == null) newMessageView = newMessage;
    }

    protected boolean isMine() {
        return (getItemViewType() & OTHER) != OTHER;
    }

    public void setData(Message message, int position, HashMap<String, User> myUsersNameInPhoneMap) {
        this.message = message;
        if (attachmentType == AttachmentTypes.NONE_TYPING)
            return;
        time.setText(Helper.getTime(message.getDate()));
        if (message.getRecipientId().startsWith(Helper.GROUP_PREFIX)) {
            String nameToDisplay = message.getSenderName();
            if (myUsersNameInPhoneMap != null && myUsersNameInPhoneMap.containsKey(message.getSenderId())) {
                nameToDisplay = myUsersNameInPhoneMap.get(message.getSenderId()).getNameToDisplay();
            }
            senderName.setText(isMine() ? "You" : nameToDisplay);
            senderName.setVisibility(View.VISIBLE);
        } else {
            senderName.setVisibility(View.GONE);
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) cardView.getLayoutParams();
        if (isMine()) {
            layoutParams.gravity = Gravity.RIGHT;
            layoutParams.leftMargin = _48dpInPx;
            time.setCompoundDrawablesWithIntrinsicBounds(0, 0, message.isSent() ? (message.isDelivered() ? R.drawable.ic_done_all_black : R.drawable.ic_done_black) : R.drawable.ic_waiting, 0);
        } else {
            layoutParams.gravity = Gravity.LEFT;
            layoutParams.rightMargin = _48dpInPx;
            itemView.startAnimation(AnimationUtils.makeInAnimation(itemView.getContext(), true));
        }
        cardView.setLayoutParams(layoutParams);
    }

    protected void onItemClick(boolean b) {
        if (itemClickListener != null && message != null) {
            if (b)
                itemClickListener.OnMessageClick(message, getAdapterPosition());
            else
                itemClickListener.OnMessageLongClick(message, getAdapterPosition());
        }
    }

}
