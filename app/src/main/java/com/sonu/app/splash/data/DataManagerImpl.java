package com.sonu.app.splash.data;

import android.content.Context;

import com.sonu.app.splash.data.cache.AllCollectionsCache;
import com.sonu.app.splash.data.cache.AllPhotosCache;
import com.sonu.app.splash.data.cache.CollectionPhotosCache;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.cache.CuratedPhotosCache;
import com.sonu.app.splash.data.cache.FeaturedCollectionsCache;
import com.sonu.app.splash.data.cache.PhotosCache;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.cache.UserPhotosCache;
import com.sonu.app.splash.data.download.DownloadSession;
import com.sonu.app.splash.data.download.Downloader;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.data.network.NetworkDataManager;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.di.ApplicationContext;
import com.sonu.app.splash.ui.content.featuredcollections.FeaturedCollectionsContract;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photodescription.PhotoDescription;
import com.sonu.app.splash.ui.userdescription.UserDescription;

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
    public void downloadPhoto(PhotoDownload photoDownload) {
        downloader.downloadPhoto(photoDownload);
    }

    @Override
    public Observable<PhotoDescription> getPhotoDescription(String photoId) {
        return networkDataManager.getPhotoDescription(photoId);
    }

    @Override
    public Observable<UserDescription> getUserDescription(String username) {
        return networkDataManager.getUserDescription(username);
    }
}
