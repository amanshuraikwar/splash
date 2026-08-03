package com.sonu.app.splash.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonu.app.splash.model.unsplash.Collection;

import org.junit.Test;

public class UnsplashJsonUtilsTest {

    @Test
    public void buildCollectionObj_parsesCurrentUnsplashStringIds() {
        JsonObject jsonObject = new JsonParser().parse(COLLECTION_JSON).getAsJsonObject();

        Collection collection = UnsplashJsonUtils.buildCollectionObj(jsonObject);

        assertEquals("YGntw1kjKq4", collection.getId());
        assertEquals("Current Collections", collection.getTitle());
        assertNotNull(collection.getCoverPhoto());
        assertNotNull(collection.getPreviewPhotos());
        assertEquals("OJimPBFyz6w", collection.getPreviewPhotos()[0].getId());
    }

    private static final String URLS_JSON = "{"
            + "\"raw\":\"https://images.unsplash.com/raw.jpg\","
            + "\"full\":\"https://images.unsplash.com/full.jpg\","
            + "\"regular\":\"https://images.unsplash.com/regular.jpg\","
            + "\"small\":\"https://images.unsplash.com/small.jpg\","
            + "\"thumb\":\"https://images.unsplash.com/thumb.jpg\""
            + "}";

    private static final String USER_JSON = "{"
            + "\"id\":\"user-id\","
            + "\"updated_at\":\"2026-07-24T22:15:10Z\","
            + "\"username\":\"author\","
            + "\"name\":\"Author Name\","
            + "\"total_likes\":1,"
            + "\"total_photos\":2,"
            + "\"total_collections\":3,"
            + "\"profile_image\":{"
            + "\"small\":\"https://example.com/small.jpg\","
            + "\"medium\":\"https://example.com/medium.jpg\","
            + "\"large\":\"https://example.com/large.jpg\""
            + "},"
            + "\"links\":{"
            + "\"self\":\"https://api.unsplash.com/users/author\","
            + "\"html\":\"https://unsplash.com/@author\","
            + "\"photos\":\"https://api.unsplash.com/users/author/photos\","
            + "\"likes\":\"https://api.unsplash.com/users/author/likes\","
            + "\"portfolio\":\"https://api.unsplash.com/users/author/portfolio\","
            + "\"following\":\"https://api.unsplash.com/users/author/following\","
            + "\"followers\":\"https://api.unsplash.com/users/author/followers\""
            + "}"
            + "}";

    private static final String PHOTO_JSON = "{"
            + "\"id\":\"photo-cover\","
            + "\"created_at\":\"2026-07-24T22:15:10Z\","
            + "\"updated_at\":\"2026-07-24T22:15:10Z\","
            + "\"width\":1200,"
            + "\"height\":800,"
            + "\"color\":\"#424242\","
            + "\"description\":null,"
            + "\"urls\":" + URLS_JSON + ","
            + "\"links\":{"
            + "\"self\":\"https://api.unsplash.com/photos/photo-cover\","
            + "\"html\":\"https://unsplash.com/photos/photo-cover\","
            + "\"download\":\"https://unsplash.com/photos/photo-cover/download\","
            + "\"download_location\":\"https://api.unsplash.com/photos/photo-cover/download\""
            + "},"
            + "\"likes\":12,"
            + "\"user\":" + USER_JSON
            + "}";

    private static final String COLLECTION_JSON = "{"
            + "\"id\":\"YGntw1kjKq4\","
            + "\"title\":\"Current Collections\","
            + "\"description\":null,"
            + "\"published_at\":\"2026-07-24T22:15:10Z\","
            + "\"updated_at\":\"2026-07-24T22:15:10Z\","
            + "\"featured\":false,"
            + "\"total_photos\":4,"
            + "\"private\":false,"
            + "\"share_key\":\"sample\","
            + "\"links\":{"
            + "\"self\":\"https://api.unsplash.com/collections/YGntw1kjKq4\","
            + "\"html\":\"https://unsplash.com/collections/YGntw1kjKq4\","
            + "\"photos\":\"https://api.unsplash.com/collections/YGntw1kjKq4/photos\","
            + "\"related\":\"https://api.unsplash.com/collections/YGntw1kjKq4/related\""
            + "},"
            + "\"user\":" + USER_JSON + ","
            + "\"cover_photo\":" + PHOTO_JSON + ","
            + "\"preview_photos\":[{"
            + "\"id\":\"OJimPBFyz6w\","
            + "\"urls\":" + URLS_JSON
            + "}]"
            + "}";
}
