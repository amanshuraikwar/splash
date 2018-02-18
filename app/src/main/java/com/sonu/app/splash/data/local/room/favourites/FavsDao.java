package com.sonu.app.splash.data.local.room.favourites;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;

import java.util.List;

/**
 * Created by amanshuraikwar on 17/02/18.
 */
@Dao
public interface FavsDao {

    @Query("SELECT * FROM FavPhoto")
    List<FavPhoto> getAllPhotos();

    @Query("SELECT * FROM FavCollection")
    List<FavCollection> getAllCollections();

    @Query("SELECT * FROM FavUser")
    List<FavUser> getAllUsers();

    @Insert
    void insertAll(FavPhoto... favPhotos);

    @Insert
    void insertAll(FavCollection... favCollections);

    @Insert
    void insertAll(FavUser... favUsers);

    @Delete
    void deleteAll(FavPhoto... favPhotos);

    @Delete
    void deleteAll(FavCollection... favCollections);

    @Delete
    void deleteAll(FavUser... favUsers);

    @Query("SELECT * from FavPhoto where id = :photoId")
    FavPhoto getFavPhotoById(String photoId);

    @Query("SELECT * from FavCollection where id = :collectionId")
    FavCollection getFavCollectionsById(int collectionId);

    @Query("SELECT * from FavUser where id = :userId")
    FavUser getFavUsersById(String userId);
}
