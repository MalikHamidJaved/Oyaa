// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.viewHolders;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.verbosetech.yoohoo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAttachmentVideoViewHolder_ViewBinding extends BaseMessageViewHolder_ViewBinding {
  private MessageAttachmentVideoViewHolder target;

  private View view2131231208;

  @UiThread
  public MessageAttachmentVideoViewHolder_ViewBinding(final MessageAttachmentVideoViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", TextView.class);
    target.durationOrSize = Utils.findRequiredViewAsType(source, R.id.videoSize, "field 'durationOrSize'", TextView.class);
    target.videoThumbnail = Utils.findRequiredViewAsType(source, R.id.videoThumbnail, "field 'videoThumbnail'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.videoPlay, "field 'videoPlay' and method 'downloadFile'");
    target.videoPlay = Utils.castView(view, R.id.videoPlay, "field 'videoPlay'", ImageView.class);
    view2131231208 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.downloadFile();
      }
    });
    target.ll = Utils.findRequiredViewAsType(source, R.id.container, "field 'll'", LinearLayout.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  public void unbind() {
    MessageAttachmentVideoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.text = null;
    target.durationOrSize = null;
    target.videoThumbnail = null;
    target.videoPlay = null;
    target.ll = null;
    target.progressBar = null;

    view2131231208.setOnClickListener(null);
    view2131231208 = null;

    super.unbind();
  }
}
