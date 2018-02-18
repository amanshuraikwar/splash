package com.sonu.app.splash.data.local.room.favourites;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.sonu.app.splash.model.unsplash.ProfileImage;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.model.unsplash.UserLinks;

/**
 * Created by amanshuraikwar on 17/02/18.
 */

@Entity
public class FavUser {

    @PrimaryKey
    @NonNull
    private String id;

    private String updatedAt, likedAt;

    private String username, name;

    private int totalLikes,
            totalPhotos,
            totalCollections;

    private ProfileImage profileImage;

    private UserLinks userLinks;

    public FavUser(String id,
                   String updatedAt,
                   String likedAt,
                   String username,
                   String name,
                   int totalLikes,
                   int totalPhotos,
                   int totalCollections,
                   ProfileImage profileImage,
                   UserLinks userLinks) {

        this.id = id;
        this.updatedAt = updatedAt;
        this.likedAt = likedAt;
        this.username = username;
        this.name = name;
        this.totalLikes = totalLikes;
        this.totalPhotos = totalPhotos;
        this.totalCollections = totalCollections;
        this.profileImage = profileImage;
        this.userLinks = userLinks;
    }

    public FavUser(User user, String likedAt) {
        this.id = user.getId();
        this.updatedAt = user.getUpdatedAt();
        this.likedAt = likedAt;
        this.username = user.getUsername();
        this.name = user.getName();
        this.totalLikes = user.getTotalLikes();
        this.totalPhotos = user.getTotalPhotos();
        this.totalCollections = user.getTotalCollections();
        this.profileImage = user.getProfileImage();
        this.userLinks = user.getUserLinks();
    }

    public String getId() {
        return id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getLikedAt() {
        return likedAt;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public int getTotalCollections() {
        return totalCollections;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public UserLinks getUserLinks() {
        return userLinks;
    }
}
