package com.sonu.app.splash.data.local.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.data.local.room.favourites.FavsDao;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownloadDao;

/**
 * Created by amanshuraikwar on 06/02/18.
 */

@Database(entities = {
        PhotoDownload.class,
        FavPhoto.class, FavCollection.class, FavUser.class}, version = 9)
@TypeConverters({com.sonu.app.splash.data.local.room.TypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract PhotoDownloadDao getDownloadPhotoDao();
    public abstract FavsDao getFavsDao();
}
