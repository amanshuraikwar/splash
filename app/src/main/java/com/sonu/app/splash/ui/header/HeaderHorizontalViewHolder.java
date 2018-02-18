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

public class HeaderHorizontalViewHolder extends ViewHolder<HeaderHorizontalListItem> {

    private static final String TAG = LogUtils.getLogTag(HeaderHorizontalViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_header_horizontal;

    @BindView(R.id.textTv)
    TextView textTv;

    @BindView(R.id.parent)
    View parent;

    public HeaderHorizontalViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final HeaderHorizontalListItem listItem, FragmentActivity parentActivity) {

        textTv.setText(listItem.getText());

        parent.setOnClickListener(view -> listItem.getOnClickListener().onClick());
    }
}
