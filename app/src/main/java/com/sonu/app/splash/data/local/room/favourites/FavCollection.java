package com.sonu.app.splash.data.local.room.favourites;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.model.unsplash.CollectionLinks;
import com.sonu.app.splash.model.unsplash.CollectionPreviewPhoto;
import com.sonu.app.splash.model.unsplash.Photo;

/**
 * Created by amanshuraikwar on 17/02/18.
 */

@Entity
public class FavCollection {

    @PrimaryKey
    private int id;

    private String title,
            description,
            publishedAt,
            updatedAt,
            likedAt;

    private boolean curated, featured;

    private int totalPhotos;

    private boolean privateC;

    private String shareKey;

    private String[] tags;

    private FavPhoto coverPhoto;

    private CollectionPreviewPhoto[] previewPhotos;

    private FavUser user;

    private CollectionLinks collectionLinks;

    public FavCollection(int id,
                         String title,
                         String description,
                         String publishedAt,
                         String updatedAt,
                         String likedAt,
                         boolean curated,
                         boolean featured,
                         int totalPhotos,
                         boolean privateC,
                         String shareKey,
                         String[] tags,
                         FavPhoto coverPhoto,
                         CollectionPreviewPhoto[] previewPhotos,
                         FavUser user,
                         CollectionLinks collectionLinks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
        this.likedAt = likedAt;
        this.curated = curated;
        this.featured = featured;
        this.totalPhotos = totalPhotos;
        this.privateC = privateC;
        this.shareKey = shareKey;
        this.tags = tags;
        this.coverPhoto = coverPhoto;
        this.previewPhotos = previewPhotos;
        this.user = user;
        this.collectionLinks = collectionLinks;
    }

    public FavCollection(Collection collection, String likedAt) {

        this.id = collection.getId();
        this.title = collection.getTitle();
        this.description = collection.getDescription();
        this.publishedAt = collection.getPublishedAt();
        this.updatedAt = collection.getUpdatedAt();
        this.likedAt = likedAt;
        this.curated = collection.isCurated();
        this.featured = collection.isFeatured();
        this.totalPhotos = collection.getTotalPhotos();
        this.privateC = collection.isPrivateC();
        this.shareKey = collection.getShareKey();
        this.tags = collection.getTags();
        this.coverPhoto = new FavPhoto(collection.getCoverPhoto(), likedAt);
        this.previewPhotos = collection.getPreviewPhotos();
        this.user = new FavUser(collection.getUser(), likedAt);
        this.collectionLinks = collection.getCollectionLinks();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getLikedAt() {
        return likedAt;
    }

    public boolean isCurated() {
        return curated;
    }

    public boolean isFeatured() {
        return featured;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public boolean isPrivateC() {
        return privateC;
    }

    public String getShareKey() {
        return shareKey;
    }

    public String[] getTags() {
        return tags;
    }

    public FavPhoto getCoverPhoto() {
        return coverPhoto;
    }

    public CollectionPreviewPhoto[] getPreviewPhotos() {
        return previewPhotos;
    }

    public FavUser getUser() {
        return user;
    }

    public CollectionLinks getCollectionLinks() {
        return collectionLinks;
    }
}
