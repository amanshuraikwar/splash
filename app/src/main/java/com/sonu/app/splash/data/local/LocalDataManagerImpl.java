package com.sonu.app.splash.data.local;

import com.sonu.app.splash.data.local.room.AppDatabase;
import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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

    public LocalDataManagerImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
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

    @Override
    public Observable<PhotoDownload> getPhotoDownloadByDownloadReference(long downloadReference) {
        return Observable.fromCallable(new Callable<PhotoDownload>() {
            @Override
            public PhotoDownload call() throws Exception {
                return appDatabase.getDownloadPhotoDao().findByDownloadReference(downloadReference);
            }
        });
    }

    @Override
    public Observable<Boolean> addFav(FavPhoto favPhoto) {
        return Observable.create(e -> {
            try {
                appDatabase.getFavsDao().insertAll(favPhoto);
                e.onNext(true);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<Boolean> addFav(FavCollection favCollection) {
        return Observable.create(e -> {
            try {
                appDatabase.getFavsDao().insertAll(favCollection);
                e.onNext(true);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<Boolean> addFav(FavUser favUser) {
        return Observable.create(e -> {
            try {
                appDatabase.getFavsDao().insertAll(favUser);
                e.onNext(true);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<List<FavPhoto>> getFavPhotos() {
        return Observable.create(e -> {
            try {
                e.onNext(appDatabase.getFavsDao().getAllPhotos());
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<List<FavCollection>> getFavCollections() {
        return Observable.create(e -> {
            try {
                e.onNext(appDatabase.getFavsDao().getAllCollections());
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<List<FavUser>> getFavUsers() {
        return Observable.create(e -> {
            try {
                e.onNext(appDatabase.getFavsDao().getAllUsers());
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<Boolean> removeFav(FavPhoto favPhoto) {
        return Observable.create(e -> {
            try {
                appDatabase.getFavsDao().deleteAll(favPhoto);
                e.onNext(true);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<Boolean> removeFav(FavCollection favCollection) {
        return Observable.create(e -> {
            try {
                appDatabase.getFavsDao().deleteAll(favCollection);
                e.onNext(true);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<Boolean> removeFav(FavUser favUser) {
        return Observable.create(e -> {
            try {
                appDatabase.getFavsDao().deleteAll(favUser);
                e.onNext(true);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<Boolean> isPhotoFav(String photoId) {
        return Observable.create(e -> {
            try {
                FavPhoto favPhoto = appDatabase.getFavsDao().getFavPhotoById(photoId);
                e.onNext(favPhoto != null);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<Boolean> isCollectionFav(int collectionId) {
        return Observable.create(e -> {
            try {
                FavCollection favCollection =
                        appDatabase.getFavsDao().getFavCollectionsById(collectionId);
                e.onNext(favCollection != null);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<Boolean> isUserFav(String userId) {
        return Observable.create(e -> {
            try {
                FavUser favUser =
                        appDatabase.getFavsDao().getFavUsersById(userId);
                e.onNext(favUser!= null);
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<FavPhoto> getFavPhotoById(String photoId) {
        return Observable.create(e -> {
            try {
                e.onNext(appDatabase.getFavsDao().getFavPhotoById(photoId));
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<FavCollection> getFavCollectionById(int collectionId) {
        return Observable.create(e -> {
            try {
                e.onNext(appDatabase.getFavsDao().getFavCollectionsById(collectionId));
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }

    @Override
    public Observable<FavUser> getFavUserById(String userId) {
        return Observable.create(e -> {
            try {
                e.onNext(appDatabase.getFavsDao().getFavUsersById(userId));
                e.onComplete();
            } catch (Exception ex) {
                e.tryOnError(ex);
            }
        });
    }
}
