package com.sonu.app.splash.data.local;

import com.sonu.app.splash.data.local.room.AppDatabase;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class LocalDataManagerImpl implements LocalDataManager {

    private static final String TAG = LogUtils.getLogTag(LocalDataManagerImpl.class);

    @Inject
    AppDatabase appDatabase;

    @Inject
    public LocalDataManagerImpl() {

    }

    @Override
    public Observable<List<PhotoDownload>> getPhotoDownloads() {
        return Observable.fromCallable(new Callable<List<PhotoDownload>>() {
            @Override
            public List<PhotoDownload> call() throws Exception {

                return appDatabase.getDownloadPhotoDao().getAll();

            }
        });
    }

    @Override
    public Observable<Boolean> addPhotoDownload(final PhotoDownload photoDownload) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                try {
                    appDatabase
                            .getDownloadPhotoDao()
                            .insertAll(photoDownload);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
    }

    @Override
    public Observable<List<PhotoDownload>> getRunningPausedPendingDownloads() {
        return Observable.fromCallable(new Callable<List<PhotoDownload>>() {
            @Override
            public List<PhotoDownload> call() throws Exception {
                return appDatabase.getDownloadPhotoDao().getRunningPausedPending();
            }
        });
    }

    @Override
    public Observable<Boolean> updatePhotoDownload(final PhotoDownload photoDownload) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                try {
                    appDatabase
                            .getDownloadPhotoDao()
                            .update(photoDownload);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
    }
}
