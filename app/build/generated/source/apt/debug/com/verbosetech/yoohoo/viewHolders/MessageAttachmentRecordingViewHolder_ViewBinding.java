// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.viewHolders;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.verbosetech.yoohoo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAttachmentRecordingViewHolder_ViewBinding extends BaseMessageViewHolder_ViewBinding {
  private MessageAttachmentRecordingViewHolder target;

  @UiThread
  public MessageAttachmentRecordingViewHolder_ViewBinding(MessageAttachmentRecordingViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", TextView.class);
    target.durationOrSize = Utils.findRequiredViewAsType(source, R.id.duration, "field 'durationOrSize'", TextView.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.container, "field 'll'", LinearLayout.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    target.playPauseToggle = Utils.findRequiredViewAsType(source, R.id.playPauseToggle, "field 'playPauseToggle'", ImageView.class);
  }

  @Override
  public void unbind() {
    MessageAttachmentRecordingViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.text = null;
    target.durationOrSize = null;
    target.ll = null;
    target.progressBar = null;
    target.playPauseToggle = null;

    super.unbind();
  }
}
