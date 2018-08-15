package com.verbosetech.yoohoo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.fragments.ChatFragment;
import com.verbosetech.yoohoo.interfaces.OnMessageItemClick;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.viewHolders.BaseMessageViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentAudioViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentContactViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentDocumentViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentImageViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentLocationViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentRecordingViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageAttachmentVideoViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageTextViewHolder;
import com.verbosetech.yoohoo.viewHolders.MessageTypingViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by a_man on 1/10/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<BaseMessageViewHolder> {
    private Helper helper;
    private OnMessageItemClick itemClickListener;
    private MessageAttachmentRecordingViewHolder.RecordingViewInteractor recordingViewInteractor;
    private String myId;
    private Context context;
    private ArrayList<Message> messages;
    private View newMessage;
    private HashMap<String, User> myUsersNameInPhoneMap;
    ChatFragment chatFragment;

    public static final int MY = 0x00000000;
    public static final int OTHER = 0x0000100;

    public MessageAdapter(Context context, ArrayList<Message> messages, String myId, View newMessage) {
        this.context = context;
        this.messages = messages;
        this.myId = myId;
        this.newMessage = newMessage;
        this.helper = new Helper(context);
        this.myUsersNameInPhoneMap = helper.getCacheMyUsers();

        if (context instanceof OnMessageItemClick) {
            this.itemClickListener = (OnMessageItemClick) context;

        } else {
            throw new RuntimeException(context.toString() + " must implement OnUserGroupItemClick");
        }

        if (context instanceof MessageAttachmentRecordingViewHolder.RecordingViewInteractor) {
            this.recordingViewInteractor = (MessageAttachmentRecordingViewHolder.RecordingViewInteractor) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement RecordingViewInteractor");
        }
    }



    @Override
    public BaseMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewType &= 0x00000FF;
        switch (viewType) {
            case AttachmentTypes.RECORDING:
                return new MessageAttachmentRecordingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_recording, parent, false), itemClickListener, recordingViewInteractor);
            case AttachmentTypes.AUDIO:
                return new MessageAttachmentAudioViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_audio, parent, false), itemClickListener);
            case AttachmentTypes.CONTACT:
                return new MessageAttachmentContactViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_contact, parent, false), itemClickListener);
            case AttachmentTypes.DOCUMENT:
                return new MessageAttachmentDocumentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_document, parent, false), itemClickListener);
            case AttachmentTypes.IMAGE:
                return new MessageAttachmentImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_image, parent, false), itemClickListener);
            case AttachmentTypes.LOCATION:
                return new MessageAttachmentLocationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_location, parent, false), itemClickListener);
            case AttachmentTypes.VIDEO:
                return new MessageAttachmentVideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_video, parent, false), itemClickListener);
            case AttachmentTypes.NONE_TYPING:
                return new MessageTypingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_typing, parent, false));
            case AttachmentTypes.NONE_TEXT:
            default:
                return new MessageTextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_text, parent, false), newMessage, itemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(BaseMessageViewHolder holder, int position) {
        holder.setData(messages.get(position), position, myUsersNameInPhoneMap);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 0) {
            return super.getItemViewType(position);
        } else {
            Message message = messages.get(position);
            int userType;
            if (message.getSenderId().equals(myId))
                userType = MY;
            else
                userType = OTHER;
            return message.getAttachmentType() | userType;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
