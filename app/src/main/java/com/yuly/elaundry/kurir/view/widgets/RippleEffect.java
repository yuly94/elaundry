package com.yuly.elaundry.kurir.view.widgets;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;

/**
 * Created by anonymous on 13/12/16.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RippleEffect {

    public static void applyColor(Drawable d, @ColorInt int color) {
        if (d instanceof RippleDrawable)
            ((RippleDrawable) d).setColor(ColorStateList.valueOf(color));
    }
}