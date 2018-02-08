package com.sonu.app.splash.data;

import android.app.DownloadManager;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.sonu.app.splash.data.cache.PhotosCache;
import com.sonu.app.splash.data.download.Downloader;
import com.sonu.app.splash.data.download.DownloaderImpl;
import com.sonu.app.splash.data.local.LocalDataManager;
import com.sonu.app.splash.data.local.LocalDataManagerImpl;
import com.sonu.app.splash.data.local.room.AppDatabase;
import com.sonu.app.splash.data.network.NetworkDataManager;
import com.sonu.app.splash.data.network.NetworkDataManagerImpl;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

@Module
public abstract class DataManagerModule {

    /**
     * this saves us from defining a separate method returning DataManagerImpl object
     */
    @Singleton
    @Binds
    abstract DataManager getDataManager(DataManagerImpl impl);

    @Singleton
    @Binds
    abstract NetworkDataManager getNetworkDataManager(NetworkDataManagerImpl impl);

    @Singleton
    @Binds
    abstract LocalDataManager getLocalDataManager(LocalDataManagerImpl impl);

    @Singleton
    @Binds
    abstract Downloader getDownloader(DownloaderImpl impl);

    @Singleton
    @Provides
    public static OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    public static DownloadManager getDownloadManager(@ApplicationContext Context context) {
        return (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
    }

    @Singleton
    @Provides
    public static AppDatabase getAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "splash-database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
