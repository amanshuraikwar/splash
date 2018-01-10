package com.android.sonu.ummsplash.data.download;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.sonu.ummsplash.ui.photo.Photo;

/**
 * Created by amanshuraikwar on 23/12/17.
 */

public class PhotoDownload implements Parcelable {

    private static final String FILE_EXTENSION = "jpg";

    public PhotoDownload(Photo photo){
        photoId = photo.getId();
        downloadUrl = photo.getLinkDownload();
        downloadedFileName = photo.getId() + "." + FILE_EXTENSION;
        photoUrlThumb = photo.getUrlThumb();
    }

    private PhotoDownload(Parcel in) {
        photoId = in.readString();
        downloadUrl = in.readString();
        downloadedFileName = in.readString();
        progress = in.readInt();
        currentFileSize = in.readInt();
        totalFileSize = in.readInt();
    }

    private String photoId;
    private String downloadUrl;
    private String downloadedFileName;
    private String photoUrlThumb;
    private int progress;
    private int currentFileSize;
    private int totalFileSize;

    public String getPhotoId() {
        return photoId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getDownloadedFileName() {
        return downloadedFileName;
    }

    public String getPhotoUrlThumb() {
        return photoUrlThumb;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(int currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public int getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(photoId);
        dest.writeString(downloadUrl);
        dest.writeString(downloadedFileName);
        dest.writeInt(progress);
        dest.writeInt(currentFileSize);
        dest.writeInt(totalFileSize);
    }

    @Override
    public String toString() {
        return "PhotoDownload{" +
                "photoId='" + photoId + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", downloadedFileName='" + downloadedFileName + '\'' +
                ", photoUrlThumb='" + photoUrlThumb + '\'' +
                ", progress=" + progress +
                ", currentFileSize=" + currentFileSize +
                ", totalFileSize=" + totalFileSize +
                '}';
    }

    public static final Parcelable.Creator<PhotoDownload> CREATOR =
            new Parcelable.Creator<PhotoDownload>() {
                public PhotoDownload createFromParcel(Parcel in) {
                    return new PhotoDownload(in);
                }

                public PhotoDownload[] newArray(int size) {
                    return new PhotoDownload[size];
                }
            };
}
