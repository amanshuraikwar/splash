package com.android.sonu.ummsplash.data.cache;

import android.util.Log;

import com.android.sonu.ummsplash.data.network.unsplashapi.ApiEndpoints;
import com.android.sonu.ummsplash.data.network.unsplashapi.RequestGenerator;
import com.android.sonu.ummsplash.data.network.unsplashapi.RequestHandler;
import com.android.sonu.ummsplash.data.network.unsplashapi.UnsplashApiException;
import com.android.sonu.ummsplash.ui.photo.Photo;
import com.android.sonu.ummsplash.util.LogUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.Request;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class NewPhotosCache implements PhotosCache {

    private static final String TAG = LogUtils.getLogTag(NewPhotosCache.class);

    private RequestHandler requestHandler;
    private List<Photo> cachedPhotos;
    private int curPage = 1;

    // volatile to give thread safety
    private volatile STATE state;

    private enum STATE {NORMAL, FETCHING}

    @Inject
    public NewPhotosCache(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        cachedPhotos = new ArrayList<>();
        state = STATE.NORMAL;
    }

    // synchronized to give thread safety
    @Override
    public synchronized Observable<List<Photo>> getMorePhotos() {
        return Observable.fromCallable(new Callable<List<Photo>>() {
            @Override
            public List<Photo> call() throws Exception {

                if (state == STATE.FETCHING) {
                    return Collections.emptyList();
                }

                return getMorePhotosAct();
            }
        });
    }

    @Override
    public Observable<List<Photo>> getCachedPhotos() {
        return Observable.fromCallable(new Callable<List<Photo>>() {
            @Override
            public List<Photo> call() throws Exception {
                return cachedPhotos;
            }
        });
    }

    @Override
    public boolean isCacheEmpty() {
        return cachedPhotos.size() == 0;
    }

    private List<Photo> getMorePhotosAct() throws IOException, UnsplashApiException {
        Log.d(TAG, "getMorePhotosAct():called");

        state = STATE.FETCHING;

        List<Photo> photoList;

        try {

            // giving page number to fetch
            String url = String.format(ApiEndpoints.GET_NEW_PHOTOS, curPage);

            Request request = RequestGenerator.get(url);

            String body = requestHandler.request(request).string();
            Log.i(TAG, "getMorePhotosAct():response-body:"+body);

            JsonArray jsonArray = new JsonParser().parse(body).getAsJsonArray();
            Log.i(TAG, "getMorePhotosAct():response-body-json:"+jsonArray);

            photoList = new ArrayList<>();

            for (JsonElement element : jsonArray) {

                Photo.Builder builder =
                        new Photo.Builder(element.getAsJsonObject().get("id").getAsString());

                builder.width(element.getAsJsonObject().get("width").getAsInt());
                builder.height(element.getAsJsonObject().get("height").getAsInt());

//            if (element.getAsJsonObject().get("description") != null) {
//                builder.description(element.getAsJsonObject().get("description").getAsString());
//            }

                builder.likes(element.getAsJsonObject().get("likes").getAsInt());
                builder.color(element.getAsJsonObject().get("color").getAsString());

                builder.artistName(
                        element
                                .getAsJsonObject()
                                .get("user")
                                .getAsJsonObject().get("name").getAsString()
                );

                builder.artistProfileImageUrl(
                        element
                                .getAsJsonObject()
                                .get("user")
                                .getAsJsonObject()
                                .get("profile_image")
                                .getAsJsonObject()
                                .get("medium").getAsString()
                );

                builder.urlRegular(
                        element
                                .getAsJsonObject()
                                .get("urls")
                                .getAsJsonObject().get("regular").getAsString());

                builder.urlThumb(
                        element
                                .getAsJsonObject()
                                .get("urls")
                                .getAsJsonObject()
                                .get("thumb")
                                .getAsString());

                builder.linkDownload(
                        element
                                .getAsJsonObject()
                                .get("links")
                                .getAsJsonObject().get("download").getAsString());

                photoList.add(builder.build());
            }

            // updating cache
            updateCache(photoList);
        } catch (IOException | UnsplashApiException e) {
            state = STATE.NORMAL;
            throw e;
        }

        state = STATE.NORMAL;

        return photoList;
    }

    private void updateCache(List<Photo> photoList) {
        cachedPhotos.addAll(photoList);
        curPage += 1;
    }
}
