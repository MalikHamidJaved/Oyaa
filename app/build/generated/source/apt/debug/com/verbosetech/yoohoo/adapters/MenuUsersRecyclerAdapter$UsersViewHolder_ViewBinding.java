// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.verbosetech.yoohoo.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MenuUsersRecyclerAdapter$UsersViewHolder_ViewBinding implements Unbinder {
  private MenuUsersRecyclerAdapter.UsersViewHolder target;

  @UiThread
  public MenuUsersRecyclerAdapter$UsersViewHolder_ViewBinding(MenuUsersRecyclerAdapter.UsersViewHolder target,
      View source) {
    this.target = target;

    target.userImage = Utils.findRequiredViewAsType(source, R.id.user_image, "field 'userImage'", CircleImageView.class);
    target.userName = Utils.findRequiredViewAsType(source, R.id.user_name, "field 'userName'", TextView.class);
    target.user_no = Utils.findRequiredViewAsType(source, R.id.user_no, "field 'user_no'", TextView.class);
    target.tv_invite = Utils.findRequiredViewAsType(source, R.id.tv_invite, "field 'tv_invite'", TextView.class);
    target.appCompatRadioButton = Utils.findRequiredViewAsType(source, R.id.userSelected, "field 'appCompatRadioButton'", AppCompatRadioButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MenuUsersRecyclerAdapter.UsersViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userImage = null;
    target.userName = null;
    target.user_no = null;
    target.tv_invite = null;
    target.appCompatRadioButton = null;
  }
}
