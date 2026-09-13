package com.sonu.app.splash.data.local;

import com.sonu.app.splash.data.local.room.AppDatabase;
import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;

import java.util.List;

import javax.inject.Inject;

/** Room-backed local data source. Callers run these operations from a coroutine dispatcher. */
public class LocalStoreImpl implements LocalStore {

    @Inject
    AppDatabase appDatabase;

    @Inject
    public LocalStoreImpl() {
    }

    public LocalStoreImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public List<PhotoDownload> getPhotoDownloads() {
        return appDatabase.getDownloadPhotoDao().getAll();
    }

    @Override
    public boolean addPhotoDownload(PhotoDownload photoDownload) {
        try {
            appDatabase.getDownloadPhotoDao().insertAll(photoDownload);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public List<PhotoDownload> getRunningPausedPendingDownloads() {
        return appDatabase.getDownloadPhotoDao().getRunningPausedPending();
    }

    @Override
    public boolean updatePhotoDownload(PhotoDownload photoDownload) {
        try {
            appDatabase.getDownloadPhotoDao().update(photoDownload);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public PhotoDownload getPhotoDownloadByDownloadReference(long downloadReference) {
        return appDatabase.getDownloadPhotoDao().findByDownloadReference(downloadReference);
    }

    @Override
    public boolean addFav(FavPhoto favPhoto) {
        try {
            appDatabase.getFavsDao().insertAll(favPhoto);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean addFav(FavCollection favCollection) {
        try {
            appDatabase.getFavsDao().insertAll(favCollection);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean addFav(FavUser favUser) {
        try {
            appDatabase.getFavsDao().insertAll(favUser);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public List<FavPhoto> getFavPhotos() {
        return appDatabase.getFavsDao().getAllPhotos();
    }

    @Override
    public List<FavCollection> getFavCollections() {
        return appDatabase.getFavsDao().getAllCollections();
    }

    @Override
    public List<FavUser> getFavUsers() {
        return appDatabase.getFavsDao().getAllUsers();
    }

    @Override
    public boolean removeFav(FavPhoto favPhoto) {
        try {
            appDatabase.getFavsDao().deleteAll(favPhoto);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean removeFav(FavCollection favCollection) {
        try {
            appDatabase.getFavsDao().deleteAll(favCollection);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean removeFav(FavUser favUser) {
        try {
            appDatabase.getFavsDao().deleteAll(favUser);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean isPhotoFav(String photoId) {
        return appDatabase.getFavsDao().getFavPhotoById(photoId) != null;
    }

    @Override
    public boolean isCollectionFav(String collectionId) {
        return appDatabase.getFavsDao().getFavCollectionsById(collectionId) != null;
    }

    @Override
    public boolean isUserFav(String userId) {
        return appDatabase.getFavsDao().getFavUsersById(userId) != null;
    }

    @Override
    public FavPhoto getFavPhotoById(String photoId) {
        return appDatabase.getFavsDao().getFavPhotoById(photoId);
    }

    @Override
    public FavCollection getFavCollectionById(String collectionId) {
        return appDatabase.getFavsDao().getFavCollectionsById(collectionId);
    }

    @Override
    public FavUser getFavUserById(String userId) {
        return appDatabase.getFavsDao().getFavUsersById(userId);
    }
}
