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
import com.sonu.app.splash.data.local.LocalStore;
import com.sonu.app.splash.data.media.CollectionPageLoader;
import com.sonu.app.splash.data.media.CreatorPageLoader;
import com.sonu.app.splash.data.media.MediaPageLoader;
import com.sonu.app.splash.di.ApplicationContext;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.PhotoStats;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.legacy.LegacyUiModelMapper;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class AppDataStoreImpl implements AppDataStore {

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
    MediaPageLoader mediaPageLoader;

    @Inject
    CollectionPageLoader collectionPageLoader;

    @Inject
    CreatorPageLoader creatorPageLoader;

    @Inject
    Downloader downloader;

    @Inject
    LocalStore localStore;

    @Inject
    public AppDataStoreImpl(){
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
        return new UserPhotosCache(mediaPageLoader, username);
    }

    @Override
    public UserCollectionsCache getUserCollectionsCache(String username) {
        return new UserCollectionsCache(collectionPageLoader, username);
    }

    @Override
    public CollectionPhotosCache getCollectionPhotosCache(String id) {
        return new CollectionPhotosCache(mediaPageLoader, id);
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
    public Photo getPhotoDescription(String photoId) {
        return LegacyUiModelMapper.toPhoto(mediaPageLoader.loadById(photoId));
    }

    @Override
    public User getUserDescription(String username) {
        return LegacyUiModelMapper.toUser(creatorPageLoader.loadByUsername(username));
    }

    @Override
    public PhotoStats getPhotoStats(String photoId) {
        return LegacyUiModelMapper.toPhotoStats(mediaPageLoader.loadStatistics(photoId));
    }

    @Override
    public List<PhotoDownload> getPhotoDownloads() {
        return localStore.getPhotoDownloads();
    }

    @Override
    public boolean addPhotoDownload(PhotoDownload photoDownload) {
        return localStore.addPhotoDownload(photoDownload);
    }

    @Override
    public List<PhotoDownload> getRunningPausedPendingDownloads() {
        return localStore.getRunningPausedPendingDownloads();
    }

    @Override
    public boolean updatePhotoDownload(PhotoDownload photoDownload) {
        return localStore.updatePhotoDownload(photoDownload);
    }

    @Override
    public PhotoDownload getPhotoDownloadByDownloadReference(long downloadReference) {
        return localStore.getPhotoDownloadByDownloadReference(downloadReference);
    }

    @Override
    public boolean addFav(FavPhoto favPhoto) {
        return localStore.addFav(favPhoto);
    }

    @Override
    public boolean addFav(FavCollection favCollection) {
        return localStore.addFav(favCollection);
    }

    @Override
    public boolean addFav(FavUser favUser) {
        return localStore.addFav(favUser);
    }

    @Override
    public List<FavPhoto> getFavPhotos() {
        return localStore.getFavPhotos();
    }

    @Override
    public List<FavCollection> getFavCollections() {
        return localStore.getFavCollections();
    }

    @Override
    public List<FavUser> getFavUsers() {
        return localStore.getFavUsers();
    }

    @Override
    public boolean removeFav(FavPhoto favPhoto) {
        return localStore.removeFav(favPhoto);
    }

    @Override
    public boolean removeFav(FavCollection favCollection) {
        return localStore.removeFav(favCollection);
    }

    @Override
    public boolean removeFav(FavUser favUser) {
        return localStore.removeFav(favUser);
    }

    @Override
    public boolean isPhotoFav(String photoId) {
        return localStore.isPhotoFav(photoId);
    }

    @Override
    public boolean isCollectionFav(String collectionId) {
        return localStore.isCollectionFav(collectionId);
    }

    @Override
    public boolean isUserFav(String userId) {
        return localStore.isUserFav(userId);
    }

    @Override
    public FavPhoto getFavPhotoById(String photoId) {
        return localStore.getFavPhotoById(photoId);
    }

    @Override
    public FavCollection getFavCollectionById(String collectionId) {
        return localStore.getFavCollectionById(collectionId);
    }

    @Override
    public FavUser getFavUserById(String userId) {
        return localStore.getFavUserById(userId);
    }
}
