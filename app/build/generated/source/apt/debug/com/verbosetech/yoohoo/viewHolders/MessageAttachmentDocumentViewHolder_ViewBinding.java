// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.viewHolders;

import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.verbosetech.yoohoo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAttachmentDocumentViewHolder_ViewBinding extends BaseMessageViewHolder_ViewBinding {
  private MessageAttachmentDocumentViewHolder target;

  @UiThread
  public MessageAttachmentDocumentViewHolder_ViewBinding(MessageAttachmentDocumentViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.fileExtention = Utils.findRequiredViewAsType(source, R.id.file_extention, "field 'fileExtention'", TextView.class);
    target.fileName = Utils.findRequiredViewAsType(source, R.id.file_name, "field 'fileName'", TextView.class);
    target.fileSize = Utils.findRequiredViewAsType(source, R.id.file_size, "field 'fileSize'", TextView.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.container, "field 'll'", LinearLayout.class);
    target.card_view = Utils.findRequiredViewAsType(source, R.id.card_view, "field 'card_view'", CardView.class);
  }

  @Override
  public void unbind() {
    MessageAttachmentDocumentViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fileExtention = null;
    target.fileName = null;
    target.fileSize = null;
    target.ll = null;
    target.card_view = null;

    super.unbind();
  }
}
