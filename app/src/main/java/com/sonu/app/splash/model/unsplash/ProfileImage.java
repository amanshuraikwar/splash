package com.sonu.app.splash.model.unsplash;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class ProfileImage implements Parcelable {

    private String small, meduim, large;

    private ProfileImage(Builder builder) {
        small = builder.small;
        meduim = builder.meduim;
        large = builder.large;
    }

    public String getSmall() {
        return small;
    }

    public String getMeduim() {
        return meduim;
    }

    public String getLarge() {
        return large;
    }

    @Override
    public String toString() {
        return "ProfileImage{" +
                "small='" + small + '\'' +
                ", meduim='" + meduim + '\'' +
                ", large='" + large + '\'' +
                '}';
    }

    public static final class Builder {
        private String small;
        private String meduim;
        private String large;

        public Builder() {
        }

        public Builder small(String val) {
            small = val;
            return this;
        }

        public Builder meduim(String val) {
            meduim = val;
            return this;
        }

        public Builder large(String val) {
            large = val;
            return this;
        }

        public ProfileImage build() {
            return new ProfileImage(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.small);
        dest.writeString(this.meduim);
        dest.writeString(this.large);
    }

    protected ProfileImage(Parcel in) {
        this.small = in.readString();
        this.meduim = in.readString();
        this.large = in.readString();
    }

    public static final Parcelable.Creator<ProfileImage> CREATOR = new Parcelable.Creator<ProfileImage>() {
        @Override
        public ProfileImage createFromParcel(Parcel source) {
            return new ProfileImage(source);
        }

        @Override
        public ProfileImage[] newArray(int size) {
            return new ProfileImage[size];
        }
    };
}
