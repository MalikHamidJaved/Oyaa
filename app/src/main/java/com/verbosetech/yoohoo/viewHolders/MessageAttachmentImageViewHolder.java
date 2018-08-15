package com.verbosetech.yoohoo.viewHolders;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.activities.ImageViewerActivity;
import com.verbosetech.yoohoo.interfaces.OnMessageItemClick;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageAttachmentImageViewHolder extends BaseMessageViewHolder {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.container)
    LinearLayout ll;

    public MessageAttachmentImageViewHolder(View itemView, OnMessageItemClick itemClickListener) {
        super(itemView, itemClickListener);
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
    public void setData(final Message message, int position, HashMap<String, User> myUsers) {
        super.setData(message, position, myUsers);

        cardView.setCardBackgroundColor(ContextCompat.getColor(context, message.isSelected() ? R.color.colorPrimary : R.color.colorBgLight));
        ll.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorPrimary) : isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));

        Glide.with(context).load(message.getAttachment().getUrl())
                .apply(new RequestOptions().placeholder(R.drawable.image_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.CHAT_CAB)
                    context.startActivity(ImageViewerActivity.newInstance(context, message.getAttachment().getUrl()));
            }
        });

    }
}
