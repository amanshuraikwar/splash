/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sonu.app.splash.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.Property;

import com.sonu.app.splash.R;
import com.sonu.app.splash.util.AnimUtils;
import com.sonu.app.splash.util.ColorHelper;

/**
 * An image view which supports parallax scrolling and applying a scrim onto it's content. Get it.
 * <p>
 * It also has a custom pinned state, for use via state lists.
 */
public class ParallaxScrimageView extends FourThreeImageView {

    private static final int[] STATE_PINNED = { R.attr.state_pinned };
    private final Paint scrimPaint;
    private int imageOffset;
    private int minOffset;
    private Rect clipBounds = new Rect();
    private float scrimAlpha = 0f;
    private float maxScrimAlpha = 1f;
    private int scrimColor = Color.TRANSPARENT;
    private float parallaxFactor = -0.5f;
    private boolean isPinned = false;
    private boolean immediatePin = false;

    public static final Property<ParallaxScrimageView, Integer> OFFSET =
            AnimUtils.createIntProperty(new AnimUtils.IntProp<ParallaxScrimageView>("offset") {
                @Override
                public void set(ParallaxScrimageView parallaxScrimageView, int offset) {
                    parallaxScrimageView.setOffset(offset);
                }

                @Override
                public int get(ParallaxScrimageView parallaxScrimageView) {
                    return parallaxScrimageView.getOffset();
                }
            });

    public ParallaxScrimageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ParallaxScrimageView);
        if (a.hasValue(R.styleable.ParallaxScrimageView_scrimColor)) {
            scrimColor = a.getColor(R.styleable.ParallaxScrimageView_scrimColor, scrimColor);
        }
        if (a.hasValue(R.styleable.ParallaxScrimageView_scrimAlpha)) {
            scrimAlpha = a.getFloat(R.styleable.ParallaxScrimageView_scrimAlpha, scrimAlpha);
        }
        if (a.hasValue(R.styleable.ParallaxScrimageView_maxScrimAlpha)) {
            maxScrimAlpha = a.getFloat(R.styleable.ParallaxScrimageView_maxScrimAlpha,
                    maxScrimAlpha);
        }
        if (a.hasValue(R.styleable.ParallaxScrimageView_parallaxFactor)) {
            parallaxFactor = a.getFloat(R.styleable.ParallaxScrimageView_parallaxFactor,
                    parallaxFactor);
        }
        a.recycle();

        scrimPaint = new Paint();
        scrimPaint.setColor(ColorHelper.modifyAlpha(scrimColor, scrimAlpha));
    }

    public int getOffset() {
        return (int) getTranslationY();
    }

    public void setOffset(int offset) {
        offset = Math.max(minOffset, offset);
        if (offset != getTranslationY()) {
            setTranslationY(offset);
            imageOffset = (int) (offset * parallaxFactor);
            clipBounds.set(0, -offset, getWidth(), getHeight());
            setClipBounds(clipBounds);
            setScrimAlpha(Math.min(
                    ((float) -offset / getMinimumHeight()) * maxScrimAlpha, maxScrimAlpha));
            postInvalidateOnAnimation();
        }
        setPinned(offset == minOffset);
    }

    public void setScrimColor(@ColorInt int scrimColor) {
        if (this.scrimColor != scrimColor) {
            this.scrimColor = scrimColor;
            postInvalidateOnAnimation();
        }
    }

    public void setScrimAlpha(@FloatRange(from = 0f, to = 1f) float alpha) {
        if (scrimAlpha != alpha) {
            scrimAlpha = alpha;
            scrimPaint.setColor(ColorHelper.modifyAlpha(scrimColor, scrimAlpha));
            postInvalidateOnAnimation();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h > getMinimumHeight()) {
            minOffset = getMinimumHeight() - h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (imageOffset != 0) {
            final int saveCount = canvas.save();
            canvas.translate(0f, imageOffset);
            super.onDraw(canvas);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), scrimPaint);
            canvas.restoreToCount(saveCount);
        } else {
            super.onDraw(canvas);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), scrimPaint);
        }
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isPinned) {
            mergeDrawableStates(drawableState, STATE_PINNED);
        }
        return drawableState;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean isPinned) {
        if (this.isPinned != isPinned) {
            this.isPinned = isPinned;
            refreshDrawableState();
            if (isPinned && immediatePin) {
                jumpDrawablesToCurrentState();
            }
        }
    }

    public boolean isImmediatePin() {
        return immediatePin;
    }

    /**
     * As the pinned state is designed to work with a {@see StateListAnimator}, we may want to short
     * circuit this animation in certain situations e.g. when flinging a list.
     */
    public void setImmediatePin(boolean immediatePin) {
        this.immediatePin = immediatePin;
    }
}
