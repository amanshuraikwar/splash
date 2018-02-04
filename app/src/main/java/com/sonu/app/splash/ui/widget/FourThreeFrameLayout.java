package com.sonu.app.splash.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author Karim Abou Zeid (kabouzeid)
 * @https://github.com/kabouzeid
 */
public class FourThreeFrameLayout extends FrameLayout {


    public FourThreeFrameLayout(Context context) {
        super(context);
    }

    public FourThreeFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FourThreeFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int fourThreeHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) * 3 / 4,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, fourThreeHeight);
    }
}