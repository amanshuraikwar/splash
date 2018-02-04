package com.sonu.app.splash.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author Karim Abou Zeid (kabouzeid)
 * @https://github.com/kabouzeid
 */
public class HeightRelativeAspectRatioImageView extends AppCompatImageView {

    private float aspectRatio = 1;

    public HeightRelativeAspectRatioImageView(Context context) {
        super(context);
    }

    public HeightRelativeAspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightRelativeAspectRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //noinspection SuspiciousNameCombination
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension((int) ( getMeasuredHeight() / aspectRatio), getMeasuredHeight());
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        requestLayout();
    }
}