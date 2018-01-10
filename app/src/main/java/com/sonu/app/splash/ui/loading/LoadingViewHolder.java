package com.sonu.app.splash.ui.loading;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;

import butterknife.BindView;

/**
 * Created by amanshuraikwar on 23/12/17.
 */

public class LoadingViewHolder extends ViewHolder<LoadingListItem> {

    @LayoutRes
    public static final int LAYOUT = R.layout.item_loading;

    @BindView(R.id.infoTv)
    TextView infoTv;

    @BindView(R.id.actionBtn)
    Button actionBtn;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(LoadingListItem listItem, FragmentActivity parentActivity) {

        if (listItem.getState() == LoadingListItem.STATE.NORMAL) {

            progressBar.setVisibility(View.GONE);
            actionBtn.setVisibility(View.VISIBLE);
            infoTv.setText(listItem.getInfoText());
            actionBtn.setText(listItem.getActionText());
        } else if (listItem.getState() == LoadingListItem.STATE.LOADING) {
            progressBar.setVisibility(View.VISIBLE);
            actionBtn.setVisibility(View.GONE);
            infoTv.setText(listItem.getInfoText());
        } else {
            progressBar.setVisibility(View.GONE);
            actionBtn.setVisibility(View.VISIBLE);
            infoTv.setText(listItem.getInfoText());
            actionBtn.setText(listItem.getActionText());
        }
    }
}
