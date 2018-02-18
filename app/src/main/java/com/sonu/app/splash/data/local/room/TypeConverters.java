package com.sonu.app.splash.data.local.room;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.model.unsplash.CollectionLinks;
import com.sonu.app.splash.model.unsplash.CollectionPreviewPhoto;
import com.sonu.app.splash.model.unsplash.PhotoLinks;
import com.sonu.app.splash.model.unsplash.PhotoUrls;
import com.sonu.app.splash.model.unsplash.ProfileImage;
import com.sonu.app.splash.model.unsplash.UserLinks;

import java.lang.reflect.Type;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class TypeConverters {

    @TypeConverter
    public static PhotoDownload.Status toStatus(int ordinal) {
        return PhotoDownload.Status.values()[ordinal];
    }

    @TypeConverter
    public static int toOrdinal(PhotoDownload.Status status) {
        return status.ordinal();
    }

    @TypeConverter
    public static PhotoUrls photoUrlsfromString(String value) {
        Type type = new TypeToken<PhotoUrls>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromPhotoUrls(PhotoUrls photoUrls) {
        Gson gson = new Gson();
        String json = gson.toJson(photoUrls);
        return json;
    }

    @TypeConverter
    public static PhotoLinks photoLinksFromString(String value) {
        Type type = new TypeToken<PhotoLinks>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromPhotoLinks(PhotoLinks photoLinks) {
        Gson gson = new Gson();
        String json = gson.toJson(photoLinks);
        return json;
    }

    @TypeConverter
    public static ProfileImage profileImageFromString(String value) {
        Type type = new TypeToken<ProfileImage>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromProfileImage(ProfileImage profileImage) {
        Gson gson = new Gson();
        String json = gson.toJson(profileImage);
        return json;
    }

    @TypeConverter
    public static UserLinks userLinksFromString(String value) {
        Type type = new TypeToken<UserLinks>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromUserLinks(UserLinks userLinks) {
        Gson gson = new Gson();
        String json = gson.toJson(userLinks);
        return json;
    }

    @TypeConverter
    public static String[] stringArrFromString(String value) {
        Type type = new TypeToken<String[]>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromStringArr(String[] arr) {
        Gson gson = new Gson();
        String json = gson.toJson(arr);
        return json;
    }

    @TypeConverter
    public static FavPhoto favPhotoFromString(String value) {
        Type type = new TypeToken<FavPhoto>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromFavPhoto(FavPhoto photo) {
        Gson gson = new Gson();
        String json = gson.toJson(photo);
        return json;
    }

    @TypeConverter
    public static CollectionPreviewPhoto[] collectionPreviewPhotosFromString(String value) {
        Type type = new TypeToken<CollectionPreviewPhoto[]>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromCollectionPreviewPhotos(CollectionPreviewPhoto[] photos) {
        Gson gson = new Gson();
        String json = gson.toJson(photos);
        return json;
    }

    @TypeConverter
    public static FavUser favUserFromString(String value) {
        Type type = new TypeToken<FavUser>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromFavUser(FavUser user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        return json;
    }

    @TypeConverter
    public static CollectionLinks collectionLinksFromString(String value) {
        Type type = new TypeToken<CollectionLinks>() {}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String fromCollectionLinks(CollectionLinks collectionLinks) {
        Gson gson = new Gson();
        String json = gson.toJson(collectionLinks);
        return json;
    }
}
