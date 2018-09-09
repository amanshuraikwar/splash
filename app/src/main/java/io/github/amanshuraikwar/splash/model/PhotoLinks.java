package io.github.amanshuraikwar.splash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class PhotoLinks implements Parcelable {

    private String self, html, download;

    @SerializedName("download_location")
    private String downloadLocation;

    private PhotoLinks(Builder builder) {
        self = builder.self;
        html = builder.html;
        download = builder.download;
        downloadLocation = builder.downloadLocation;
    }

    public String getSelf() {
        return self;
    }

    public String getHtml() {
        return html;
    }

    public String getDownload() {
        return download;
    }

    public String getDownloadLocation() {
        return downloadLocation;
    }

    @Override
    public String toString() {
        return "PhotoLinks{" +
                "self='" + self + '\'' +
                ", html='" + html + '\'' +
                ", download='" + download + '\'' +
                ", downloadLocation='" + downloadLocation + '\'' +
                '}';
    }

    public static final class Builder {
        private String self;
        private String html;
        private String download;
        private String downloadLocation;

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

        public Builder download(String val) {
            download = val;
            return this;
        }

        public Builder downloadLocation(String val) {
            downloadLocation = val;
            return this;
        }

        public PhotoLinks build() {
            return new PhotoLinks(this);
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
        dest.writeString(this.download);
        dest.writeString(this.downloadLocation);
    }

    protected PhotoLinks(Parcel in) {
        this.self = in.readString();
        this.html = in.readString();
        this.download = in.readString();
        this.downloadLocation = in.readString();
    }

    public static final Parcelable.Creator<PhotoLinks> CREATOR = new Parcelable.Creator<PhotoLinks>() {
        @Override
        public PhotoLinks createFromParcel(Parcel source) {
            return new PhotoLinks(source);
        }

        @Override
        public PhotoLinks[] newArray(int size) {
            return new PhotoLinks[size];
        }
    };
}
