package com.sonu.app.splash.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.graphics.ColorUtils;

/**
 * Created by amanshuraikwar on 10/01/18.
 */

public class DrawableUtils {

    private static final int RIPPLE_ALPHA = 55;

    public static RippleDrawable createRippleDrawable(int color) {
        int rippleColor = ColorUtils.setAlphaComponent(color, RIPPLE_ALPHA);
        return new RippleDrawable(ColorStateList.valueOf(rippleColor), null, null);
    }
}
