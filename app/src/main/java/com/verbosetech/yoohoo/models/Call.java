package com.verbosetech.yoohoo.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Call extends RealmObject implements Parcelable {

    @Nullable
    String image;
    String id;
    @Nullable
    String from;

    String duration;

    private String body, senderName, senderId, recipientId;
    private long date;
    private boolean delivered = false, sent = false;
    private
    @AttachmentTypes.AttachmentType
    int attachmentType;
    private Attachment attachment;

    @Ignore
    private boolean selected;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public static Creator<Call> getCREATOR() {
        return CREATOR;
    }



    public Call(String image, String id, String from, String duration) {
        this.image = image;
        this.id = id;
        this.from = from;
        this.duration = duration;
    }


    public Call() {
    }

    protected Call(Parcel in) {
        image = in.readString();
        id = in.readString();
        from = in.readString();
        duration = in.readString();
    }

    public static final Creator<Call> CREATOR = new Creator<Call>() {
        @Override
        public Call createFromParcel(Parcel in) {
            return new Call(in);
        }

        @Override
        public Call[] newArray(int size) {
            return new Call[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(id);
        parcel.writeString(from);
        parcel.writeString(duration);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(int attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
