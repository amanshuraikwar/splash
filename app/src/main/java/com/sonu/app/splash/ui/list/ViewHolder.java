package com.sonu.app.splash.ui.list;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by sonu on 30/6/17.
 */

public abstract class ViewHolder<Item extends ListItem>
        extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(Item listItem,
                              FragmentActivity parentActivity);
}
