package com.sonu.app.splash.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sonu.app.splash.model.unsplash.Badge;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.model.unsplash.CollectionLinks;
import com.sonu.app.splash.model.unsplash.CollectionPreviewPhoto;
import com.sonu.app.splash.model.unsplash.Exif;
import com.sonu.app.splash.model.unsplash.Location;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.PhotoLinks;
import com.sonu.app.splash.model.unsplash.PhotoUrls;
import com.sonu.app.splash.model.unsplash.ProfileImage;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.model.unsplash.UserLinks;
import com.sonu.app.splash.model.unsplash.UserTags;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

public class UnsplashJsonUtils {

    public static Photo buildPhotoObj(JsonObject jsonObject) {

        Photo.Builder builder =
                new Photo.Builder(jsonObject.get("id").getAsString());

        builder.createdAt(jsonObject.get("created_at").getAsString());
        builder.updatedAt(jsonObject.get("updated_at").getAsString());

        builder.width(jsonObject.get("width").getAsInt());
        builder.height(jsonObject.get("height").getAsInt());

        builder.color(jsonObject.get("color").getAsString());

        try {
            builder.description(jsonObject.get("description").getAsString());
        } catch (Exception e) {
            builder.description("- photo on Unsplash");
        }

        builder.urls(buildPhotoUrlsObj(jsonObject.get("urls").getAsJsonObject()));

        builder.links(buildPhotoLinks(jsonObject.get("links").getAsJsonObject()));

        builder.likes(jsonObject.get("likes").getAsInt());

        builder.user(buildUserObj(jsonObject.get("user").getAsJsonObject()));

        try {
            builder.location(buildLocationObj(jsonObject.get("location").getAsJsonObject()));
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.exif(buildExifObj(jsonObject.get("exif").getAsJsonObject()));
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.views(jsonObject.get("views").getAsInt());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.downloads(jsonObject.get("downloads").getAsInt());
        } catch (Exception e) {
            // do nothing
        }

        return builder.build();
    }

    private static PhotoUrls buildPhotoUrlsObj(JsonObject jsonObject) {

        PhotoUrls.Builder builder = new PhotoUrls.Builder();

        builder.raw(jsonObject.get("raw").getAsString());

        builder.full(jsonObject.get("full").getAsString());

        builder.regular(jsonObject.get("regular").getAsString());

        builder.small(jsonObject.get("small").getAsString());

        builder.thumb(jsonObject.get("thumb").getAsString());

        return builder.build();
    }

    private static PhotoLinks buildPhotoLinks(JsonObject jsonObject) {

        PhotoLinks.Builder builder = new PhotoLinks.Builder();

        builder.self(jsonObject.get("self").getAsString());

        builder.html(jsonObject.get("html").getAsString());

        builder.download(jsonObject.get("download").getAsString());

        builder.downloadLocation(jsonObject.get("download_location").getAsString());

        return builder.build();
    }

