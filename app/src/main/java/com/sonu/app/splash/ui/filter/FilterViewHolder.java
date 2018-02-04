package com.sonu.app.splash.ui.filter;

import android.content.res.ColorStateList;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;

/**
 * Created by amanshuraikwar on 27/01/18.
 */

public class FilterViewHolder extends ViewHolder<FilterListItem> {

    private static final String TAG = LogUtils.getLogTag(FilterViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_filter;

    @BindView(R.id.filterIcon)
    ImageView filterIcon;

    @BindView(R.id.text)
    TextView text;

    public FilterViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(FilterListItem listItem, FragmentActivity parentActivity) {

        text.setText(listItem.getText());
        filterIcon.setImageDrawable(
                ContextCompat.getDrawable(parentActivity, listItem.getIconDrawableId()));

        if (listItem.getIconColor() != 0) {

            filterIcon.setImageTintList(ColorStateList.valueOf(listItem.getIconColor()));
        }
    }
}
