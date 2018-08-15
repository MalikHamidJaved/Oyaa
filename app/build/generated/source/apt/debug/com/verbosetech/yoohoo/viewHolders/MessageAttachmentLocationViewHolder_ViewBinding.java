// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.viewHolders;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.verbosetech.yoohoo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAttachmentLocationViewHolder_ViewBinding extends BaseMessageViewHolder_ViewBinding {
  private MessageAttachmentLocationViewHolder target;

  @UiThread
  public MessageAttachmentLocationViewHolder_ViewBinding(MessageAttachmentLocationViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", TextView.class);
    target.locationImage = Utils.findRequiredViewAsType(source, R.id.locationImage, "field 'locationImage'", ImageView.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.container, "field 'll'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    MessageAttachmentLocationViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.text = null;
    target.locationImage = null;
    target.ll = null;

    super.unbind();
  }
}
