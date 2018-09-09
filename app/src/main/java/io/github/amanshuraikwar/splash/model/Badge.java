package io.github.amanshuraikwar.splash.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class Badge implements Parcelable {

    private String title;

    private boolean primary;

    private String slug, link;

    private Badge(Builder builder) {
        title = builder.title;
        primary = builder.primary;
        slug = builder.slug;
        link = builder.link;
    }

    public String getTitle() {
        return title;
    }

    public boolean isPrimary() {
        return primary;
    }

    public String getSlug() {
        return slug;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "title='" + title + '\'' +
                ", primary=" + primary +
                ", slug='" + slug + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public static final class Builder {
        private String title;
        private boolean primary;
        private String slug;
        private String link;

        public Builder(String title) {

            this.title = title;
        }

        public Builder primary(boolean val) {
            primary = val;
            return this;
        }

        public Builder slug(String val) {
            slug = val;
            return this;
        }

        public Builder link(String val) {
            link = val;
            return this;
        }

        public Badge build() {
            return new Badge(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeByte(this.primary ? (byte) 1 : (byte) 0);
        dest.writeString(this.slug);
        dest.writeString(this.link);
    }

    protected Badge(Parcel in) {
        this.title = in.readString();
        this.primary = in.readByte() != 0;
        this.slug = in.readString();
        this.link = in.readString();
    }

    public static final Parcelable.Creator<Badge> CREATOR = new Parcelable.Creator<Badge>() {
        @Override
        public Badge createFromParcel(Parcel source) {
            return new Badge(source);
        }

        @Override
        public Badge[] newArray(int size) {
            return new Badge[size];
        }
    };
}
