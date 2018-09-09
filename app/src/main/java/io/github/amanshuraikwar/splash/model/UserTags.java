package io.github.amanshuraikwar.splash.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class UserTags implements Parcelable {

    private String[] custom;
    private String[] aggregated;

    private UserTags(Builder builder) {
        custom = builder.custom;
        aggregated = builder.aggregated;
    }

    public String[] getCustom() {
        return custom;
    }

    public String[] getAggregated() {
        return aggregated;
    }

    @Override
    public String toString() {
        return "UserTags{" +
                "custom=" + Arrays.toString(custom) +
                ", aggregated=" + Arrays.toString(aggregated) +
                '}';
    }

    public static final class Builder {
        private String[] custom = new String[]{};
        private String[] aggregated = new String[]{};

        public Builder() {
        }

        public Builder custom(String[] val) {
            custom = val;
            return this;
        }

        public Builder aggregated(String[] val) {
            aggregated = val;
            return this;
        }

        public UserTags build() {
            return new UserTags(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.custom);
        dest.writeStringArray(this.aggregated);
    }

    protected UserTags(Parcel in) {
        this.custom = in.createStringArray();
        this.aggregated = in.createStringArray();
    }

    public static final Parcelable.Creator<UserTags> CREATOR = new Parcelable.Creator<UserTags>() {
        @Override
        public UserTags createFromParcel(Parcel source) {
            return new UserTags(source);
        }

        @Override
        public UserTags[] newArray(int size) {
            return new UserTags[size];
        }
    };
}
