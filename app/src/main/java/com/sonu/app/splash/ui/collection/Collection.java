package com.sonu.app.splash.ui.collection;

import android.os.Parcel;
import android.os.Parcelable;

import com.sonu.app.splash.ui.photo.Photo;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class Collection implements Parcelable {

    private int id,
            totalPhotos;

    private String title,
            description,
            publishedAt,
            updatedAt,
            shareKey,
            artistId,
            artistName,
            artistUsername,
            artistProfileImageUrl;

    private String[] tags;

    private Photo coverPhoto;

    private boolean curated;

    public int getId() {
        return id;
    }

    public int getTotalPhotos() {
        return totalPhotos;
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

    public String getShareKey() {
        return shareKey;
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

    public String[] getTags() {
        return tags;
    }

    public Photo getCoverPhoto() {
        return coverPhoto;
    }

    public boolean isCurated() {
        return curated;
    }

    private Collection(Builder builder) {

        id = builder.id;
        totalPhotos = builder.totalPhotos;
        title = builder.title;
        description = builder.description;
        publishedAt = builder.publishedAt;
        updatedAt = builder.updatedAt;
        shareKey = builder.shareKey;
        artistId = builder.artistId;
        artistName = builder.artistName;
        artistUsername = builder.artistUsername;
        artistProfileImageUrl = builder.artistProfileImageUrl;
        coverPhoto = builder.coverPhoto;
        curated = builder.curated;
        tags = builder.tags;
    }



    public static class Builder {

        private int id,
                totalPhotos;

        private String title = "",
                description = "",
                publishedAt = "",
                updatedAt = "",
                shareKey = "",
                artistId = "",
                artistName = "",
                artistUsername = "",
                artistProfileImageUrl = "";

        private String[] tags = new String[]{};

        private Photo coverPhoto;

        private boolean curated;

        public Builder(int id) {
            this.id = id;
        }

        public void totalPhotos(int totalPhotos) {
            this.totalPhotos = totalPhotos;
        }

        public void title(String title) {
            this.title = title;
        }

        public void description(String description) {
            this.description = description;
        }

        public void publishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public void updatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public void shareKey(String shareKey) {
            this.shareKey = shareKey;
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

        public void tags(String[] tags) {
            this.tags = tags;
        }

        public void coverPhoto(Photo coverPhoto) {
            this.coverPhoto = coverPhoto;
        }

        public void curated(boolean curated) {
            this.curated = curated;
        }

        public Collection build() {
            return new Collection(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.totalPhotos);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.publishedAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.shareKey);
        dest.writeString(this.artistId);
        dest.writeString(this.artistName);
        dest.writeString(this.artistUsername);
        dest.writeString(this.artistProfileImageUrl);
        dest.writeStringArray(this.tags);
        dest.writeParcelable(this.coverPhoto, flags);
        dest.writeByte(this.curated ? (byte) 1 : (byte) 0);
    }

    protected Collection(Parcel in) {
        this.id = in.readInt();
        this.totalPhotos = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.publishedAt = in.readString();
        this.updatedAt = in.readString();
        this.shareKey = in.readString();
        this.artistId = in.readString();
        this.artistName = in.readString();
        this.artistUsername = in.readString();
        this.artistProfileImageUrl = in.readString();
        this.tags = in.createStringArray();
        this.coverPhoto = in.readParcelable(Photo.class.getClassLoader());
        this.curated = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel source) {
            return new Collection(source);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };
}
