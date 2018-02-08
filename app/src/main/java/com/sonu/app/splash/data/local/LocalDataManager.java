package com.sonu.app.splash.data.local;

import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface LocalDataManager {

    Observable<List<PhotoDownload>> getPhotoDownloads();
    Observable<Boolean> addPhotoDownload(PhotoDownload photoDownload);
    Observable<List<PhotoDownload>> getRunningPausedPendingDownloads();
    Observable<Boolean> updatePhotoDownload(PhotoDownload photoDownload);
}
