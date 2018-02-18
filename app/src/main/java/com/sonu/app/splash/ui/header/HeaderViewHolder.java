package com.sonu.app.splash.ui.header;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;

/**
 * Created by amanshuraikwar on 27/01/18.
 */

public class HeaderViewHolder extends ViewHolder<HeaderListItem> {

    private static final String TAG = LogUtils.getLogTag(HeaderViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_header;

    @BindView(R.id.text)
    TextView textTv;

    public HeaderViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final HeaderListItem listItem, FragmentActivity parentActivity) {

        textTv.setText(listItem.getText());
    }
}
