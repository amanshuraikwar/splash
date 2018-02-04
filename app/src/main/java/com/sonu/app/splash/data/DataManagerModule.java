package com.sonu.app.splash.data;

import android.app.DownloadManager;
import android.content.Context;

import com.sonu.app.splash.data.cache.PhotosCache;
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
    @Provides
    public static OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    public static DownloadManager getDownloadManager(@ApplicationContext Context context) {
        return (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
    }
}
