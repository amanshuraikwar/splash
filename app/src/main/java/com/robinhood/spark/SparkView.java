package com.robinhood.spark;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.sonu.app.splash.R;

public class SparkView extends View {

    public interface ScrubListener {
        void onScrubbed(@Nullable Object value);
    }

    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint scrubPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path linePath = new Path();

    private SparkAdapter adapter;
    private ScrubListener scrubListener;
    private boolean scrubEnabled;
    private int scrubIndex = -1;

    public SparkView(Context context) {
        this(context, null);
    }

    public SparkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SparkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaults(context);
        readAttributes(context, attrs);
    }

    private void initDefaults(Context context) {
        float density = getResources().getDisplayMetrics().density;
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStrokeWidth(2f * density);
        linePaint.setColor(ContextCompat.getColor(context, R.color.grey1));

        scrubPaint.setStyle(Paint.Style.STROKE);
        scrubPaint.setStrokeWidth(1f * density);
        scrubPaint.setColor(ContextCompat.getColor(context, R.color.grey2));
    }

    private void readAttributes(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SparkView);
        linePaint.setColor(typedArray.getColor(
                R.styleable.SparkView_spark_lineColor,
                linePaint.getColor()));
        linePaint.setStrokeWidth(typedArray.getDimension(
                R.styleable.SparkView_spark_lineWidth,
                linePaint.getStrokeWidth()));
        float cornerRadius = typedArray.getDimension(
                R.styleable.SparkView_spark_cornerRadius,
                0f);
        if (cornerRadius > 0f) {
            linePaint.setPathEffect(new CornerPathEffect(cornerRadius));
        }
        scrubEnabled = typedArray.getBoolean(
                R.styleable.SparkView_spark_scrubEnabled,
                false);
        scrubPaint.setColor(typedArray.getColor(
                R.styleable.SparkView_spark_scrubLineColor,
                scrubPaint.getColor()));
        typedArray.recycle();
    }

    public void setAdapter(SparkAdapter adapter) {
        this.adapter = adapter;
        scrubIndex = -1;
        invalidate();
    }

    public void setScrubListener(ScrubListener scrubListener) {
        this.scrubListener = scrubListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (adapter == null || adapter.getCount() == 0) {
            return;
        }

        int count = adapter.getCount();
        buildLinePath(count);
        canvas.drawPath(linePath, linePaint);

        if (scrubEnabled && scrubIndex >= 0) {
            float x = xForIndex(scrubIndex, count);
            canvas.drawLine(x, getPaddingTop(), x, getHeight() - getPaddingBottom(), scrubPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!scrubEnabled || adapter == null || adapter.getCount() == 0) {
            return super.onTouchEvent(event);
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                updateScrub(event.getX());
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                clearScrub();
                getParent().requestDisallowInterceptTouchEvent(false);
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void buildLinePath(int count) {
        linePath.reset();
        float minY = findMinY(count);
        float maxY = findMaxY(count);

        for (int i = 0; i < count; i++) {
            float x = xForIndex(i, count);
            float y = yForValue(adapter.getY(i), minY, maxY);
            if (i == 0) {
                linePath.moveTo(x, y);
            } else {
                linePath.lineTo(x, y);
            }
        }
    }

    private void updateScrub(float touchX) {
        int count = adapter.getCount();
        int index;
        if (count == 1) {
            index = 0;
        } else {
            float left = getPaddingLeft();
            float right = getWidth() - getPaddingRight();
            float normalized = (touchX - left) / Math.max(1f, right - left);
            index = Math.round(clamp(normalized, 0f, 1f) * (count - 1));
        }

        scrubIndex = index;
        if (scrubListener != null) {
            scrubListener.onScrubbed(adapter.getItem(index));
        }
        invalidate();
    }

    private void clearScrub() {
        scrubIndex = -1;
        if (scrubListener != null) {
            scrubListener.onScrubbed(null);
        }
        invalidate();
    }

    private float xForIndex(int index, int count) {
        float left = getPaddingLeft();
        float right = getWidth() - getPaddingRight();
        if (count == 1) {
            return (left + right) / 2f;
        }
        return left + ((right - left) * index / (count - 1f));
    }

    private float yForValue(float value, float minY, float maxY) {
        float top = getPaddingTop();
        float bottom = getHeight() - getPaddingBottom();
        if (maxY == minY) {
            return (top + bottom) / 2f;
        }
        float normalized = (value - minY) / (maxY - minY);
        return bottom - ((bottom - top) * normalized);
    }

    private float findMinY(int count) {
        float min = adapter.getY(0);
        for (int i = 1; i < count; i++) {
            min = Math.min(min, adapter.getY(i));
        }
        return min;
    }

    private float findMaxY(int count) {
        float max = adapter.getY(0);
        for (int i = 1; i < count; i++) {
            max = Math.max(max, adapter.getY(i));
        }
        return max;
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
