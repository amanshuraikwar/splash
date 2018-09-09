package io.github.amanshuraikwar.splash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class User implements Parcelable {

    private String id, updatedAt;

    private int numericId;

    private String username, name;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("twitter_username")
    private String twitterUsername;

    @SerializedName("portfolio_url")
    private String portfolioUrl;

    private String bio,
            location;

    @SerializedName("total_likes")
    private int totalLikes;

    @SerializedName("total_photos")
    private int totalPhotos;

    @SerializedName("total_collections")
    private int totalCollections;

    @SerializedName("following_count")
    private int followingCount;

    @SerializedName("followers_count")
    private int followersCount;

    private int downloads;

    @SerializedName("profile_image")
    private ProfileImage profileImage;

    private Badge badge;

    private UserTags userTags;

    @SerializedName("links")
    private UserLinks userLinks;

    private User(Builder builder) {
        id = builder.id;
        updatedAt = builder.updatedAt;
        numericId = builder.numericId;
        username = builder.username;
        name = builder.name;
        firstName = builder.firstName;
        lastName = builder.lastName;
        twitterUsername = builder.twitterUsername;
        portfolioUrl = builder.portfolioUrl;
        bio = builder.bio;
        location = builder.location;
        totalLikes = builder.totalLikes;
        totalPhotos = builder.totalPhotos;
        totalCollections = builder.totalCollections;
        followingCount = builder.followingCount;
        followersCount = builder.followersCount;
        downloads = builder.downloads;
        profileImage = builder.profileImage;
        badge = builder.badge;
        userTags = builder.userTags;
        userLinks = builder.userLinks;
    }

    public String getId() {
        return id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getNumericId() {
        return numericId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTwitterUsername() {
        return twitterUsername;
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

    public int getTotalLikes() {
        return totalLikes;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public int getTotalCollections() {
        return totalCollections;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getDownloads() {
        return downloads;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public Badge getBadge() {
        return badge;
    }

    public UserTags getUserTags() {
        return userTags;
    }

    public UserLinks getUserLinks() {
        return userLinks;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", numericId=" + numericId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", twitterUsername='" + twitterUsername + '\'' +
                ", portfolioUrl='" + portfolioUrl + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", totalLikes=" + totalLikes +
                ", totalPhotos=" + totalPhotos +
                ", totalCollections=" + totalCollections +
                ", followingCount=" + followingCount +
                ", followersCount=" + followersCount +
                ", downloads=" + downloads +
                ", profileImage=" + profileImage +
                ", badge=" + badge +
                ", userTags=" + userTags +
                ", userLinks=" + userLinks +
                '}';
    }

    public static final class Builder {
        private String id;
        private String updatedAt;
        private int numericId;
        private String username;
        private String name;
        private String firstName;
        private String lastName;
        private String twitterUsername;
        private String portfolioUrl;
        private String bio;
        private String location;
        private int totalLikes;
        private int totalPhotos;
        private int totalCollections;
        private int followingCount;
        private int followersCount;
        private int downloads;
        private ProfileImage profileImage;
        private Badge badge;
        private UserTags userTags;
        private UserLinks userLinks;

        public Builder(String id) {
            this.id = id;
        }

        public Builder updatedAt(String val) {
            updatedAt = val;
            return this;
        }

        public Builder numericId(int val) {
            numericId = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder twitterUsername(String val) {
            twitterUsername = val;
            return this;
        }

        public Builder portfolioUrl(String val) {
            portfolioUrl = val;
            return this;
        }

        public Builder bio(String val) {
            bio = val;
            return this;
        }

        public Builder location(String val) {
            location = val;
            return this;
        }

        public Builder totalLikes(int val) {
            totalLikes = val;
            return this;
        }

        public Builder totalPhotos(int val) {
            totalPhotos = val;
            return this;
        }

        public Builder totalCollections(int val) {
            totalCollections = val;
            return this;
        }

        public Builder followingCount(int val) {
            followingCount = val;
            return this;
        }

        public Builder followersCount(int val) {
            followersCount = val;
            return this;
        }

        public Builder downloads(int val) {
            downloads = val;
            return this;
        }

        public Builder profileImage(ProfileImage val) {
            profileImage = val;
            return this;
        }

        public Builder badge(Badge val) {
            badge = val;
            return this;
        }

        public Builder tags(UserTags val) {
            userTags = val;
            return this;
        }

        public Builder userLinks(UserLinks val) {
            userLinks = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.numericId);
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.twitterUsername);
        dest.writeString(this.portfolioUrl);
        dest.writeString(this.bio);
        dest.writeString(this.location);
        dest.writeInt(this.totalLikes);
        dest.writeInt(this.totalPhotos);
        dest.writeInt(this.totalCollections);
        dest.writeInt(this.followingCount);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.downloads);
        dest.writeParcelable(this.profileImage, flags);
        dest.writeParcelable(this.badge, flags);
        dest.writeParcelable(this.userTags, flags);
        dest.writeParcelable(this.userLinks, flags);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.updatedAt = in.readString();
        this.numericId = in.readInt();
        this.username = in.readString();
        this.name = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.twitterUsername = in.readString();
        this.portfolioUrl = in.readString();
        this.bio = in.readString();
        this.location = in.readString();
        this.totalLikes = in.readInt();
        this.totalPhotos = in.readInt();
        this.totalCollections = in.readInt();
        this.followingCount = in.readInt();
        this.followersCount = in.readInt();
        this.downloads = in.readInt();
        this.profileImage = in.readParcelable(ProfileImage.class.getClassLoader());
        this.badge = in.readParcelable(Badge.class.getClassLoader());
        this.userTags = in.readParcelable(UserTags.class.getClassLoader());
        this.userLinks = in.readParcelable(UserLinks.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
