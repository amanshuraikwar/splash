package com.sonu.app.splash.ui.list;

import android.view.View;

import com.sonu.app.splash.ui.loading.LoadingListItem;
import com.sonu.app.splash.ui.loading.LoadingViewHolder;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoViewHolder;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class ListItemTypeFactory {

    public int type(PhotoListItem item) {
        return 0;
    }

    public int type(LoadingListItem item) {
        return 1;
    }

    public int getLayout(int viewType) {
        switch (viewType) {
            case 0:
                return PhotoViewHolder.LAYOUT;
            case 1:
                return LoadingViewHolder.LAYOUT;
            default:
                return 0;
        }
    }

    public ViewHolder createViewHolder(View parent, int type) {

        ViewHolder viewHolder = null;

        switch (type) {
            case 0:
                viewHolder = new PhotoViewHolder(parent);
                break;
            case 1:
                viewHolder = new LoadingViewHolder(parent);
                break;
        }

        return viewHolder;
    }
}
