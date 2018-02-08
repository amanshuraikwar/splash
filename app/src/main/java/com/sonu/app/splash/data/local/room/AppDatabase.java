package com.sonu.app.splash.data.local.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by amanshuraikwar on 06/02/18.
 */

@Database(entities = {PhotoDownload.class}, version = 5)
@TypeConverters({com.sonu.app.splash.data.local.room.TypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract PhotoDownloadDao getDownloadPhotoDao();
}
