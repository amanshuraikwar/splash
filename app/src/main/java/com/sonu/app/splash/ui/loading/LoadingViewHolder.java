package com.sonu.app.splash.ui.loading;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.util.LogUtils;

/**
 * Created by amanshuraikwar on 23/12/17.
 */

public class LoadingViewHolder extends ViewHolder<LoadingListItem> {

    @LayoutRes
    public static final int LAYOUT = R.layout.item_loading;

    private static final String TAG = LogUtils.getLogTag(LoadingViewHolder.class);
    ProgressBar progressBar;
    Button actionBtn;
    View errorOverlay;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
        actionBtn = itemView.findViewById(R.id.actionBtn);
        errorOverlay = itemView.findViewById(R.id.errorOverlay);
    }

    public void bindState(LoadingListItem.STATE state) {

        Log.d(TAG, "bindState:called");

        if (state == LoadingListItem.STATE.NORMAL) {
            errorOverlay.setVisibility(View.VISIBLE);
            actionBtn.setVisibility(View.INVISIBLE);
        } else if (state == LoadingListItem.STATE.LOADING) {
            errorOverlay.setVisibility(View.INVISIBLE);
            actionBtn.setVisibility(View.INVISIBLE);
        } else {
            errorOverlay.setVisibility(View.VISIBLE);
            actionBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bind(LoadingListItem listItem, FragmentActivity parentActivity) {

        Log.d(TAG, "bind:called");

        actionBtn.setOnClickListener(v -> listItem.getOnClickListener().onActionBtnClick());

        bindState(listItem.getState());
    }
}
