package com.sonu.app.splash.ui.download;

import android.view.View;

import com.sonu.app.splash.data.local.room.PhotoDownload;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public interface DownloadOnClickListener {

    void onPhotoClick(PhotoDownload photoDownload, View transitionView);
}