    private static Location buildLocationObj(JsonObject jsonObject) {

        Location.Builder builder = new Location.Builder();

        try {
            builder.title(jsonObject.get("title").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.name(jsonObject.get("name").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.city(jsonObject.get("city").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.country(jsonObject.get("country").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.lat(jsonObject.get("position").getAsJsonObject().get("latitude").getAsDouble());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.lon(jsonObject.get("position").getAsJsonObject().get("longitude").getAsDouble());
        } catch (Exception e) {
            // do nothing
        }

        return builder.build();
    }

    private static Exif buildExifObj(JsonObject jsonObject) {

        Exif.Builder builder = new Exif.Builder();

        try {
            builder.make(jsonObject.get("make").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.model(jsonObject.get("model").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.exposureTime(jsonObject.get("exposure_time").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.aperture(jsonObject.get("aperture").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.focalLength(jsonObject.get("focal_length").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.iso(jsonObject.get("iso").getAsInt());
        } catch (Exception e) {
            // do nothing
        }

        return builder.build();
    }

    public static User buildUserObj(JsonObject jsonObject) {

        User.Builder builder =
                new User.Builder(jsonObject.get("id").getAsString());

        builder.updatedAt(jsonObject.get("updated_at").getAsString());

        try {
            builder.numericId(jsonObject.get("numeric_id").getAsInt());
        } catch (Exception e) {
            // do nothing
        }

        builder.username(jsonObject.get("username").getAsString());

        builder.name(jsonObject.get("name").getAsString());

        try {

            builder.firstName(jsonObject.get("first_name").getAsString());
            builder.lastName(jsonObject.get("last_name").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.twitterUsername(jsonObject.get("twitter_username").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.portfolioUrl(jsonObject.get("portfolio_url").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.bio(jsonObject.get("bio").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.location(jsonObject.get("location").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        builder.totalLikes(jsonObject.get("total_likes").getAsInt());

        builder.totalPhotos(jsonObject.get("total_photos").getAsInt());

        builder.totalCollections(jsonObject.get("total_collections").getAsInt());

        try {
            builder.followingCount(jsonObject.get("following_count").getAsInt());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.followersCount(jsonObject.get("followers_count").getAsInt());
        } catch (Exception e) {
            // do nothing
        }

        try {
            builder.downloads(jsonObject.get("downloads").getAsInt());
        } catch (Exception e) {
            // do nothing
        }

        builder.profileImage(buildProfileImageObj(jsonObject.get("profile_image").getAsJsonObject()));

        try {
            builder.badge(buildBadgeObj(jsonObject.get("badge").getAsJsonObject()));
        } catch (Exception e) {
            // do nothing
        }

        try {

            builder.tags(buildUserTags(jsonObject.get("tags").getAsJsonObject()));
        } catch (Exception e) {
            // do nothing
        }

        builder.userLinks(buildUserLinks(jsonObject.get("links").getAsJsonObject()));

        return builder.build();
    }

    private static ProfileImage buildProfileImageObj(JsonObject jsonObject) {

        ProfileImage.Builder builder = new ProfileImage.Builder();

        builder.small(jsonObject.get("small").getAsString());

        builder.meduim(jsonObject.get("medium").getAsString());

        builder.large(jsonObject.get("large").getAsString());

        return builder.build();
    }

    private static Badge buildBadgeObj(JsonObject jsonObject) {

        Badge.Builder builder = new Badge.Builder(jsonObject.get("title").getAsString());

        builder.primary(jsonObject.get("primary").getAsBoolean());

        try {
            builder.slug(jsonObject.get("slug").getAsString());
        } catch (Exception e) {
            // do nothing
        }

        builder.link(jsonObject.get("link").getAsString());

        return builder.build();
    }

    private static UserTags buildUserTags(JsonObject jsonObject) {

        UserTags.Builder builder = new UserTags.Builder();

        JsonArray jsonTags;

        try {

            jsonTags = jsonObject
                    .getAsJsonObject()
                    .get("custom")
                    .getAsJsonArray();

            String tags[] = new String[jsonTags.size()];

            int index = 0;
            for (JsonElement tag : jsonTags) {
                tags[index] = tag.getAsJsonObject().get("title").getAsString();
                index++;
            }

            builder.custom(tags);
        } catch (Exception e) {
            // do nothing
        }

        try {

            jsonTags = jsonObject
                    .getAsJsonObject()
                    .get("aggregated")
                    .getAsJsonArray();

            String tags[] = new String[jsonTags.size()];

            int index = 0;
            for (JsonElement tag : jsonTags) {
                tags[index] = tag.getAsJsonObject().get("title").getAsString();
                index++;
            }

            builder.aggregated(tags);
        } catch (Exception e) {
            // do nothing
        }

        return builder.build();
    }

    private static UserLinks buildUserLinks(JsonObject jsonObject) {

        UserLinks.Builder builder = new UserLinks.Builder();

        builder.self(jsonObject.get("self").getAsString());

        builder.html(jsonObject.get("html").getAsString());

        builder.photos(jsonObject.get("photos").getAsString());

        builder.likes(jsonObject.get("likes").getAsString());

        builder.portfolio(jsonObject.get("portfolio").getAsString());

        builder.following(jsonObject.get("following").getAsString());

        builder.followers(jsonObject.get("followers").getAsString());

        return builder.build();
    }

    public static Collection buildCollectionObj(JsonObject jsonObject) {


        Collection.Builder builder =
                new Collection.Builder(jsonObject.get("id").getAsInt());

        builder.title(jsonObject.get("title").getAsString());

        builder.publishedAt(jsonObject.get("published_at").getAsString());
        builder.updatedAt(jsonObject.get("updated_at").getAsString());

        builder.curated(jsonObject.get("curated").getAsBoolean());

        builder.featured(jsonObject.get("featured").getAsBoolean());

        builder.totalPhotos(jsonObject.get("total_photos").getAsInt());

        builder.privateC(jsonObject.get("private").getAsBoolean());

        builder.shareKey(jsonObject.get("share_key").getAsString());

        try {

            JsonArray jsonTags = jsonObject.get("tags").getAsJsonArray();

            String tags[] = new String[jsonTags.size()];

            int index = 0;
            for (JsonElement tag : jsonTags) {
                tags[index] = tag.getAsJsonObject().get("title").getAsString();
                index++;
            }

            builder.tags(tags);
        } catch (Exception e) {
            // do nothing
        }

        builder.coverPhoto(buildPhotoObj(jsonObject.get("cover_photo").getAsJsonObject()));

        try {

            JsonArray jsonPreviewPhotos = jsonObject.get("preview_photos").getAsJsonArray();

            CollectionPreviewPhoto[] previewPhotos =
                    new CollectionPreviewPhoto[jsonPreviewPhotos.size()];

            int index = 0;
            for (JsonElement previewPhoto : jsonPreviewPhotos) {

                previewPhotos[index] =
                        buildCollectionPreviewPhotoObj(previewPhoto.getAsJsonObject());
                index++;
            }

            builder.previewPhotos(previewPhotos);
        } catch (Exception e) {
            // do nothing
        }

        builder.user(buildUserObj(jsonObject.get("user").getAsJsonObject()));

        builder.collectionLinks(
                buildCollectionLinksObj(jsonObject.get("links").getAsJsonObject()));

        return builder.build();
    }

    private static CollectionPreviewPhoto buildCollectionPreviewPhotoObj(JsonObject jsonObject) {

        CollectionPreviewPhoto.Builder builder =
                new CollectionPreviewPhoto.Builder(jsonObject.get("id").getAsInt());

        builder.photoUrls(buildPhotoUrlsObj(jsonObject.get("urls").getAsJsonObject()));

        return builder.build();
    }

    private static CollectionLinks buildCollectionLinksObj(JsonObject jsonObject) {

        CollectionLinks.Builder builder = new CollectionLinks.Builder();

        builder.self(jsonObject.get("self").getAsString());

        builder.html(jsonObject.get("html").getAsString());

        builder.photos(jsonObject.get("photos").getAsString());

        builder.related(jsonObject.get("related").getAsString());

        return builder.build();
    }
}
