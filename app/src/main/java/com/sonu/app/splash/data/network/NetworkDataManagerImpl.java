package com.sonu.app.splash.data.network;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestGenerator;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.PhotoStats;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.exceptions.UndeliverableException;
import okhttp3.Request;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class NetworkDataManagerImpl implements NetworkDataManager {

    private static final String TAG = LogUtils.getLogTag(NetworkDataManagerImpl.class);

    private RequestHandler requestHandler;

    @Inject
    public NetworkDataManagerImpl(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public Observable<Photo> getPhotoDescription(final String photoId) {
        return Observable.create(e -> {
            try {
                e.onNext(getPhotoAct(photoId));
                e.onComplete();
            } catch (Exception ex) {
                ex.printStackTrace();
                e.tryOnError(ex);
            }
        });
    }

    private Photo getPhotoAct(String photoId)
            throws IOException, UnsplashApiException, UndeliverableException {

        String url = String.format(ApiEndpoints.GET_PHOTO, photoId);

        Request request = RequestGenerator.get(url);

        String body = requestHandler.request(request).string();
        Log.i(TAG, "getPhotoAct:response-body:"+body);

        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

        Log.i(TAG, "getPhotoAct:response-body-json:"+jsonObject);

        return UnsplashJsonUtils.buildPhotoObj(jsonObject);
    }

    @Override
    public Observable<User> getUserDescription(final String username) {
        return Observable.create(e -> {
            try {
                e.onNext(getUserAct(username));
                e.onComplete();
            } catch (Exception ex) {
                ex.printStackTrace();
                e.tryOnError(ex);
            }
        });
    }

    private User getUserAct(String username)
            throws IOException, UnsplashApiException, UndeliverableException {

        String url = String.format(ApiEndpoints.GET_USER, username);

        Request request = RequestGenerator.get(url);

        String body = requestHandler.request(request).string();
        Log.i(TAG, "getPhotoAct:response-body:"+body);

        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

        Log.i(TAG, "getPhotoAct:response-body-json:"+jsonObject);

        return UnsplashJsonUtils.buildUserObj(jsonObject);
    }

    @Override
    public Observable<PhotoStats> getPhotoStats(String photoId) {
        return Observable.create(e -> {
            try {
                e.onNext(getPhotoStatsAct(photoId));
                e.onComplete();
            } catch (Exception ex) {
                ex.printStackTrace();
                e.tryOnError(ex);
            }
        });
    }

    private PhotoStats getPhotoStatsAct(String photoId)
            throws IOException, UnsplashApiException, UndeliverableException {

        String url = String.format(ApiEndpoints.GET_PHOTO_STATISTICS, photoId);

        Request request = RequestGenerator.get(url);

        String body = requestHandler.request(request).string();
        Log.i(TAG, "getPhotoAct:response-body:"+body);

        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

        Log.i(TAG, "getPhotoAct:response-body-json:"+jsonObject);

        return UnsplashJsonUtils.buildPhotoStatsObj(jsonObject);
    }
}
