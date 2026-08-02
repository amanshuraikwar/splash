package com.sonu.app.splash.data.local.room.photodownload;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by amanshuraikwar on 06/02/18.
 */

@Dao
public interface PhotoDownloadDao {

    @Query("SELECT * FROM PhotoDownload")
    List<PhotoDownload> getAll();

    @Query("SELECT * FROM PhotoDownload WHERE downloadReference = :downloadReference LIMIT 1")
    PhotoDownload findByDownloadReference(long downloadReference);

    @Query("SELECT * FROM PhotoDownload WHERE status = 1 or status = 2 or status = 3")
    List<PhotoDownload> getRunningPausedPending();

    @Update(onConflict = REPLACE)
    void update(PhotoDownload photoDownload);

    @Insert
    void insertAll(PhotoDownload... photoDownloads);
}
