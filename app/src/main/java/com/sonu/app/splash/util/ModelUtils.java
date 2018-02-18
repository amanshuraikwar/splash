package com.sonu.app.splash.util;

import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.User;

/**
 * Created by amanshuraikwar on 17/02/18.
 */

public class ModelUtils {

    public static Photo buildPhotoObj(FavPhoto favPhoto) {
        Photo.Builder builder = new Photo.Builder(favPhoto.getId());
        builder.createdAt(favPhoto.getCreatedAt());
        builder.updatedAt(favPhoto.getUpdatedAt());
        builder.width(favPhoto.getWidth());
        builder.height(favPhoto.getHeight());
        builder.color(favPhoto.getColor());
        builder.description(favPhoto.getDescription());
        builder.urls(favPhoto.getPhotoUrls());
        builder.links(favPhoto.getPhotoLinks());
        builder.user(buildUserObj(favPhoto.getUser()));
        return builder.build();
    }

    public static Collection buildCollectionObj(FavCollection favCollection) {
        Collection.Builder builder = new Collection.Builder(favCollection.getId());

        return builder
                .title(favCollection.getTitle())
                .description(favCollection.getDescription())
                .publishedAt(favCollection.getPublishedAt())
                .updatedAt(favCollection.getUpdatedAt())
                .curated(favCollection.isCurated())
                .featured(favCollection.isFeatured())
                .totalPhotos(favCollection.getTotalPhotos())
                .privateC(favCollection.isPrivateC())
                .shareKey(favCollection.getShareKey())
                .tags(favCollection.getTags())
                .coverPhoto(buildPhotoObj(favCollection.getCoverPhoto()))
                .previewPhotos(favCollection.getPreviewPhotos())
                .user(buildUserObj(favCollection.getUser()))
                .collectionLinks(favCollection.getCollectionLinks())
                .build();
    }

    public static User buildUserObj(FavUser favUser) {
        return new User.Builder(favUser.getId())
                .updatedAt(favUser.getUpdatedAt())
                .username(favUser.getUsername())
                .name(favUser.getName())
                .totalLikes(favUser.getTotalLikes())
                .totalPhotos(favUser.getTotalPhotos())
                .totalCollections(favUser.getTotalCollections())
                .profileImage(favUser.getProfileImage())
                .userLinks(favUser.getUserLinks())
                .build();
    }
}
