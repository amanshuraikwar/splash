package com.sonu.app.splash.data;

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
import com.sonu.app.splash.data.local.LocalDataManager;
import com.sonu.app.splash.data.network.NetworkDataManager;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface DataManager extends LocalDataManager, NetworkDataManager, Downloader {

    AllPhotosCache getAllPhotosCache();

    CuratedPhotosCache getCuratedPhotoCache();

    AllCollectionsCache getAllCollectionsCache();

    FeaturedCollectionsCache getFeaturedCollectionsCache();

    SearchCollectionsCache getSearchCollectionsCache();

    SearchPhotosCache getSearchPhotosCache();

    SearchUsersCache getSearchUsersCache();

    UserPhotosCache getUserPhotosCache(String username);

    UserCollectionsCache getUserCollectionsCache(String username);

    CollectionPhotosCache getCollectionPhotosCache(String id);

    DownloadSession getDownloadSession();
}
