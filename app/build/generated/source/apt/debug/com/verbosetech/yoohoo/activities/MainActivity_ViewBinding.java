// Generated code from Butter Knife. Do not modify!
package com.verbosetech.yoohoo.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingMenuLayout;
import com.verbosetech.yoohoo.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.usersImage = Utils.findRequiredViewAsType(source, R.id.users_image, "field 'usersImage'", CircleImageView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.menuRecyclerView = Utils.findRequiredViewAsType(source, R.id.menu_recycler_view, "field 'menuRecyclerView'", RecyclerView.class);
    target.swipeMenuRecyclerView = Utils.findRequiredViewAsType(source, R.id.menu_recycler_view_swipe_refresh, "field 'swipeMenuRecyclerView'", SwipeRefreshLayout.class);
    target.menuLayout = Utils.findRequiredViewAsType(source, R.id.menu_layout, "field 'menuLayout'", FlowingMenuLayout.class);
    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawerLayout'", FlowingDrawer.class);
    target.searchContact = Utils.findRequiredViewAsType(source, R.id.searchContact, "field 'searchContact'", EditText.class);
    target.invite = Utils.findRequiredViewAsType(source, R.id.invite, "field 'invite'", TextView.class);
    target.toolbarContainer = Utils.findRequiredViewAsType(source, R.id.toolbarContainer, "field 'toolbarContainer'", RelativeLayout.class);
    target.cabContainer = Utils.findRequiredViewAsType(source, R.id.cabContainer, "field 'cabContainer'", RelativeLayout.class);
    target.selectedCount = Utils.findRequiredViewAsType(source, R.id.selectedCount, "field 'selectedCount'", TextView.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabLayout, "field 'tabLayout'", TabLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewPager, "field 'viewPager'", ViewPager.class);
    target.floatingActionButton = Utils.findRequiredViewAsType(source, R.id.addConversation, "field 'floatingActionButton'", FloatingActionButton.class);
    target.coordinatorLayout = Utils.findRequiredViewAsType(source, R.id.coordinatorLayout, "field 'coordinatorLayout'", CoordinatorLayout.class);
    target.backImage = Utils.findRequiredViewAsType(source, R.id.back_button, "field 'backImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.usersImage = null;
    target.toolbar = null;
    target.menuRecyclerView = null;
    target.swipeMenuRecyclerView = null;
    target.menuLayout = null;
    target.drawerLayout = null;
    target.searchContact = null;
    target.invite = null;
    target.toolbarContainer = null;
    target.cabContainer = null;
    target.selectedCount = null;
    target.tabLayout = null;
    target.viewPager = null;
    target.floatingActionButton = null;
    target.coordinatorLayout = null;
    target.backImage = null;
  }
}
