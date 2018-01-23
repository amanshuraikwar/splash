package com.sonu.app.splash.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photodescription.PhotoDescription;
import com.sonu.app.splash.ui.userdescription.UserDescription;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

public class UnsplashJsonUtils {

    public static Photo getPhotoObj(JsonElement element) {

        Photo.Builder builder =
                new Photo.Builder(element.getAsJsonObject().get("id").getAsString());

        builder.createdAt(element.getAsJsonObject().get("created_at").getAsString());
        builder.updatedAt(element.getAsJsonObject().get("updated_at").getAsString());

        builder.width(element.getAsJsonObject().get("width").getAsInt());
        builder.height(element.getAsJsonObject().get("height").getAsInt());

        builder.color(element.getAsJsonObject().get("color").getAsString());
        builder.likes(element.getAsJsonObject().get("likes").getAsInt());

        try {
            builder.description(element.getAsJsonObject().get("description").getAsString());
        } catch (Exception e) {
            builder.description("- photo on Unsplash");
        }

        builder.artistId(
                element
                        .getAsJsonObject()
                        .get("user")
                        .getAsJsonObject().get("id").getAsString()
        );

        builder.artistName(
                element
                        .getAsJsonObject()
                        .get("user")
                        .getAsJsonObject().get("name").getAsString()
        );

        builder.artistUsername(
                element
                        .getAsJsonObject()
                        .get("user")
                        .getAsJsonObject().get("username").getAsString()
        );

        builder.artistProfileImageUrl(
                element
                        .getAsJsonObject()
                        .get("user")
                        .getAsJsonObject()
                        .get("profile_image")
                        .getAsJsonObject()
                        .get("large").getAsString()
        );

        builder.urlRaw(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("raw").getAsString());

        builder.urlFull(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("full").getAsString());

        builder.urlRegular(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("regular").getAsString());

        builder.urlSmall(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("small").getAsString());

        builder.urlThumb(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("thumb").getAsString());

        builder.urlThumb(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject()
                        .get("thumb")
                        .getAsString());

        builder.linkSelf(
                element
                        .getAsJsonObject()
                        .get("links")
                        .getAsJsonObject().get("self").getAsString());

        builder.linkHtml(
                element
                        .getAsJsonObject()
                        .get("links")
                        .getAsJsonObject().get("html").getAsString());

        builder.linkDownload(
                element
                        .getAsJsonObject()
                        .get("links")
                        .getAsJsonObject().get("download").getAsString());

        builder.linkDownloadLocation(
                element
                        .getAsJsonObject()
                        .get("links")
                        .getAsJsonObject().get("download_location").getAsString());

        return builder.build();
    }

