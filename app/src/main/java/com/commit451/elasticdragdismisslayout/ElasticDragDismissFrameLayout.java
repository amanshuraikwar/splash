package com.commit451.elasticdragdismisslayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

import com.sonu.app.splash.R;

import java.util.ArrayList;
import java.util.List;

public class ElasticDragDismissFrameLayout extends FrameLayout implements NestedScrollingParent2 {

    private static final float DEFAULT_DRAG_DISMISS_SCALE = 0.95f;
    private static final float DRAG_DAMPING = 0.7f;

    private final List<ElasticDragDismissListener> listeners = new ArrayList<>();
    private final NestedScrollingParentHelper nestedScrollingParentHelper;
    private final int touchSlop;

    private float dragDismissDistance;
    private float dragDismissScale;
    private float downY;
    private float lastY;
    private float rawDragOffsetPixels;
    private boolean dragging;
    private boolean dismissed;

    public ElasticDragDismissFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public ElasticDragDismissFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticDragDismissFrameLayout(@NonNull Context context,
                                         @Nullable AttributeSet attrs,
                                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        dragDismissDistance = dpToPx(120f);
        dragDismissScale = DEFAULT_DRAG_DISMISS_SCALE;
        readAttributes(context, attrs);
    }

    private void readAttributes(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.ElasticDragDismissFrameLayout);
        dragDismissDistance = typedArray.getDimension(
                R.styleable.ElasticDragDismissFrameLayout_dragDismissDistance,
                dragDismissDistance);
        dragDismissScale = typedArray.getFloat(
                R.styleable.ElasticDragDismissFrameLayout_dragDismissScale,
                dragDismissScale);
        typedArray.recycle();
    }

    public void addListener(ElasticDragDismissListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(ElasticDragDismissListener listener) {
        listeners.remove(listener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (dismissed) {
            return false;
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                lastY = downY;
                dragging = false;
                animate().cancel();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - downY;
                if (Math.abs(dy) > touchSlop && !canScrollableChildMove(dy)) {
                    dragging = true;
                    lastY = event.getY();
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                dragging = false;
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (dismissed) {
            return false;
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                lastY = downY;
                animate().cancel();
                return true;
            case MotionEvent.ACTION_MOVE:
                float dyFromStart = event.getY() - downY;
                if (!dragging && Math.abs(dyFromStart) > touchSlop && !canScrollableChildMove(dyFromStart)) {
                    dragging = true;
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (dragging) {
                    dragBy(event.getY() - lastY);
                    lastY = event.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                settleOrDismiss();
                return true;
            case MotionEvent.ACTION_UP:
                if (!dragging) {
                    performClick();
                }
                settleOrDismiss();
                return true;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child,
                                       @NonNull View target,
                                       int axes,
                                       int type) {
        return !dismissed && (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child,
                                       @NonNull View target,
                                       int axes,
                                       int type) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        nestedScrollingParentHelper.onStopNestedScroll(target, type);
        if (type == ViewCompat.TYPE_TOUCH && rawDragOffsetPixels != 0f) {
            settleOrDismiss();
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target,
                               int dxConsumed,
                               int dyConsumed,
                               int dxUnconsumed,
                               int dyUnconsumed,
                               int type) {
        if (type == ViewCompat.TYPE_TOUCH && dyUnconsumed != 0) {
            dragBy(-dyUnconsumed);
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target,
                                  int dx,
                                  int dy,
                                  @NonNull int[] consumed,
                                  int type) {
        if (type != ViewCompat.TYPE_TOUCH || rawDragOffsetPixels == 0f) {
            return;
        }

        boolean scrollingBackTowardRest =
                (rawDragOffsetPixels > 0f && dy > 0) || (rawDragOffsetPixels < 0f && dy < 0);
        if (!scrollingBackTowardRest) {
            return;
        }

        float previous = rawDragOffsetPixels;
        if (Math.abs(dy) > Math.abs(previous)) {
            rawDragOffsetPixels = 0f;
        } else {
            rawDragOffsetPixels -= dy;
        }
        consumed[1] = Math.round(previous - rawDragOffsetPixels);
        updateDragState();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return onStartNestedScroll(child, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        onNestedScrollAccepted(child, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        onStopNestedScroll(target, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScroll(@NonNull View target,
                               int dxConsumed,
                               int dyConsumed,
                               int dxUnconsumed,
                               int dyUnconsumed) {
        onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target,
                                  int dx,
                                  int dy,
                                  @NonNull int[] consumed) {
        onNestedPreScroll(target, dx, dy, consumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public boolean onNestedFling(@NonNull View target,
                                 float velocityX,
                                 float velocityY,
                                 boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }

    private void dragBy(float offsetPixels) {
        rawDragOffsetPixels += offsetPixels;
        updateDragState();
    }

    private void updateDragState() {
        float elasticOffsetPixels = rawDragOffsetPixels * DRAG_DAMPING;
        float dismissProgress = Math.min(1f, Math.abs(elasticOffsetPixels) / dragDismissDistance);
        float scale = 1f - ((1f - dragDismissScale) * dismissProgress);

        setTranslationY(elasticOffsetPixels);
        setScaleX(scale);
        setScaleY(scale);

        float elasticOffset = Math.copySign(dismissProgress, elasticOffsetPixels);
        float rawOffset = rawDragOffsetPixels / dragDismissDistance;
        for (ElasticDragDismissListener listener : new ArrayList<>(listeners)) {
            listener.onDrag(elasticOffset, elasticOffsetPixels, rawOffset, rawDragOffsetPixels);
        }
    }

    private void settleOrDismiss() {
        getParent().requestDisallowInterceptTouchEvent(false);
        dragging = false;

        if (Math.abs(getTranslationY()) >= dragDismissDistance) {
            dismissed = true;
            for (ElasticDragDismissListener listener : new ArrayList<>(listeners)) {
                listener.onDragDismissed();
            }
            return;
        }

        rawDragOffsetPixels = 0f;
        animate()
                .translationY(0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(200L)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        notifyDragReset();
                        animate().setListener(null);
                    }
                })
                .start();
    }

    private void notifyDragReset() {
        for (ElasticDragDismissListener listener : new ArrayList<>(listeners)) {
            listener.onDrag(0f, 0f, 0f, 0f);
        }
    }

    private boolean canScrollableChildMove(float dy) {
        int direction = dy > 0f ? -1 : 1;
        return canAnyChildScroll(this, direction);
    }

    private boolean canAnyChildScroll(View view, int direction) {
        if (view != this && view.canScrollVertically(direction)) {
            return true;
        }

        if (!(view instanceof ViewGroup)) {
            return false;
        }

        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (canAnyChildScroll(viewGroup.getChildAt(i), direction)) {
                return true;
            }
        }
        return false;
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
