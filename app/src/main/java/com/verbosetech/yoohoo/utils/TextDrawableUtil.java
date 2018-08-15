package com.verbosetech.yoohoo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

/**
 * Created by mayank on 9/5/17.
 */

public class TextDrawableUtil {

    public static Drawable getNameIcon(@NonNull Context context, @NonNull String name, int dp) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int px = dp == -1 ? -1 : GeneralUtils.dpToPx(context, dp);
        return TextDrawable.builder()
                .beginConfig()
                .width(px)
                .height(px)
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(String.valueOf(name.charAt(0)), generator.getColor(name));
    }

}
