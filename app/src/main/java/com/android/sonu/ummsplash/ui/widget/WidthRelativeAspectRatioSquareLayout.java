package com.android.sonu.ummsplash.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * @author Karim Abou Zeid (kabouzeid)
 * @https://github.com/kabouzeid
 */
public class WidthRelativeAspectRatioSquareLayout extends FrameLayout {

    private float aspectRatio = 1;

    public WidthRelativeAspectRatioSquareLayout(Context context) {
        super(context);
    }

    public WidthRelativeAspectRatioSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WidthRelativeAspectRatioSquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WidthRelativeAspectRatioSquareLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //noinspection SuspiciousNameCombination
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), (int) (aspectRatio * getMeasuredWidth()));
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        requestLayout();
    }
}