package com.sonu.app.splash.data.local;

import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;

import java.util.List;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface LocalStore {

    // downloads
    List<PhotoDownload> getPhotoDownloads();
    boolean addPhotoDownload(PhotoDownload photoDownload);
    List<PhotoDownload> getRunningPausedPendingDownloads();
    boolean updatePhotoDownload(PhotoDownload photoDownload);
    PhotoDownload getPhotoDownloadByDownloadReference(long downloadReference);

    // favourites
    boolean addFav(FavPhoto favPhoto);
    boolean addFav(FavCollection favCollection);
    boolean addFav(FavUser favUser);
    List<FavPhoto> getFavPhotos();
    List<FavCollection> getFavCollections();
    List<FavUser> getFavUsers();
    boolean removeFav(FavPhoto favPhoto);
    boolean removeFav(FavCollection favCollection);
    boolean removeFav(FavUser favUser);
    boolean isPhotoFav(String photoId);
    boolean isCollectionFav(String collectionId);
    boolean isUserFav(String userId);
    FavPhoto getFavPhotoById(String photoId);
    FavCollection getFavCollectionById(String collectionId);
    FavUser getFavUserById(String userId);
}
