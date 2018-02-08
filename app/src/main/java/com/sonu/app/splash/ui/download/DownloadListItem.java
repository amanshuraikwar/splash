package com.sonu.app.splash.ui.download;

import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class DownloadListItem extends ListItem<DownloadOnClickListener> {

    private PhotoDownload photoDownload;

    public DownloadListItem(PhotoDownload photoDownload) {
        this.photoDownload = photoDownload;
    }

    public PhotoDownload getPhotoDownload() {
        return photoDownload;
    }

    @Override
    public int type(ListItemTypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
