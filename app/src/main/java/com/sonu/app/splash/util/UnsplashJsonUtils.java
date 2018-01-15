package com.sonu.app.splash.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photodescription.PhotoDescription;

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
            // do nothing
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
            // do nothing
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

            builder.exifModel(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("model").getAsString());

            builder.exifExposureTime(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("exposure_time").getAsString());

            builder.exifAperture(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("aperture").getAsString());

            builder.exifFocalLength(
                    element
                            .getAsJsonObject()
                            .get("exif")
                            .getAsJsonObject().get("focal_length").getAsString());

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

}
