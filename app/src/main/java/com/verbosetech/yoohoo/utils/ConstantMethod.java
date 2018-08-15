package com.verbosetech.yoohoo.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import com.verbosetech.yoohoo.R;

public class ConstantMethod {

    public static void replaceFragment(FragmentManager manager, Fragment fragment, int view) {
        String fragmentTag = fragment.getClass().getName();

        boolean fragmentPopped;
        try {
            fragmentPopped = manager.popBackStackImmediate(fragmentTag, 0);
        } catch (Exception e) {
            fragmentPopped = false;
        }
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.left_to_right,
                    R.anim.right_to_left);
            ft.replace(view, fragment, fragmentTag);
            ft.addToBackStack(fragmentTag);
            ft.commit();
        }
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
