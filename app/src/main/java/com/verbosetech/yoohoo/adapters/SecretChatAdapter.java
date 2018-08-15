package com.verbosetech.yoohoo.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.interfaces.ContextualModeInteractor;
import com.verbosetech.yoohoo.interfaces.OnUserGroupItemClick;
import com.verbosetech.yoohoo.models.Chat;
import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.SecretChat;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SecretChatAdapter extends RecyclerView.Adapter<SecretChatAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<SecretChat> dataList;
    private OnUserGroupItemClick itemClickListener;
    private ContextualModeInteractor contextualModeInteractor;
    private int selectedCount = 0;

    public SecretChatAdapter(Context context, ArrayList<SecretChat> dataList) {
        this.context = context;
        this.dataList = dataList;

        if (context instanceof OnUserGroupItemClick) {
            this.itemClickListener = (OnUserGroupItemClick) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnUserGroupItemClick");
        }

        if (context instanceof ContextualModeInteractor) {
            this.contextualModeInteractor = (ContextualModeInteractor) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ContextualModeInteractor");
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView status, name, lastMessage, time;
        //        private ImageView image;
        private CircleImageView image;
        private RelativeLayout user_details_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.emotion);
            name = itemView.findViewById(R.id.user_name);
            time = itemView.findViewById(R.id.time);
            lastMessage = itemView.findViewById(R.id.message);
            image = itemView.findViewById(R.id.user_image);
            user_details_container = itemView.findViewById(R.id.user_details_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contextualModeInteractor.isContextualMode()) {
                        toggleSelection(dataList.get(getAdapterPosition()), getAdapterPosition());
                    } else {
                        int pos = getAdapterPosition();
                        if (pos != -1) {
                            SecretChat chat = dataList.get(pos);
                            if (chat.getUser() != null)
                                itemClickListener.OnUserClick(chat.getUser(), pos, image);
                            else if (chat.getGroup() != null)
                                itemClickListener.OnGroupClick(chat.getGroup(), pos, image);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    contextualModeInteractor.enableContextualMode();
                    toggleSelection(dataList.get(getAdapterPosition()), getAdapterPosition());
                    return true;
                }
            });
        }

        private void setData(SecretChat chat) {
            User chatUser = chat.getUser();
            Group chatGroup = chat.getGroup();

            Glide.with(context).load(chatUser != null ? chatUser.getImage() : chatGroup.getImage()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(image);

            name.setText(chatUser != null ? chatUser.getNameToDisplay() : chatGroup.getName());
            name.setCompoundDrawablesWithIntrinsicBounds(0, 0, !chat.isRead() ? R.drawable.ring_blue : 0, 0);
            status.setText(chatUser != null ? chatUser.getStatus() : chatGroup.getStatus());

            time.setText(Helper.getDateTime(chat.getTimeUpdated()));
            lastMessage.setText(chat.getLastMessage());
            lastMessage.setTextColor(ContextCompat.getColor(context, !chat.isRead() ? R.color.textColorPrimary : R.color.textColorSecondary));

            user_details_container.setBackgroundColor(ContextCompat.getColor(context, (chat.isSelected() ? R.color.bg_gray : R.color.colorIcon)));
        }
    }

    private void toggleSelection(SecretChat chat, int position) {
        chat.setSelected(!chat.isSelected());
        notifyItemChanged(position);

        if (chat.isSelected())
            selectedCount++;
        else
            selectedCount--;

        contextualModeInteractor.updateSelectedCount(selectedCount);
    }

    public void disableContextualMode() {
        selectedCount = 0;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).isSelected()) {
                dataList.get(i).setSelected(false);
                notifyItemChanged(i);
            }
        }
    }

}
