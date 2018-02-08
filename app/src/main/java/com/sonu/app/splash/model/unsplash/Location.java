package com.sonu.app.splash.model.unsplash;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class Location implements Parcelable {

    private String title, name, city, country;
    private double lat, lon;

    private Location(Builder builder) {
        title = builder.title;
        name = builder.name;
        city = builder.city;
        country = builder.country;
        lat = builder.lat;
        lon = builder.lon;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "Location{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public static final class Builder {
        private String title;
        private String name;
        private String city;
        private String country;
        private double lat;
        private double lon;

        public Builder() {
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder country(String val) {
            country = val;
            return this;
        }

        public Builder lat(double val) {
            lat = val;
            return this;
        }

        public Builder lon(double val) {
            lon = val;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lon);
    }

    protected Location(Parcel in) {
        this.title = in.readString();
        this.name = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.lat = in.readDouble();
        this.lon = in.readDouble();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
