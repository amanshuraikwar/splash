package com.sonu.app.splash.data;

import android.content.Context;

import com.sonu.app.splash.data.cache.AllCollectionsCache;
import com.sonu.app.splash.data.cache.AllPhotosCache;
import com.sonu.app.splash.data.cache.CollectionPhotosCache;
import com.sonu.app.splash.data.cache.CuratedPhotosCache;
import com.sonu.app.splash.data.cache.FeaturedCollectionsCache;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.cache.UserPhotosCache;
import com.sonu.app.splash.data.download.DownloadSession;
import com.sonu.app.splash.data.download.Downloader;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.data.local.LocalDataManager;
import com.sonu.app.splash.data.network.NetworkDataManager;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.di.ApplicationContext;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class DataManagerImpl implements DataManager {

    @Inject
    @ApplicationContext
    Context applicationContext;

    @Inject
    DownloadSession downloadSession;

    @Inject
    AllCollectionsCache allCollectionsCache;

    @Inject
    FeaturedCollectionsCache featuredCollectionsCache;

    @Inject
    AllPhotosCache allPhotosCache;

    @Inject
    CuratedPhotosCache curatedPhotosCache;

    @Inject
    SearchCollectionsCache searchCollectionsCache;

    @Inject
    SearchPhotosCache searchPhotosCache;

    @Inject
    SearchUsersCache searchUsersCache;

    @Inject
    NetworkDataManager networkDataManager;

    @Inject
    RequestHandler requestHandler;

    @Inject
    Downloader downloader;

    @Inject
    LocalDataManager localDataManager;

    @Inject
    public DataManagerImpl(){
    }

    @Override
    public AllPhotosCache getAllPhotosCache() {
        return allPhotosCache;
    }

    @Override
    public CuratedPhotosCache getCuratedPhotoCache() {
        return curatedPhotosCache;
    }

    @Override
    public AllCollectionsCache getAllCollectionsCache() {
        return allCollectionsCache;
    }

    @Override
    public FeaturedCollectionsCache getFeaturedCollectionsCache() {
        return featuredCollectionsCache;
    }

    @Override
    public SearchCollectionsCache getSearchCollectionsCache() {
        return searchCollectionsCache;
    }

    @Override
    public SearchPhotosCache getSearchPhotosCache() {
        return searchPhotosCache;
    }

    @Override
    public SearchUsersCache getSearchUsersCache() {
        return searchUsersCache;
    }

    @Override
    public UserPhotosCache getUserPhotosCache(String username) {
        return new UserPhotosCache(requestHandler, username);
    }

    @Override
    public CollectionPhotosCache getCollectionPhotosCache(String id) {
        return new CollectionPhotosCache(requestHandler, id);
    }

    @Override
    public DownloadSession getDownloadSession() {
        return downloadSession;
    }

    @Override
    public long downloadPhoto(Photo photo) {
        return downloader.downloadPhoto(photo);
    }

    @Override
    public PhotoDownload.Status checkDownloadStatus(long downloadReference) {
        return downloader.checkDownloadStatus(downloadReference);
    }

    @Override
    public Observable<Photo> getPhotoDescription(String photoId) {
        return networkDataManager.getPhotoDescription(photoId);
    }

    @Override
    public Observable<User> getUserDescription(String username) {
        return networkDataManager.getUserDescription(username);
    }

    @Override
    public Observable<List<PhotoDownload>> getPhotoDownloads() {
        return localDataManager.getPhotoDownloads();
    }

    @Override
    public Observable<Boolean> addPhotoDownload(PhotoDownload photoDownload) {
        return localDataManager.addPhotoDownload(photoDownload);
    }

    @Override
    public Observable<List<PhotoDownload>> getRunningPausedPendingDownloads() {
        return localDataManager.getRunningPausedPendingDownloads();
    }

    @Override
    public Observable<Boolean> updatePhotoDownload(PhotoDownload photoDownload) {
        return localDataManager.updatePhotoDownload(photoDownload);
    }
}
