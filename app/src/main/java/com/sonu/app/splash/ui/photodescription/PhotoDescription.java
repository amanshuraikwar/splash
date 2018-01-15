package com.sonu.app.splash.ui.photodescription;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Nonnull;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class PhotoDescription implements Parcelable {

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
            artistProfileImageUrl,
            artistUsername,
            locationTitle,
            exifMake,
            exifModel,
            exifExposureTime,
            exifAperture,
            exifFocalLength;

    private int width, height, likes, views, downloads, exifIso;

    private double locationLat, locationLon;

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

    public String getArtistProfileImageUrl() {
        return artistProfileImageUrl;
    }

    public String getArtistUsername() {
        return artistUsername;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getExifMake() {
        return exifMake;
    }

    public String getExifModel() {
        return exifModel;
    }

    public String getExifExposureTime() {
        return exifExposureTime;
    }

    public String getExifAperture() {
        return exifAperture;
    }

    public String getExifFocalLength() {
        return exifFocalLength;
    }

    public int getViews() {
        return views;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getExifIso() {
        return exifIso;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public double getLocationLon() {
        return locationLon;
    }

    private PhotoDescription(Builder builder) {
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
        locationTitle = builder.locationTitle;
        exifMake = builder.exifMake;
        exifModel = builder.exifModel;
        exifExposureTime = builder.exifExposureTime;
        exifAperture = builder.exifAperture;
        exifFocalLength = builder.exifFocalLength;
        width = builder.width;
        height = builder.height;
        likes = builder.likes;
        views = builder.views;
        downloads = builder.downloads;
        exifIso = builder.exifIso;
        artistId = builder.artistId;
        artistName = builder.artistName;
        artistProfileImageUrl = builder.artistProfileImageUrl;
        artistUsername = builder.artistUsername;
        locationLat = builder.locationLat;
        locationLon = builder.locationLon;
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
        parcel.writeString(locationTitle);
        parcel.writeString(exifMake);
        parcel.writeString(exifModel);
        parcel.writeString(exifExposureTime);
        parcel.writeString(exifAperture);
        parcel.writeString(exifFocalLength);
        parcel.writeInt(exifIso);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeInt(likes);
        parcel.writeInt(views);
        parcel.writeInt(downloads);
        parcel.writeString(artistId);
        parcel.writeString(artistName);
        parcel.writeString(artistProfileImageUrl);
        parcel.writeString(artistUsername);
        parcel.writeDouble(locationLat);
        parcel.writeDouble(locationLon);
    }

    public PhotoDescription(Parcel parcel) {
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
        locationTitle = parcel.readString();
        exifMake = parcel.readString();
        exifModel = parcel.readString();
        exifExposureTime = parcel.readString();
        exifAperture = parcel.readString();
        exifFocalLength = parcel.readString();
        exifIso = parcel.readInt();
        width = parcel.readInt();
        height = parcel.readInt();
        likes = parcel.readInt();
        views = parcel.readInt();
        downloads = parcel.readInt();
        artistId = parcel.readString();
        artistName = parcel.readString();
        artistProfileImageUrl = parcel.readString();
        artistUsername = parcel.readString();
        locationLat = parcel.readLong();
        locationLon = parcel.readLong();
    }

    @Override
    public String toString() {
        return "PhotoDescription{" +
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
                ", artistId='" + artistId + '\'' +
                ", artistName='" + artistName + '\'' +
                ", artistProfileImageUrl='" + artistProfileImageUrl + '\'' +
                ", artistUsername='" + artistUsername + '\'' +
                ", locationTitle='" + locationTitle + '\'' +
                ", exifMake='" + exifMake + '\'' +
                ", exifModel='" + exifModel + '\'' +
                ", exifExposureTime='" + exifExposureTime + '\'' +
                ", exifAperture='" + exifAperture + '\'' +
                ", exifFocalLength='" + exifFocalLength + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", likes=" + likes +
                ", views=" + views +
                ", downloads=" + downloads +
                ", exifIso=" + exifIso +
                ", locationLat=" + locationLat +
                ", locationLon=" + locationLon +
                '}';
    }

    public static final Creator<PhotoDescription> CREATOR =
            new Creator<PhotoDescription>() {
                public PhotoDescription createFromParcel(Parcel in) {
                    return new PhotoDescription(in);
                }

                public PhotoDescription[] newArray(int size) {
                    return new PhotoDescription[size];
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
                artistProfileImageUrl = "",
                artistUsername = "",
                locationTitle = "",
                exifMake = "--",
                exifModel = "--",
                exifExposureTime = "--",
                exifAperture = "--",
                exifFocalLength = "--";

        private int width, height, likes, views, downloads, exifIso;

        private double locationLat, locationLon;

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

        public void locationTitle(@Nonnull String locationTitle) {
            this.locationTitle = locationTitle;
        }

        public void exifMake(@Nonnull String exifMake) {
            this.exifMake = exifMake;
        }

        public void exifModel(@Nonnull String exifModel) {
            this.exifModel = exifModel;
        }

        public void exifExposureTime(@Nonnull String exifExposureTime) {
            this.exifExposureTime = exifExposureTime;
        }

        public void exifAperture(@Nonnull String exifApperature) {
            this.exifAperture = exifApperature;
        }

        public void exifFocalLength(@Nonnull String exifFocalLength) {
            this.exifFocalLength = exifFocalLength;
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

        public void views(int views) {
            this.views = views;
        }

        public void downloads(int downloads) {
            this.downloads = downloads;
        }

        public void exifIso(int exifIso) {
            this.exifIso = exifIso;
        }

        public void artistId(String artistId) {
            this.artistId = artistId;
        }

        public void artistName(String artistName) {
            this.artistName = artistName;
        }

        public void artistProfileImageUrl(String artistProfileImageUrl) {
            this.artistProfileImageUrl = artistProfileImageUrl;
        }

        public void artistUsername(String artistUsername) {
            this.artistUsername = artistUsername;
        }

        public void locationLat(double locationLat) {
            this.locationLat = locationLat;
        }

        public void locationLon(double locationLon) {
            this.locationLon = locationLon;
        }

        public PhotoDescription build() {
            return new PhotoDescription(this);
        }
    }
}
