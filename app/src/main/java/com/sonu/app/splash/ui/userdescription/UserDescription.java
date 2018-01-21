package com.sonu.app.splash.ui.userdescription;

import android.os.Parcel;
import android.os.Parcelable;

import com.sonu.app.splash.ui.photodescription.PhotoDescription;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public class UserDescription implements Parcelable {

    private String
            id,
            updatedAt,
            username,
            name,
            portfolioUrl,
            bio,
            location,
            badgeTitle,
            badgeLink,
            profileImageUrl;

    private String[] tags;

    private int totalLikes, totalPhotos, totalCollections, followersCount, downloads;


    public String getId() {
        return id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getBadgeTitle() {
        return badgeTitle;
    }

    public String getBadgeLink() {
        return badgeLink;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String[] getTags() {
        return tags;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public int getTotalCollections() {
        return totalCollections;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getDownloads() {
        return downloads;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public UserDescription(Parcel parcel) {

        id = parcel.readString();
        updatedAt = parcel.readString();
        username = parcel.readString();
        name = parcel.readString();
        portfolioUrl = parcel.readString();
        bio = parcel.readString();
        location = parcel.readString();
        badgeTitle = parcel.readString();
        badgeLink = parcel.readString();
        profileImageUrl = parcel.readString();
        tags = parcel.createStringArray();
        totalLikes = parcel.readInt();
        totalPhotos = parcel.readInt();
        totalCollections = parcel.readInt();
        followersCount = parcel.readInt();
        downloads = parcel.readInt();
    }

    private UserDescription(Builder builder) {
        id = builder.id;
        updatedAt = builder.updatedAt;
        username = builder.username;
        name = builder.name;
        portfolioUrl = builder.portfolioUrl;
        bio = builder.bio;
        location = builder.location;
        badgeTitle = builder.badgeTitle;
        badgeLink = builder.badgeLink;
        profileImageUrl = builder.profileImageUrl;
        tags = builder.tags;
        totalLikes = builder.totalLikes;
        totalPhotos = builder.totalPhotos;
        totalCollections = builder.totalCollections;
        followersCount = builder.followersCount;
        downloads = builder.downloads;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(updatedAt);
        parcel.writeString(username);
        parcel.writeString(name);
        parcel.writeString(portfolioUrl);
        parcel.writeString(bio);
        parcel.writeString(location);
        parcel.writeString(badgeTitle);
        parcel.writeString(badgeLink);
        parcel.writeString(profileImageUrl);
        parcel.writeStringArray(tags);
        parcel.writeInt(totalLikes);
        parcel.writeInt(totalPhotos);
        parcel.writeInt(totalCollections);
        parcel.writeInt(followersCount);
        parcel.writeInt(downloads);
    }

    public static final Creator<UserDescription> CREATOR =
            new Creator<UserDescription>() {
                public UserDescription createFromParcel(Parcel in) {
                    return new UserDescription(in);
                }

                public UserDescription[] newArray(int size) {
                    return new UserDescription[size];
                }
            };

    public static class Builder {

        private String
                id = "",
                updatedAt = "",
                username = "",
                name = "",
                portfolioUrl = "",
                bio = "",
                location = "",
                badgeTitle = "",
                badgeLink = "",
                profileImageUrl = "";

        private String[] tags = new String[]{};

        private int totalLikes, totalPhotos, totalCollections, followersCount, downloads;

        public Builder(String id) {
            this.id = id;
        }

        public void updatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public void username(String username) {
            this.username = username;
        }

        public void name(String name) {
            this.name = name;
        }

        public void portfolioUrl(String portfolioUrl) {
            this.portfolioUrl = portfolioUrl;
        }

        public void bio(String bio) {
            this.bio = bio;
        }

        public void location(String location) {
            this.location = location;
        }

        public void badgeTitle(String badgeTitle) {
            this.badgeTitle = badgeTitle;
        }

        public void badgeLink(String badgeLink) {
            this.badgeLink = badgeLink;
        }

        public void profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }

        public void tags(String[] tags) {
            this.tags = tags;
        }

        public void totalLikes(int totalLikes) {
            this.totalLikes = totalLikes;
        }

        public void totalPhotos(int totalPhotos) {
            this.totalPhotos = totalPhotos;
        }

        public void totalCollections(int totalCollections) {
            this.totalCollections = totalCollections;
        }

        public void followersCount(int followersCount) {
            this.followersCount = followersCount;
        }

        public void downloads(int downloads) {
            this.downloads = downloads;
        }

        public UserDescription build() {
            return new UserDescription(this);
        }
    }
}
