package com.sonu.app.splash.data;

import android.app.DownloadManager;
import androidx.room.Room;
import android.content.Context;

import com.sonu.app.splash.data.download.Downloader;
import com.sonu.app.splash.data.download.DownloaderImpl;
import com.sonu.app.splash.data.local.LocalStore;
import com.sonu.app.splash.data.local.LocalStoreImpl;
import com.sonu.app.splash.data.local.room.AppDatabase;
import com.sonu.app.splash.data.media.MediaDataSource;
import com.sonu.app.splash.data.media.CollectionDataSource;
import com.sonu.app.splash.data.media.CreatorDataSource;
import com.sonu.app.splash.data.media.unsplash.UnsplashApi;
import com.sonu.app.splash.data.media.unsplash.UnsplashCollectionDataSource;
import com.sonu.app.splash.data.media.unsplash.UnsplashCreatorDataSource;
import com.sonu.app.splash.data.media.unsplash.UnsplashMediaDataSource;
import com.sonu.app.splash.data.rss.RssDataSource;
import com.sonu.app.splash.data.rss.github.GitHubRssDataSource;
import com.sonu.app.splash.data.rss.github.RssApi;
import com.sonu.app.splash.BuildConfig;
import com.sonu.app.splash.di.ApplicationContext;

import javax.inject.Singleton;
import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

@Module
public abstract class AppDataStoreModule {

    /**
     * this saves us from defining a separate method returning AppDataStoreImpl object
     */
    @Singleton
    @Binds
    abstract AppDataStore getAppDataStore(AppDataStoreImpl impl);

    @IntoSet
    @Binds
    abstract MediaDataSource getMediaDataSource(UnsplashMediaDataSource impl);

    @IntoSet
    @Binds
    abstract CollectionDataSource getCollectionDataSource(UnsplashCollectionDataSource impl);

    @IntoSet
    @Binds
    abstract CreatorDataSource getCreatorDataSource(UnsplashCreatorDataSource impl);

    @IntoSet
    @Binds
    abstract RssDataSource getRssDataSource(GitHubRssDataSource impl);

    @Singleton
    @Binds
    abstract LocalStore getLocalStore(LocalStoreImpl impl);

    @Singleton
    @Binds
    abstract Downloader getDownloader(DownloaderImpl impl);

    @Singleton
    @Provides
    public static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request.Builder requestBuilder = chain.request().newBuilder()
                            .header("Accept", "application/json")
                            .header("Accept-Version", "v1");

                    String accessKey = BuildConfig.UNSPLASH_ACCESS_KEY.trim();
                    if (!accessKey.isEmpty()) {
                        requestBuilder.header("Authorization", "Client-ID " + accessKey);
                    }

                    return chain.proceed(requestBuilder.build());
                })
                .build();
    }

    @Singleton
    @Provides
    public static Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public static UnsplashApi getUnsplashApi(Retrofit retrofit) {
        return retrofit.create(UnsplashApi.class);
    }

    @Singleton
    @Provides
    @Named("rss")
    public static Retrofit getRssRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://amanshuraikwar.github.io/rss/")
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    public static RssApi getRssApi(@Named("rss") Retrofit retrofit) {
        return retrofit.create(RssApi.class);
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
