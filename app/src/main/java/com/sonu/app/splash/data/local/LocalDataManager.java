package com.sonu.app.splash.data.local;

import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface LocalDataManager {

    // downloads
    Observable<List<PhotoDownload>> getPhotoDownloads();
    Observable<Boolean> addPhotoDownload(PhotoDownload photoDownload);
    Observable<List<PhotoDownload>> getRunningPausedPendingDownloads();
    Observable<Boolean> updatePhotoDownload(PhotoDownload photoDownload);
    Observable<PhotoDownload> getPhotoDownloadByDownloadReference(long downloadReference);

    // favourites
    Observable<Boolean> addFav(FavPhoto favPhoto);
    Observable<Boolean> addFav(FavCollection favCollection);
    Observable<Boolean> addFav(FavUser favUser);
    Observable<List<FavPhoto>> getFavPhotos();
    Observable<List<FavCollection>> getFavCollections();
    Observable<List<FavUser>> getFavUsers();
    Observable<Boolean> removeFav(FavPhoto favPhoto);
    Observable<Boolean> removeFav(FavCollection favCollection);
    Observable<Boolean> removeFav(FavUser favUser);
    Observable<Boolean> isPhotoFav(String photoId);
    Observable<Boolean> isCollectionFav(int collectionId);
    Observable<Boolean> isUserFav(String userId);
    Observable<FavPhoto> getFavPhotoById(String photoId);
    Observable<FavCollection> getFavCollectionById(int collectionId);
    Observable<FavUser> getFavUserById(String userId);
}
