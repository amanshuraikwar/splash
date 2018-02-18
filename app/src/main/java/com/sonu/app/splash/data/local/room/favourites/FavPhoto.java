package com.sonu.app.splash.data.local.room.favourites;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.PhotoLinks;
import com.sonu.app.splash.model.unsplash.PhotoUrls;

/**
 * Created by amanshuraikwar on 17/02/18.
 */

@Entity
public class FavPhoto {

    @PrimaryKey
    @NonNull
    private String id;

    private String likedAt, createdAt, updatedAt;

    private int width, height;

    private String color, description;

    private PhotoUrls photoUrls;

    private PhotoLinks photoLinks;

    private FavUser user;

    public FavPhoto(String id, String likedAt, String createdAt, String updatedAt, int width, int height, String color, String description, PhotoUrls photoUrls, PhotoLinks photoLinks, FavUser user) {
        this.id = id;
        this.likedAt = likedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.width = width;
        this.height = height;
        this.color = color;
        this.description = description;
        this.photoUrls = photoUrls;
        this.photoLinks = photoLinks;
        this.user = user;
    }

    public FavPhoto(Photo photo, String likedAt) {
        this.id = photo.getId();
        this.likedAt = likedAt;
        this.createdAt = photo.getCreatedAt();
        this.updatedAt = photo.getUpdatedAt();
        this.width = photo.getWidth();
        this.height = photo.getHeight();
        this.color = photo.getColor();
        this.description = photo.getDescription();
        this.photoUrls = photo.getPhotoUrls();
        this.photoLinks = photo.getPhotoLinks();
        this.user = new FavUser(photo.getUser(), likedAt);
    }

    public String getId() {
        return id;
    }

    public String getLikedAt() {
        return likedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public PhotoUrls getPhotoUrls() {
        return photoUrls;
    }

    public PhotoLinks getPhotoLinks() {
        return photoLinks;
    }

    public FavUser getUser() {
        return user;
    }
}
