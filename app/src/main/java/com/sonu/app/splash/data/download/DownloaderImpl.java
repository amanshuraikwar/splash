package com.sonu.app.splash.data.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.sonu.app.splash.di.ApplicationContext;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.PhotoUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class DownloaderImpl implements Downloader {

    @Inject
    DownloadManager downloadManager;

    @Inject
    public DownloaderImpl() {
    }

    @Override
    public long downloadPhoto(Photo photo) {

        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(photo.getPhotoLinks().getDownload()));

        request.setTitle("Downloading photo");
        request.setDescription(PhotoUtils.getDownloadFileName(photo));

        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                PhotoUtils.getDownloadFileName(photo));

        return downloadManager.enqueue(request);
    }

    @Override
    public PhotoDownload.Status checkDownloadStatus(long downloadReference) {
        DownloadManager.Query query = new DownloadManager.Query();

        //set the query filter to our previously Enqueued download
        query.setFilterById(downloadReference);

        //Query the download manager about downloads that have been requested.
        Cursor cursor = downloadManager.query(query);
        if(cursor.moveToFirst()){

            return getDownloadStatus(cursor);
        }

        return PhotoDownload.Status.FAILED;
    }

    private PhotoDownload.Status getDownloadStatus(Cursor cursor) {

        //column for download  status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);

        switch(status){

            case DownloadManager.STATUS_FAILED:

                switch(reason){
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        break;
                }

                return PhotoDownload.Status.FAILED;

            case DownloadManager.STATUS_PAUSED:

                switch(reason){
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        break;
                }

                return PhotoDownload.Status.PAUSED;

            case DownloadManager.STATUS_PENDING:

                return PhotoDownload.Status.PENDING;

            case DownloadManager.STATUS_RUNNING:

                return PhotoDownload.Status.RUNNING;

            case DownloadManager.STATUS_SUCCESSFUL:

                return PhotoDownload.Status.SUCCESSFUL;
        }

        return PhotoDownload.Status.FAILED;
    }
}
