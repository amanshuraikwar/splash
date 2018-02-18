package com.sonu.app.splash.model.unsplash;

import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class Photo implements Parcelable {

    @PrimaryKey
    private String id;

    private String createdAt,
            updatedAt;

    private int width, height;

    private String color,
            description;

    private PhotoUrls photoUrls;

    private PhotoLinks photoLinks;

    private int likes;

    private User user;

    private Location location;

    private Exif exif;

    private int views, downloads;

    private Photo(Builder builder) {
        id = builder.id;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
        width = builder.width;
        height = builder.height;
        color = builder.color;
        description = builder.description;
        photoUrls = builder.photoUrls;
        photoLinks = builder.photoLinks;
        likes = builder.likes;
        user = builder.user;
        location = builder.location;
        exif = builder.exif;
        views = builder.views;
        downloads = builder.downloads;
    }

    public String getId() {
        return id;
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

    public int getLikes() {
        return likes;
    }

    public User getUser() {
        return user;
    }

    public Location getLocation() {
        return location;
    }

    public Exif getExif() {
        return exif;
    }

    public int getViews() {
        return views;
    }

    public int getDownloads() {
        return downloads;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", photoUrls=" + photoUrls +
                ", photoLinks=" + photoLinks +
                ", likes=" + likes +
                ", user=" + user +
                ", location=" + location +
                ", exif=" + exif +
                ", views=" + views +
                ", downloads=" + downloads +
                '}';
    }

    public static final class Builder {

        private String id;
        private String createdAt;
        private String updatedAt;
        private int width;
        private int height;
        private String color;
        private String description;
        private PhotoUrls photoUrls;
        private PhotoLinks photoLinks;
        private int likes;
        private User user;
        private Location location;
        private Exif exif;
        private int views;
        private int downloads;

        public Builder(String id) {
            this.id = id;
        }

        public Builder createdAt(String val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(String val) {
            updatedAt = val;
            return this;
        }

        public Builder width(int val) {
            width = val;
            return this;
        }

        public Builder height(int val) {
            height = val;
            return this;
        }

        public Builder color(String val) {
            color = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder urls(PhotoUrls val) {
            photoUrls = val;
            return this;
        }

        public Builder links(PhotoLinks val) {
            photoLinks = val;
            return this;
        }

        public Builder likes(int val) {
            likes = val;
            return this;
        }

        public Builder user(User val) {
            user = val;
            return this;
        }

        public Builder location(Location val) {
            location = val;
            return this;
        }

        public Builder exif(Exif val) {
            exif = val;
            return this;
        }

        public Builder views(int val) {
            views = val;
            return this;
        }

        public Builder downloads(int val) {
            downloads = val;
            return this;
        }

        public Photo build() {
            return new Photo(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.color);
        dest.writeString(this.description);
        dest.writeParcelable(this.photoUrls, flags);
        dest.writeParcelable(this.photoLinks, flags);
        dest.writeInt(this.likes);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.location, flags);
        dest.writeParcelable(this.exif, flags);
        dest.writeInt(this.views);
        dest.writeInt(this.downloads);
    }

    protected Photo(Parcel in) {
        this.id = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.color = in.readString();
        this.description = in.readString();
        this.photoUrls = in.readParcelable(PhotoUrls.class.getClassLoader());
        this.photoLinks = in.readParcelable(PhotoLinks.class.getClassLoader());
        this.likes = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.exif = in.readParcelable(Exif.class.getClassLoader());
        this.views = in.readInt();
        this.downloads = in.readInt();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
