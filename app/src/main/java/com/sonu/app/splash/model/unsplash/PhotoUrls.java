package com.sonu.app.splash.model.unsplash;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class PhotoUrls implements Parcelable {

    private String raw, full, regular, small, thumb;

    private PhotoUrls(Builder builder) {
        raw = builder.raw;
        full = builder.full;
        regular = builder.regular;
        small = builder.small;
        thumb = builder.thumb;
    }

    public String getRaw() {
        return raw;
    }

    public String getFull() {
        return full;
    }

    public String getRegular() {
        return regular;
    }

    public String getSmall() {
        return small;
    }

    public String getThumb() {
        return thumb;
    }

    @Override
    public String toString() {
        return "PhotoUrls{" +
                "raw='" + raw + '\'' +
                ", full='" + full + '\'' +
                ", regular='" + regular + '\'' +
                ", small='" + small + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }

    public static final class Builder {
        private String raw;
        private String full;
        private String regular;
        private String small;
        private String thumb;

        public Builder() {
        }

        public Builder raw(String val) {
            raw = val;
            return this;
        }

        public Builder full(String val) {
            full = val;
            return this;
        }

        public Builder regular(String val) {
            regular = val;
            return this;
        }

        public Builder small(String val) {
            small = val;
            return this;
        }

        public Builder thumb(String val) {
            thumb = val;
            return this;
        }

        public PhotoUrls build() {
            return new PhotoUrls(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.raw);
        dest.writeString(this.full);
        dest.writeString(this.regular);
        dest.writeString(this.small);
        dest.writeString(this.thumb);
    }

    protected PhotoUrls(Parcel in) {
        this.raw = in.readString();
        this.full = in.readString();
        this.regular = in.readString();
        this.small = in.readString();
        this.thumb = in.readString();
    }

    public static final Parcelable.Creator<PhotoUrls> CREATOR = new Parcelable.Creator<PhotoUrls>() {
        @Override
        public PhotoUrls createFromParcel(Parcel source) {
            return new PhotoUrls(source);
        }

        @Override
        public PhotoUrls[] newArray(int size) {
            return new PhotoUrls[size];
        }
    };
}
