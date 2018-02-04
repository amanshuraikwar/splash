package com.sonu.app.splash.data.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.LongSparseArray;

import com.sonu.app.splash.di.ApplicationContext;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class Downloader {

    private DownloadManager downloadManager;
    LongSparseArray<PhotoDownload> queuedDownloads;

    @Inject
    public Downloader(@ApplicationContext Context applicationContext) {

        downloadManager =
                (DownloadManager) applicationContext.getSystemService(Context.DOWNLOAD_SERVICE);

        queuedDownloads = new LongSparseArray<>();
    }

    public void downloadPhoto(PhotoDownload photoDownload) {

        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(photoDownload.getDownloadUrl()));

        request.setTitle("Downloading photo");
        request.setDescription(photoDownload.getDownloadedFileName());

        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                photoDownload.getDownloadedFileName());

        long downloadReference = downloadManager.enqueue(request);
        queuedDownloads.append(downloadReference, photoDownload);
    }
}
