package com.sonu.app.splash.util;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;

/**
 * Created by amanshuraikwar on 25/12/17.
 */

public class ColorHelper {

    public static final float LIGHT_COLOR_LIGHT_THRESHOLD = 0.5f;

    public static final int LIGHT_TEXT_COLOR = Color.parseColor("#eeeeee");
    public static final int DARK_TEXT_COLOR = Color.parseColor("#212121");

    public static boolean isColorLight(int color) {
        float hsl[] = new float[3];
        ColorUtils.colorToHSL(color, hsl);

        return hsl[2] > LIGHT_COLOR_LIGHT_THRESHOLD;
    }
}
