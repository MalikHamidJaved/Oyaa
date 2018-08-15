// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.viewHolders;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.verbosetech.yoohoo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAttachmentContactViewHolder_ViewBinding extends BaseMessageViewHolder_ViewBinding {
  private MessageAttachmentContactViewHolder target;

  @UiThread
  public MessageAttachmentContactViewHolder_ViewBinding(MessageAttachmentContactViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", TextView.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.container, "field 'll'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    MessageAttachmentContactViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.text = null;
    target.ll = null;

    super.unbind();
  }
}
