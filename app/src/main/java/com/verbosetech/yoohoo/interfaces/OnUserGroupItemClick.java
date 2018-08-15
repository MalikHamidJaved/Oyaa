package com.verbosetech.yoohoo.interfaces;

import android.view.View;

import com.verbosetech.yoohoo.models.Group;
import com.verbosetech.yoohoo.models.User;

/**
 * Created by a_man on 5/10/2017.
 */

public interface OnUserGroupItemClick {
    void OnUserClick(User user, int position, View userImage);
    void OnGroupClick(Group group, int position, View userImage);
}
