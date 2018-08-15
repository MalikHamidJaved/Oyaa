// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.viewHolders;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.verbosetech.yoohoo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CallHistoryItemViewHolder_ViewBinding implements Unbinder {
  private CallHistoryItemViewHolder target;

  @UiThread
  public CallHistoryItemViewHolder_ViewBinding(CallHistoryItemViewHolder target, View source) {
    this.target = target;

    target.contAppointment = Utils.findRequiredViewAsType(source, R.id.rl_one, "field 'contAppointment'", RelativeLayout.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.tvChecklist = Utils.findRequiredViewAsType(source, R.id.tv_time_date, "field 'tvChecklist'", TextView.class);
    target.ivAnswer = Utils.findRequiredViewAsType(source, R.id.iv_answer, "field 'ivAnswer'", ImageView.class);
    target.ivCallType = Utils.findRequiredViewAsType(source, R.id.iv_call_type, "field 'ivCallType'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CallHistoryItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.contAppointment = null;
    target.tvName = null;
    target.tvChecklist = null;
    target.ivAnswer = null;
    target.ivCallType = null;
  }
}
