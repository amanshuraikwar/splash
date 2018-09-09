package io.github.amanshuraikwar.splash.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class CollectionPreviewPhoto implements Parcelable {

    private int id;

    private PhotoUrls photoUrls;

    private CollectionPreviewPhoto(Builder builder) {
        id = builder.id;
        photoUrls = builder.photoUrls;
    }

    public int getId() {
        return id;
    }

    public PhotoUrls getPhotoUrls() {
        return photoUrls;
    }

    @Override
    public String toString() {
        return "CollectionPreviewPhoto{" +
                "id=" + id +
                ", photoUrls=" + photoUrls +
                '}';
    }

    public static final class Builder {
        private int id;
        private PhotoUrls photoUrls;

        public Builder(int id) {
            this.id = id;
        }

        public Builder photoUrls(PhotoUrls val) {
            photoUrls = val;
            return this;
        }

        public CollectionPreviewPhoto build() {
            return new CollectionPreviewPhoto(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.photoUrls, flags);
    }

    protected CollectionPreviewPhoto(Parcel in) {
        this.id = in.readInt();
        this.photoUrls = in.readParcelable(PhotoUrls.class.getClassLoader());
    }

    public static final Parcelable.Creator<CollectionPreviewPhoto> CREATOR = new Parcelable.Creator<CollectionPreviewPhoto>() {
        @Override
        public CollectionPreviewPhoto createFromParcel(Parcel source) {
            return new CollectionPreviewPhoto(source);
        }

        @Override
        public CollectionPreviewPhoto[] newArray(int size) {
            return new CollectionPreviewPhoto[size];
        }
    };
}
