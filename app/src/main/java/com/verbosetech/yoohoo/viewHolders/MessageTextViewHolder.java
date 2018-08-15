package com.verbosetech.yoohoo.viewHolders;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vanniktech.emoji.EmojiTextView;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.interfaces.OnMessageItemClick;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.GeneralUtils;
import com.verbosetech.yoohoo.utils.LinkTransformationMethod;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

import static com.verbosetech.yoohoo.adapters.MessageAdapter.MY;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageTextViewHolder extends BaseMessageViewHolder {
    @BindView(R.id.text)
    EmojiTextView text;
    @BindView(R.id.container)
    LinearLayout ll;

    private Message message;

    private static int _4dpInPx = -1;

    public MessageTextViewHolder(View itemView, View newMessageView, OnMessageItemClick itemClickListener) {
        super(itemView, newMessageView, itemClickListener);
        text.setTransformationMethod(new LinkTransformationMethod());
        text.setMovementMethod(LinkMovementMethod.getInstance());
        if (_4dpInPx == -1) _4dpInPx = GeneralUtils.dpToPx(itemView.getContext(), 4);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(true);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClick(false);
                return true;
            }
        });
    }

    @Override
    public void setData(Message message, int position, HashMap<String, User> myUsers) {
        super.setData(message, position, myUsers);
        this.message = message;
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, message.isSelected() ? R.color.colorPrimary : R.color.colorBgLight));
        ll.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorPrimary) : isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));
        text.setText(message.getBody());
        if (getItemViewType() == MY) {
            animateView(position);
        }
    }

    private void animateView(int position) {
        if (animate && position > lastPosition) {

            itemView.post(new Runnable() {
                @Override
                public void run() {

                    float originalX = cardView.getX();
                    final float originalY = itemView.getY();
                    int[] loc = new int[2];
                    newMessageView.getLocationOnScreen(loc);
                    cardView.setX(loc[0] / 2);
                    itemView.setY(loc[1]);
                    ValueAnimator radiusAnimator = new ValueAnimator();
                    radiusAnimator.setFloatValues(80, _4dpInPx);
                    radiusAnimator.setDuration(850);
                    radiusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            cardView.setRadius((Float) animation.getAnimatedValue());
                        }
                    });
                    radiusAnimator.start();
                    cardView.animate().x(originalX).setDuration(900).setInterpolator(new DecelerateInterpolator()).start();
                    itemView.animate().y(originalY - _4dpInPx).setDuration(750).setInterpolator(new DecelerateInterpolator()).start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            itemView.animate().y(originalY + _4dpInPx).setDuration(250).setInterpolator(new DecelerateInterpolator()).start();
                        }
                    }, 750);
                }
            });
            lastPosition = position;
//            setAnimation(getAdapterPosition());
            animate = false;
        }
    }

}
