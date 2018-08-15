package com.verbosetech.yoohoo.viewHolders;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.interfaces.OnMessageItemClick;
import com.verbosetech.yoohoo.models.Attachment;
import com.verbosetech.yoohoo.models.AttachmentTypes;
import com.verbosetech.yoohoo.models.DownloadFileEvent;
import com.verbosetech.yoohoo.models.Message;
import com.verbosetech.yoohoo.models.User;
import com.verbosetech.yoohoo.utils.FileUtils;
import com.verbosetech.yoohoo.utils.Helper;
import com.verbosetech.yoohoo.utils.MyFileProvider;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageAttachmentDocumentViewHolder extends BaseMessageViewHolder {

    @BindView(R.id.file_extention)
    TextView fileExtention;
    @BindView(R.id.file_name)
    TextView fileName;
    @BindView(R.id.file_size)
    TextView fileSize;
    @BindView(R.id.container)
    LinearLayout ll;
    @BindView(R.id.card_view)
    CardView card_view;
    private Message message;
    private File file;

    public MessageAttachmentDocumentViewHolder(View itemView, OnMessageItemClick itemClickListener) {
        super(itemView, itemClickListener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.CHAT_CAB)
                    downloadFile();
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
    public void setData(Message message, int position, HashMap<String,User> myUsers) {
        super.setData(message, position, myUsers);
        card_view.setCardBackgroundColor(ContextCompat.getColor(context, message.isSelected() ? R.color.colorPrimary : R.color.colorBgLight));
        ll.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorPrimary) : isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));

        this.message = message;
        file = new File(Environment.getExternalStorageDirectory() + "/"
                +
                context.getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(message.getAttachmentType()) + (isMine() ? "/.sent/" : "")
                , message.getAttachment().getName());
        ll.setBackgroundColor(isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));
        Attachment attachment = message.getAttachment();
        fileName.setText(attachment.getName());
        fileSize.setText(FileUtils.getReadableFileSize(attachment.getBytesCount()));
        fileExtention.setText(FileUtils.getExtension(attachment.getName()));
    }

    //@OnClick(R.id.download)
    public void downloadFile() {
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = MyFileProvider.getUriForFile(context,
                    context.getString(R.string.authority),
                    file);
            intent.setDataAndType(uri, Helper.getMimeType(context, uri)); //storage path is path of your vcf file and vFile is name of that file.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } else if (!isMine())
            EventBus.getDefault().post(new DownloadFileEvent(message.getAttachmentType(), message.getAttachment(), getAdapterPosition()));
        else
            Toast.makeText(context, "File unavailable", Toast.LENGTH_SHORT).show();
    }
}
