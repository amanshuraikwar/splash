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

package com.sonu.app.splash.ui.transition;

import android.content.Context;
import android.graphics.Rect;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;

/**
 * Shared element transitions do not seem to like transitioning from a single view to two separate
 * views so we need to alter the ChangeBounds transition to compensate
 */
public class PhotoSharedEnter extends ChangeBounds {

    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";

    private Context context;

    public PhotoSharedEnter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

        int width = ((View) transitionValues.values.get(PROPNAME_PARENT)).getWidth();
        int height = ((View) transitionValues.values.get(PROPNAME_PARENT)).getHeight();

        Rect bounds = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        bounds.right = width;
        bounds.bottom = (int) (((float)screenWidth) * ((float)height) / ((float)width));
        transitionValues.values.put(PROPNAME_BOUNDS, bounds);
    }

}
