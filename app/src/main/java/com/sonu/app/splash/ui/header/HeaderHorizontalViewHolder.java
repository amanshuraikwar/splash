package com.sonu.app.splash.ui.header;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.util.LogUtils;

/**
 * Created by amanshuraikwar on 27/01/18.
 */

public class HeaderHorizontalViewHolder extends ViewHolder<HeaderHorizontalListItem> {

    private static final String TAG = LogUtils.getLogTag(HeaderHorizontalViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_header_horizontal;
    TextView textTv;
    View parent;

    public HeaderHorizontalViewHolder(View itemView) {
        super(itemView);
        textTv = itemView.findViewById(R.id.textTv);
        parent = itemView.findViewById(R.id.parent);
    }

    @Override
    public void bind(final HeaderHorizontalListItem listItem, FragmentActivity parentActivity) {

        textTv.setText(listItem.getText());

        parent.setOnClickListener(view -> listItem.getOnClickListener().onClick());
    }
}
