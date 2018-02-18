package com.sonu.app.splash.data;

import android.content.Context;
import android.net.Uri;

import com.sonu.app.splash.data.cache.AllCollectionsCache;
import com.sonu.app.splash.data.cache.AllPhotosCache;
import com.sonu.app.splash.data.cache.CollectionPhotosCache;
import com.sonu.app.splash.data.cache.CuratedPhotosCache;
import com.sonu.app.splash.data.cache.FeaturedCollectionsCache;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.cache.UserCollectionsCache;
import com.sonu.app.splash.data.cache.UserPhotosCache;
import com.sonu.app.splash.data.download.DownloadSession;
import com.sonu.app.splash.data.download.Downloader;
import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.data.local.LocalDataManager;
import com.sonu.app.splash.data.network.NetworkDataManager;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.di.ApplicationContext;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.PhotoStats;
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
    public UserCollectionsCache getUserCollectionsCache(String username) {
        return new UserCollectionsCache(requestHandler, username);
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
    public Uri getDownloadedFilePath(long downloadReference) {
        return downloader.getDownloadedFilePath(downloadReference);
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
    public Observable<PhotoStats> getPhotoStats(String photoId) {
        return networkDataManager.getPhotoStats(photoId);
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

    @Override
    public Observable<PhotoDownload> getPhotoDownloadByDownloadReference(long downloadReference) {
        return localDataManager.getPhotoDownloadByDownloadReference(downloadReference);
    }

    @Override
    public Observable<Boolean> addFav(FavPhoto favPhoto) {
        return localDataManager.addFav(favPhoto);
    }

    @Override
    public Observable<Boolean> addFav(FavCollection favCollection) {
        return localDataManager.addFav(favCollection);
    }

    @Override
    public Observable<Boolean> addFav(FavUser favUser) {
        return localDataManager.addFav(favUser);
    }

    @Override
    public Observable<List<FavPhoto>> getFavPhotos() {
        return localDataManager.getFavPhotos();
    }

    @Override
    public Observable<List<FavCollection>> getFavCollections() {
        return localDataManager.getFavCollections();
    }

    @Override
    public Observable<List<FavUser>> getFavUsers() {
        return localDataManager.getFavUsers();
    }

    @Override
    public Observable<Boolean> removeFav(FavPhoto favPhoto) {
        return localDataManager.removeFav(favPhoto);
    }

    @Override
    public Observable<Boolean> removeFav(FavCollection favCollection) {
        return localDataManager.removeFav(favCollection);
    }

    @Override
    public Observable<Boolean> removeFav(FavUser favUser) {
        return localDataManager.removeFav(favUser);
    }

    @Override
    public Observable<Boolean> isPhotoFav(String photoId) {
        return localDataManager.isPhotoFav(photoId);
    }

    @Override
    public Observable<Boolean> isCollectionFav(int collectionId) {
        return localDataManager.isCollectionFav(collectionId);
    }

    @Override
    public Observable<Boolean> isUserFav(String userId) {
        return localDataManager.isUserFav(userId);
    }

    @Override
    public Observable<FavPhoto> getFavPhotoById(String photoId) {
        return localDataManager.getFavPhotoById(photoId);
    }

    @Override
    public Observable<FavCollection> getFavCollectionById(int collectionId) {
        return localDataManager.getFavCollectionById(collectionId);
    }

    @Override
    public Observable<FavUser> getFavUserById(String userId) {
        return localDataManager.getFavUserById(userId);
    }
}
