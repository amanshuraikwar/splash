package com.sonu.app.splash.model.unsplash;

/**
 * Created by amanshuraikwar on 13/02/18.
 */

public class PhotoStats {

    private String id;
    private StatsValues<Integer> downloads, views, likes;

    private PhotoStats(Builder builder) {
        id = builder.id;
        downloads = builder.downloads;
        views = builder.views;
        likes = builder.likes;
    }

    public String getId() {
        return id;
    }

    public StatsValues<Integer> getDownloads() {
        return downloads;
    }

    public StatsValues<Integer> getViews() {
        return views;
    }

    public StatsValues<Integer> getLikes() {
        return likes;
    }


    public static final class Builder {
        private String id;
        private StatsValues<Integer> downloads;
        private StatsValues<Integer> views;
        private StatsValues<Integer> likes;

        public Builder(String id) {
            this.id = id;
        }

        public Builder downloads(StatsValues<Integer> val) {
            downloads = val;
            return this;
        }

        public Builder views(StatsValues<Integer> val) {
            views = val;
            return this;
        }

        public Builder likes(StatsValues<Integer> val) {
            likes = val;
            return this;
        }

        public PhotoStats build() {
            return new PhotoStats(this);
        }
    }
}
