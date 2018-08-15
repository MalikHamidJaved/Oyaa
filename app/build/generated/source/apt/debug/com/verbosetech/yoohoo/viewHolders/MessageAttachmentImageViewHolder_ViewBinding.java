// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.viewHolders;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.internal.Utils;
import com.verbosetech.yoohoo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAttachmentImageViewHolder_ViewBinding extends BaseMessageViewHolder_ViewBinding {
  private MessageAttachmentImageViewHolder target;

  @UiThread
  public MessageAttachmentImageViewHolder_ViewBinding(MessageAttachmentImageViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.image = Utils.findRequiredViewAsType(source, R.id.image, "field 'image'", ImageView.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.container, "field 'll'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    MessageAttachmentImageViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.image = null;
    target.ll = null;

    super.unbind();
  }
}
