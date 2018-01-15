package com.sonu.app.splash.ui.photo;

import android.os.Parcel;
import android.os.Parcelable;

import com.sonu.app.splash.data.download.PhotoDownload;

import javax.annotation.Nonnull;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class Photo implements Parcelable {

    private String id,
            createdAt,
            updatedAt,
            color,
            description,
            urlRaw,
            urlFull,
            urlRegular,
            urlSmall,
            urlThumb,
            linkSelf,
            linkHtml,
            linkDownload,
            linkDownloadLocation,
            artistId,
            artistName,
            artistUsername,
            artistProfileImageUrl;

    private int width, height, likes;

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlRaw() {
        return urlRaw;
    }

    public String getUrlFull() {
        return urlFull;
    }

    public String getUrlRegular() {
        return urlRegular;
    }

    public String getUrlSmall() {
        return urlSmall;
    }

    public String getUrlThumb() {
        return urlThumb;
    }

    public String getLinkSelf() {
        return linkSelf;
    }

    public String getLinkHtml() {
        return linkHtml;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public String getLinkDownloadLocation() {
        return linkDownloadLocation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLikes() {
        return likes;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistUsername() {
        return artistUsername;
    }

    public String getArtistProfileImageUrl() {
        return artistProfileImageUrl;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", urlRaw='" + urlRaw + '\'' +
                ", urlFull='" + urlFull + '\'' +
                ", urlRegular='" + urlRegular + '\'' +
                ", urlSmall='" + urlSmall + '\'' +
                ", urlThumb='" + urlThumb + '\'' +
                ", linkSelf='" + linkSelf + '\'' +
                ", linkHtml='" + linkHtml + '\'' +
                ", linkDownload='" + linkDownload + '\'' +
                ", linkDownloadLocation='" + linkDownloadLocation + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", likes=" + likes +
                ", artistId=" + artistId +
                ", artistName=" + artistName +
                ", artistUsername=" + artistUsername +
                ", artistProfileImageUrl=" + artistProfileImageUrl +
                '}';
    }

    private Photo(Builder builder) {
        id = builder.id;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
        color = builder.color;
        description = builder.description;
        urlRaw = builder.urlRaw;
        urlFull = builder.urlFull;
        urlRegular = builder.urlRegular;
        urlSmall = builder.urlSmall;
        urlThumb = builder.urlThumb;
        linkSelf = builder.linkSelf;
        linkHtml = builder.linkHtml;
        linkDownload = builder.linkDownload;
        linkDownloadLocation = builder.linkDownloadLocation;
        width = builder.width;
        height = builder.height;
        likes = builder.likes;
        artistId = builder.artistId;
        artistName = builder.artistName;
        artistUsername = builder.artistUsername;
        artistProfileImageUrl = builder.artistProfileImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeString(color);
        parcel.writeString(description);
        parcel.writeString(urlRaw);
        parcel.writeString(urlFull);
        parcel.writeString(urlRegular);
        parcel.writeString(urlSmall);
        parcel.writeString(urlThumb);
        parcel.writeString(linkSelf);
        parcel.writeString(linkHtml);
        parcel.writeString(linkDownload);
        parcel.writeString(linkDownloadLocation);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeInt(likes);
        parcel.writeString(artistId);
        parcel.writeString(artistName);
        parcel.writeString(artistUsername);
        parcel.writeString(artistProfileImageUrl);
    }

    public Photo(Parcel parcel) {
        id = parcel.readString();
        createdAt = parcel.readString();
        updatedAt = parcel.readString();
        color = parcel.readString();
        description = parcel.readString();
        urlRaw = parcel.readString();
        urlFull = parcel.readString();
        urlRegular = parcel.readString();
        urlSmall = parcel.readString();
        urlThumb = parcel.readString();
        linkSelf = parcel.readString();
        linkHtml = parcel.readString();
        linkDownload = parcel.readString();
        linkDownloadLocation = parcel.readString();
        width = parcel.readInt();
        height = parcel.readInt();
        likes = parcel.readInt();
        artistId = parcel.readString();
        artistName = parcel.readString();
        artistUsername = parcel.readString();
        artistProfileImageUrl = parcel.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR =
            new Parcelable.Creator<Photo>() {
                public Photo createFromParcel(Parcel in) {
                    return new Photo(in);
                }

                public Photo[] newArray(int size) {
                    return new Photo[size];
                }
            };

    public static class Builder {

        // default values
        private String id,
                createdAt = "",
                updatedAt = "",
                color = "#ffffff",
                description = "",
                urlRaw = "",
                urlFull = "",
                urlRegular = "",
                urlSmall = "",
                urlThumb = "",
                linkSelf = "",
                linkHtml = "",
                linkDownload = "",
                linkDownloadLocation = "",
                artistId = "",
                artistName = "",
                artistUsername = "",
                artistProfileImageUrl = "";

        private int width, height, likes;

        public Builder(String id) {
            this.id = id;
        }

        public void createdAt(@Nonnull String createdAt) {
            this.createdAt = createdAt;
        }

        public void updatedAt(@Nonnull String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public void color(@Nonnull String color) {
            this.color = color;
        }

        public void description(@Nonnull String description) {
            this.description = description;
        }

        public void urlRaw(@Nonnull String urlRaw) {
            this.urlRaw = urlRaw;
        }

        public void urlFull(@Nonnull String urlFull) {
            this.urlFull = urlFull;
        }

        public void urlRegular(@Nonnull String urlRegular) {
            this.urlRegular = urlRegular;
        }

        public void urlSmall(@Nonnull String urlSmall) {
            this.urlSmall = urlSmall;
        }

        public void urlThumb(@Nonnull String urlThumb) {
            this.urlThumb = urlThumb;
        }

        public void linkSelf(@Nonnull String linkSelf) {
            this.linkSelf = linkSelf;
        }

        public void linkHtml(@Nonnull String linkHtml) {
            this.linkHtml = linkHtml;
        }

        public void linkDownload(@Nonnull String linkDownload) {
            this.linkDownload = linkDownload;
        }

        public void linkDownloadLocation(@Nonnull String linkDownloadLocation) {
            this.linkDownloadLocation = linkDownloadLocation;
        }

        public void width(int width) {
            this.width = width;
        }

        public void height(int height) {
            this.height = height;
        }

        public void likes(int likes) {
            this.likes = likes;
        }

        public void artistId(String artistId) {
            this.artistId = artistId;
        }

        public void artistName(String artistName) {
            this.artistName = artistName;
        }

        public void artistUsername(String artistUsername) {
            this.artistUsername = artistUsername;
        }

        public void artistProfileImageUrl(String artistProfileImageUrl) {
            this.artistProfileImageUrl = artistProfileImageUrl;
        }

        public Photo build() {
            return new Photo(this);
        }
    }
}