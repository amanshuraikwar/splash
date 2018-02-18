package com.sonu.app.splash.ui.list;

import android.view.View;

import com.sonu.app.splash.ui.collection.CollectionHorizontalListItem;
import com.sonu.app.splash.ui.collection.CollectionHorizontalViewHolder;
import com.sonu.app.splash.ui.collection.CollectionListItem;
import com.sonu.app.splash.ui.collection.CollectionViewHolder;
import com.sonu.app.splash.ui.download.DownloadListItem;
import com.sonu.app.splash.ui.download.DownloadViewHolder;
import com.sonu.app.splash.ui.header.HeaderHorizontalListItem;
import com.sonu.app.splash.ui.header.HeaderHorizontalViewHolder;
import com.sonu.app.splash.ui.header.HeaderListItem;
import com.sonu.app.splash.ui.header.HeaderViewHolder;
import com.sonu.app.splash.ui.loading.LoadingListItem;
import com.sonu.app.splash.ui.loading.LoadingViewHolder;
import com.sonu.app.splash.ui.photo.PhotoHorizontalViewHolder;
import com.sonu.app.splash.ui.photo.PhotoHorizontalListItem;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoViewHolder;
import com.sonu.app.splash.ui.user.UserHorizontalListItem;
import com.sonu.app.splash.ui.user.UserHorizontalViewHolder;
import com.sonu.app.splash.ui.user.UserListItem;
import com.sonu.app.splash.ui.user.UserViewHolder;

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

    public int type(HeaderHorizontalListItem item) {
        return 3;
    }

    public int type(CollectionListItem item) {
        return 4;
    }

    public int type(PhotoHorizontalListItem item) {
        return 5;
    }

    public int type(CollectionHorizontalListItem item) {
        return 6;
    }

    public int type(UserHorizontalListItem item) {
        return 7;
    }

    public int type(UserListItem item) {
        return 8;
    }

    public int type(DownloadListItem item) {
        return 9;
    }

    public int type(HeaderListItem item) {
        return 10;
    }

    public int getLayout(int viewType) {
        switch (viewType) {
            case 0:
                return PhotoViewHolder.LAYOUT;
            case 1:
                return LoadingViewHolder.LAYOUT;
            case 3:
                return HeaderHorizontalViewHolder.LAYOUT;
            case 4:
                return CollectionViewHolder.LAYOUT;
            case 5:
                return PhotoHorizontalViewHolder.LAYOUT;
            case 6:
                return CollectionHorizontalViewHolder.LAYOUT;
            case 7:
                return UserHorizontalViewHolder.LAYOUT;
            case 8:
                return UserViewHolder.LAYOUT;
            case 9:
                return DownloadViewHolder.LAYOUT;
            case 10:
                return HeaderViewHolder.LAYOUT;
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
            case 3:
                viewHolder = new HeaderHorizontalViewHolder(parent);
                break;
            case 4:
                viewHolder = new CollectionViewHolder(parent);
                break;
            case 5:
                viewHolder = new PhotoHorizontalViewHolder(parent);
                break;
            case 6:
                viewHolder = new CollectionHorizontalViewHolder(parent);
                break;
            case 7:
                viewHolder = new UserHorizontalViewHolder(parent);
                break;
            case 8:
                viewHolder = new UserViewHolder(parent);
                break;
            case 9:
                viewHolder = new DownloadViewHolder(parent);
                break;
            case 10:
                viewHolder = new HeaderViewHolder(parent);
                break;
        }

        return viewHolder;
    }
}
