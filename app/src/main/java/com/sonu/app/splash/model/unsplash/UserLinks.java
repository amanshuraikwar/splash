package com.sonu.app.splash.model.unsplash;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class UserLinks implements Parcelable {

    private String self, html, photos, likes, portfolio, following, followers;

    private UserLinks(Builder builder) {
        self = builder.self;
        html = builder.html;
        photos = builder.photos;
        likes = builder.likes;
        portfolio = builder.portfolio;
        following = builder.following;
        followers = builder.followers;
    }

    public String getSelf() {
        return self;
    }

    public String getHtml() {
        return html;
    }

    public String getPhotos() {
        return photos;
    }

    public String getLikes() {
        return likes;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public String getFollowing() {
        return following;
    }

    public String getFollowers() {
        return followers;
    }

    @Override
    public String toString() {
        return "UserLinks{" +
                "self='" + self + '\'' +
                ", html='" + html + '\'' +
                ", photos='" + photos + '\'' +
                ", likes='" + likes + '\'' +
                ", portfolio='" + portfolio + '\'' +
                ", following='" + following + '\'' +
                ", followers='" + followers + '\'' +
                '}';
    }

    public static final class Builder {
        private String self;
        private String html;
        private String photos;
        private String likes;
        private String portfolio;
        private String following;
        private String followers;

        public Builder() {
        }

        public Builder self(String val) {
            self = val;
            return this;
        }

        public Builder html(String val) {
            html = val;
            return this;
        }

        public Builder photos(String val) {
            photos = val;
            return this;
        }

        public Builder likes(String val) {
            likes = val;
            return this;
        }

        public Builder portfolio(String val) {
            portfolio = val;
            return this;
        }

        public Builder following(String val) {
            following = val;
            return this;
        }

        public Builder followers(String val) {
            followers = val;
            return this;
        }

        public UserLinks build() {
            return new UserLinks(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.self);
        dest.writeString(this.html);
        dest.writeString(this.photos);
        dest.writeString(this.likes);
        dest.writeString(this.portfolio);
        dest.writeString(this.following);
        dest.writeString(this.followers);
    }

    protected UserLinks(Parcel in) {
        this.self = in.readString();
        this.html = in.readString();
        this.photos = in.readString();
        this.likes = in.readString();
        this.portfolio = in.readString();
        this.following = in.readString();
        this.followers = in.readString();
    }

    public static final Parcelable.Creator<UserLinks> CREATOR = new Parcelable.Creator<UserLinks>() {
        @Override
        public UserLinks createFromParcel(Parcel source) {
            return new UserLinks(source);
        }

        @Override
        public UserLinks[] newArray(int size) {
            return new UserLinks[size];
        }
    };
}
