package com.sonu.app.splash.data.download;

import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public interface Downloader {

    long downloadPhoto(Photo photo);
    PhotoDownload.Status checkDownloadStatus(long downloadReference);
}