    public static PhotoDescription getPhotoDescriptionObj(JsonObject element) {

        PhotoDescription.Builder builder =
                new PhotoDescription.Builder(element.getAsJsonObject().get("id").getAsString());

        builder.createdAt(element.getAsJsonObject().get("created_at").getAsString());
        builder.updatedAt(element.getAsJsonObject().get("updated_at").getAsString());

        builder.width(element.getAsJsonObject().get("width").getAsInt());
        builder.height(element.getAsJsonObject().get("height").getAsInt());

        builder.color(element.getAsJsonObject().get("color").getAsString());
        builder.likes(element.getAsJsonObject().get("likes").getAsInt());

        builder.views(element.getAsJsonObject().get("views").getAsInt());
        builder.downloads(element.getAsJsonObject().get("downloads").getAsInt());

        try {
            builder.description(element.getAsJsonObject().get("description").getAsString());
        } catch (Exception e) {
            builder.description("- photo on Unsplash");
        }

        builder.artistId(
                element
                        .getAsJsonObject()
                        .get("user")
                        .getAsJsonObject().get("id").getAsString()
        );

        builder.artistName(
                element
                        .getAsJsonObject()
                        .get("user")
                        .getAsJsonObject().get("name").getAsString()
        );

        builder.artistProfileImageUrl(
                element
                        .getAsJsonObject()
                        .get("user")
                        .getAsJsonObject()
                        .get("profile_image")
                        .getAsJsonObject()
                        .get("large").getAsString()
        );

        builder.artistUsername(
                element
                        .getAsJsonObject()
                        .get("user")
                        .getAsJsonObject().get("username").getAsString()
        );

        builder.urlRaw(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("raw").getAsString());

        builder.urlFull(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("full").getAsString());

        builder.urlRegular(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("regular").getAsString());

        builder.urlSmall(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("small").getAsString());

        builder.urlThumb(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject().get("thumb").getAsString());

        builder.urlThumb(
                element
                        .getAsJsonObject()
                        .get("urls")
                        .getAsJsonObject()
                        .get("thumb")
                        .getAsString());

        builder.linkSelf(
                element
                        .getAsJsonObject()
                        .get("links")
                        .getAsJsonObject().get("self").getAsString());

        builder.linkHtml(
                element
                        .getAsJsonObject()
                        .get("links")
                        .getAsJsonObject().get("html").getAsString());

        builder.linkDownload(
                element
                        .getAsJsonObject()
                        .get("links")
                        .getAsJsonObject().get("download").getAsString());

        builder.linkDownloadLocation(
                element
                        .getAsJsonObject()
                        .get("links")
                        .getAsJsonObject().get("download_location").getAsString());

        try {

            builder.locationTitle(
                    element
                            .get("location")
                            .getAsJsonObject().get("title").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.locationLat(
                    element
                            .get("location")
                            .getAsJsonObject()
                            .get("position")
                            .getAsJsonObject()
                            .get("latitude")
                            .getAsDouble());

            builder.locationLon(
                    element
                            .get("location")
                            .getAsJsonObject()
                            .get("position")
                            .getAsJsonObject()
                            .get("longitude")
                            .getAsDouble());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.exifMake(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("make").getAsString());

        } catch (Exception e) {
            // do nothing
        }

        try {

            builder.exifModel(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("model").getAsString());

        } catch (Exception e) {
            // do nothing
        }

        try {

            builder.exifExposureTime(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("exposure_time").getAsString());

        } catch (Exception e) {
            // do nothing
        }

        try {

            builder.exifAperture(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("aperture").getAsString());

        } catch (Exception e) {
            // do nothing
        }

        try {

            builder.exifFocalLength(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("focal_length").getAsString());

        } catch (Exception e) {
            // do nothing
        }

        try {

            builder.exifIso(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("iso").getAsInt());

        } catch (Exception e) {
            // do nothing
        }


        return builder.build();
    }

    public static UserDescription getUserDescriptionObj(JsonObject element) {

        UserDescription.Builder builder =
                new UserDescription.Builder(element.getAsJsonObject().get("id").getAsString());

        builder.updatedAt(element.getAsJsonObject().get("updated_at").getAsString());

        builder.username(element.getAsJsonObject().get("username").getAsString());

        builder.name(element.getAsJsonObject().get("name").getAsString());

        try {
            builder.portfolioUrl(element.getAsJsonObject().get("portfolio_url").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.bio(element.getAsJsonObject().get("bio").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.location(element.getAsJsonObject().get("location").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        builder.totalLikes(element.getAsJsonObject().get("total_likes").getAsInt());

        builder.totalPhotos(element.getAsJsonObject().get("total_photos").getAsInt());

        builder.totalCollections(element.getAsJsonObject().get("total_collections").getAsInt());

        builder.followersCount(element.getAsJsonObject().get("followers_count").getAsInt());

        builder.downloads(element.getAsJsonObject().get("downloads").getAsInt());

        builder.profileImageUrl(
                element.getAsJsonObject()
                        .get("profile_image")
                        .getAsJsonObject().get("large").getAsString());

        try {
            builder.badgeTitle(
                    element.getAsJsonObject()
                            .get("badge")
                            .getAsJsonObject().get("title").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.badgeLink(
                    element.getAsJsonObject()
                            .get("badge")
                            .getAsJsonObject().get("link").getAsString());
        } catch (Exception e) {
            // do nothing
        }


        JsonArray jsonTags = element.getAsJsonObject()
                .get("tags")
                .getAsJsonObject()
                .get("custom")
                .getAsJsonArray();

        String tags[] = new String[jsonTags.size()];

        int index = 0;
        for (JsonElement tag : jsonTags) {
            tags[index] = tag.getAsJsonObject().get("title").getAsString();
            index++;
        }

        builder.tags(tags);

        return builder.build();
    }

}
