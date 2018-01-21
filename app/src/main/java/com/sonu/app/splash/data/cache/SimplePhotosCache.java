package com.sonu.app.splash.data.cache;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestGenerator;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

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

public abstract class SimplePhotosCache implements PhotosCache {

    private RequestHandler requestHandler;
    private List<Photo> cachedPhotos;
    private int curPage = 1;

    // volatile to give thread safety
    private volatile STATE state;

    private enum STATE {NORMAL, FETCHING}

    public SimplePhotosCache(RequestHandler requestHandler) {
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

    protected abstract String getApiEndpoint();
    protected abstract String getTag();

    private List<Photo> getMorePhotosAct() throws IOException, UnsplashApiException {
        Log.d(getTag(), "getMorePhotosAct():called");

        state = STATE.FETCHING;

        List<Photo> photoList;

        try {

            // giving page number to fetch
            String url = String.format(getApiEndpoint(), curPage);

            Request request = RequestGenerator.get(url);

            String body = requestHandler.request(request).string();
            Log.i(getTag(), "getMorePhotosAct():response-body:"+body);

            JsonArray jsonArray = new JsonParser().parse(body).getAsJsonArray();
            Log.i(getTag(), "getMorePhotosAct():response-body-json:"+jsonArray);

            photoList = new ArrayList<>();

            for (JsonElement element : jsonArray) {

                photoList.add(UnsplashJsonUtils.getPhotoObj(element));
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
