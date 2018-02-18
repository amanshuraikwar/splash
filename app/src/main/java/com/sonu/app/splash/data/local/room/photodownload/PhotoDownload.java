package com.sonu.app.splash.data.local.room.photodownload;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.PhotoUtils;

/**
 * Created by amanshuraikwar on 23/12/17.
 */

@Entity
public class PhotoDownload{

    public enum Status {SUCCESSFUL, RUNNING, PAUSED, PENDING, FAILED}

    @PrimaryKey
    private long downloadReference;

    private String downloadTimestamp;

    private Status status;

    private String photoId;

    private String downloadUrl;
    private String downloadedFileName;
    private String photoUrl;

    private int width, height;
    private String color;

    private PhotoDownload(Builder builder) {
        downloadReference = builder.downloadReference;
        downloadTimestamp = builder.downloadTimestamp;
        status = builder.status;
        photoId = builder.photoId;
        downloadUrl = builder.downloadUrl;
        downloadedFileName = builder.downloadedFileName;
        photoUrl = builder.photoUrl;
        width = builder.width;
        height = builder.height;
        color = builder.color;
    }

    public PhotoDownload(long downloadReference,
                         String downloadTimestamp,
                         Status status,
                         String photoId,
                         String downloadUrl,
                         String downloadedFileName,
                         String photoUrl,
                         int width,
                         int height,
                         String color) {

        this.downloadReference = downloadReference;
        this.downloadTimestamp = downloadTimestamp;
        this.status = status;
        this.photoId = photoId;
        this.downloadUrl = downloadUrl;
        this.downloadedFileName = downloadedFileName;
        this.photoUrl = photoUrl;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getDownloadedFileName() {
        return downloadedFileName;
    }

    public long getDownloadReference() {
        return downloadReference;
    }

    public String getDownloadTimestamp() {
        return downloadTimestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPhotoUrl() {
        return photoUrl;
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

    @Override
    public String toString() {
        return "PhotoDownload{" +
                "downloadReference=" + downloadReference +
                ", downloadTimestamp='" + downloadTimestamp + '\'' +
                ", status=" + status +
                ", photoId='" + photoId + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", downloadedFileName='" + downloadedFileName + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", color='" + color + '\'' +
                '}';
    }

    public static final class Builder {

        private long downloadReference;
        private String downloadTimestamp;
        private Status status = Status.PENDING;
        private String photoId;
        private String downloadUrl;
        private String downloadedFileName;
        private String photoUrl;
        private int width, height;
        private String color;

        public Builder(long downloadReference, String downloadTimestamp) {

            this.downloadReference = downloadReference;
            this.downloadTimestamp = downloadTimestamp;
        }

        public Builder photo(Photo photo) {

            photoId = photo.getId();
            downloadUrl = photo.getPhotoLinks().getDownload();
            downloadedFileName = PhotoUtils.getDownloadFileName(photo);
            photoUrl = photo.getPhotoUrls().getFull();
            width = photo.getWidth();
            height = photo.getHeight();
            color = photo.getColor();
            return this;
        }

        public Builder status(Status val) {
            status = val;
            return this;
        }

        public PhotoDownload build() {
            return new PhotoDownload(this);
        }
    }
}
