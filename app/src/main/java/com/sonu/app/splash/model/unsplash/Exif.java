package com.sonu.app.splash.model.unsplash;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class Exif implements Parcelable {

    private String make, model, exposureTime, aperture, focalLength;
    private int iso;

    private Exif(Builder builder) {
        make = builder.make;
        model = builder.model;
        exposureTime = builder.exposureTime;
        aperture = builder.aperture;
        focalLength = builder.focalLength;
        iso = builder.iso;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getExposureTime() {
        return exposureTime;
    }

    public String getAperture() {
        return aperture;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public int getIso() {
        return iso;
    }

    @Override
    public String toString() {
        return "Exif{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", exposureTime='" + exposureTime + '\'' +
                ", aperture='" + aperture + '\'' +
                ", focalLength='" + focalLength + '\'' +
                ", iso=" + iso +
                '}';
    }

    public static final class Builder {
        private String make;
        private String model;
        private String exposureTime;
        private String aperture;
        private String focalLength;
        private int iso;

        public Builder() {
        }

        public Builder make(String val) {
            make = val;
            return this;
        }

        public Builder model(String val) {
            model = val;
            return this;
        }

        public Builder exposureTime(String val) {
            exposureTime = val;
            return this;
        }

        public Builder aperture(String val) {
            aperture = val;
            return this;
        }

        public Builder focalLength(String val) {
            focalLength = val;
            return this;
        }

        public Builder iso(int val) {
            iso = val;
            return this;
        }

        public Exif build() {
            return new Exif(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.make);
        dest.writeString(this.model);
        dest.writeString(this.exposureTime);
        dest.writeString(this.aperture);
        dest.writeString(this.focalLength);
        dest.writeInt(this.iso);
    }

    protected Exif(Parcel in) {
        this.make = in.readString();
        this.model = in.readString();
        this.exposureTime = in.readString();
        this.aperture = in.readString();
        this.focalLength = in.readString();
        this.iso = in.readInt();
    }

    public static final Parcelable.Creator<Exif> CREATOR = new Parcelable.Creator<Exif>() {
        @Override
        public Exif createFromParcel(Parcel source) {
            return new Exif(source);
        }

        @Override
        public Exif[] newArray(int size) {
            return new Exif[size];
        }
    };
}
