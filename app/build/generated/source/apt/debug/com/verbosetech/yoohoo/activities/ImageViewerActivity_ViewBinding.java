// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.github.chrisbanes.photoview.PhotoView;
import com.verbosetech.yoohoo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageViewerActivity_ViewBinding implements Unbinder {
  private ImageViewerActivity target;

  @UiThread
  public ImageViewerActivity_ViewBinding(ImageViewerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ImageViewerActivity_ViewBinding(ImageViewerActivity target, View source) {
    this.target = target;

    target.photoView = Utils.findRequiredViewAsType(source, R.id.photo_view, "field 'photoView'", PhotoView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageViewerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.photoView = null;
  }
}
