package io.github.amanshuraikwar.splash.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class CollectionLinks implements Parcelable {

    private String self, html, photos, related;

    private CollectionLinks(Builder builder) {
        self = builder.self;
        html = builder.html;
        photos = builder.photos;
        related = builder.related;
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

    public String getRelated() {
        return related;
    }

    @Override
    public String toString() {
        return "CollectionLinks{" +
                "self='" + self + '\'' +
                ", html='" + html + '\'' +
                ", photos='" + photos + '\'' +
                ", related='" + related + '\'' +
                '}';
    }

    public static final class Builder {
        private String self;
        private String html;
        private String photos;
        private String related;

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

        public Builder related(String val) {
            related = val;
            return this;
        }

        public CollectionLinks build() {
            return new CollectionLinks(this);
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
        dest.writeString(this.related);
    }

    protected CollectionLinks(Parcel in) {
        this.self = in.readString();
        this.html = in.readString();
        this.photos = in.readString();
        this.related = in.readString();
    }

    public static final Parcelable.Creator<CollectionLinks> CREATOR = new Parcelable.Creator<CollectionLinks>() {
        @Override
        public CollectionLinks createFromParcel(Parcel source) {
            return new CollectionLinks(source);
        }

        @Override
        public CollectionLinks[] newArray(int size) {
            return new CollectionLinks[size];
        }
    };
}

