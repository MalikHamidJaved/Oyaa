package com.verbosetech.yoohoo.models;

/**
 * Created by mayank on 11/5/17.
 */

public class DownloadFileEvent {
    private Attachment attachment;
    private int position, attachmentType;

    public DownloadFileEvent(int attachmentType, Attachment attachment, int adapterPosition) {
        this.attachment = attachment;
        this.attachmentType = attachmentType;
        this.position = adapterPosition;
    }

    public int getPosition() {
        return position;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    @AttachmentTypes.AttachmentType
    public int getAttachmentType() {
        return attachmentType;
    }
}

