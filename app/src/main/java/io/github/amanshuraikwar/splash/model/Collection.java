package io.github.amanshuraikwar.splash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class Collection implements Parcelable {

    private int id;

    private String title,
            description;

    @SerializedName("published_at")
    private String publishedAt;

    @SerializedName("updated_at")
    private String updatedAt;

    private boolean curated, featured;

    @SerializedName("total_photos")
    private int totalPhotos;

    @SerializedName("private")
    private boolean privateC;

    @SerializedName("share_key")
    private String shareKey;

    private String[] tags;

    @SerializedName("cover_photo")
    private Photo coverPhoto;

    private CollectionPreviewPhoto[] previewPhotos;

    private User user;

    @SerializedName("link")
    private CollectionLinks collectionLinks;

    private Collection(Builder builder) {
        id = builder.id;
        title = builder.title;
        description = builder.description;
        publishedAt = builder.publishedAt;
        updatedAt = builder.updatedAt;
        curated = builder.curated;
        featured = builder.featured;
        totalPhotos = builder.totalPhotos;
        privateC = builder.privateC;
        shareKey = builder.shareKey;
        tags = builder.tags;
        coverPhoto = builder.coverPhoto;
        previewPhotos = builder.previewPhotos;
        user = builder.user;
        collectionLinks = builder.collectionLinks;
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

    public Photo getCoverPhoto() {
        return coverPhoto;
    }

    public CollectionPreviewPhoto[] getPreviewPhotos() {
        return previewPhotos;
    }

    public User getUser() {
        return user;
    }

    public CollectionLinks getCollectionLinks() {
        return collectionLinks;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", curated=" + curated +
                ", featured=" + featured +
                ", totalPhotos=" + totalPhotos +
                ", privateC=" + privateC +
                ", shareKey='" + shareKey + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", coverPhoto=" + coverPhoto +
                ", previewPhotos=" + Arrays.toString(previewPhotos) +
                ", user=" + user +
                ", collectionLinks=" + collectionLinks +
                '}';
    }

    public static final class Builder {
        private int id;
        private String title;
        private String description;
        private String publishedAt;
        private String updatedAt;
        private boolean curated;
        private boolean featured;
        private int totalPhotos;
        private boolean privateC;
        private String shareKey;
        private String[] tags;
        private Photo coverPhoto;
        private CollectionPreviewPhoto[] previewPhotos;
        private User user;
        private CollectionLinks collectionLinks;

        public Builder(int id) {
            this.id = id;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder publishedAt(String val) {
            publishedAt = val;
            return this;
        }

        public Builder updatedAt(String val) {
            updatedAt = val;
            return this;
        }

        public Builder curated(boolean val) {
            curated = val;
            return this;
        }

        public Builder featured(boolean val) {
            featured = val;
            return this;
        }

        public Builder totalPhotos(int val) {
            totalPhotos = val;
            return this;
        }

        public Builder privateC(boolean val) {
            privateC = val;
            return this;
        }

        public Builder shareKey(String val) {
            shareKey = val;
            return this;
        }

        public Builder tags(String[] val) {
            tags = val;
            return this;
        }

        public Builder coverPhoto(Photo val) {
            coverPhoto = val;
            return this;
        }

        public Builder previewPhotos(CollectionPreviewPhoto[] val) {
            previewPhotos = val;
            return this;
        }

        public Builder user(User val) {
            user = val;
            return this;
        }

        public Builder collectionLinks(CollectionLinks val) {
            collectionLinks = val;
            return this;
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
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.publishedAt);
        dest.writeString(this.updatedAt);
        dest.writeByte(this.curated ? (byte) 1 : (byte) 0);
        dest.writeByte(this.featured ? (byte) 1 : (byte) 0);
        dest.writeInt(this.totalPhotos);
        dest.writeByte(this.privateC ? (byte) 1 : (byte) 0);
        dest.writeString(this.shareKey);
        dest.writeStringArray(this.tags);
        dest.writeParcelable(this.coverPhoto, flags);
        dest.writeTypedArray(this.previewPhotos, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.collectionLinks, flags);
    }

    protected Collection(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.publishedAt = in.readString();
        this.updatedAt = in.readString();
        this.curated = in.readByte() != 0;
        this.featured = in.readByte() != 0;
        this.totalPhotos = in.readInt();
        this.privateC = in.readByte() != 0;
        this.shareKey = in.readString();
        this.tags = in.createStringArray();
        this.coverPhoto = in.readParcelable(Photo.class.getClassLoader());
        this.previewPhotos = in.createTypedArray(CollectionPreviewPhoto.CREATOR);
        this.user = in.readParcelable(User.class.getClassLoader());
        this.collectionLinks = in.readParcelable(CollectionLinks.class.getClassLoader());
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
