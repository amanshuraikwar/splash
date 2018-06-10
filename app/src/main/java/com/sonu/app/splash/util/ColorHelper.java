package com.sonu.app.splash.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by amanshuraikwar on 25/12/17.
 */

public class ColorHelper {

    public static final float LIGHT_COLOR_LIGHT_THRESHOLD = 0.5f;

    public static final int IS_LIGHT = 0;
    public static final int IS_DARK = 1;
    public static final int LIGHTNESS_UNKNOWN = 2;

    public static final int LIGHT_TEXT_COLOR = Color.parseColor("#eeeeee");
    public static final int DARK_TEXT_COLOR = Color.parseColor("#212121");

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({IS_LIGHT, IS_DARK, LIGHTNESS_UNKNOWN})
    public @interface Lightness {
    }

    public static boolean isDark(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) < LIGHT_COLOR_LIGHT_THRESHOLD;
    }

    public static @Lightness int isDark(Palette palette) {
        Palette.Swatch mostPopulous = getMostPopulousSwatch(palette);
        if (mostPopulous == null) return LIGHTNESS_UNKNOWN;
        return isDark(mostPopulous.getRgb()) ? IS_DARK : IS_LIGHT;
    }

    public static @Nullable
    Palette.Swatch getMostPopulousSwatch(Palette palette) {
        Palette.Swatch mostPopulous = null;
        if (palette != null) {
            for (Palette.Swatch swatch : palette.getSwatches()) {
                if (mostPopulous == null || swatch.getPopulation() > mostPopulous.getPopulation()) {
                    mostPopulous = swatch;
                }
            }
        }
        return mostPopulous;
    }

    public static boolean isDark(@NonNull Bitmap bitmap) {
        return isDark(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    }

    public static boolean isDark(@NonNull Bitmap bitmap, int backupPixelX, int backupPixelY) {
        // first try palette with a small color quant size
        Palette palette = Palette.from(bitmap).maximumColorCount(3).generate();
        if (palette != null && palette.getSwatches().size() > 0) {
            return isDark(palette) == IS_DARK;
        } else {
            // if palette failed, then check the color of the specified pixel
            return isDark(bitmap.getPixel(backupPixelX, backupPixelY));
        }
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult @ColorInt int modifyAlpha(@ColorInt int color,
                                                         @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult
    @ColorInt int modifyAlpha(@ColorInt int color,
                              @FloatRange(from = 0f, to = 1f) float alpha) {
        return modifyAlpha(color, (int) (255f * alpha));
    }
}
