package com.sonu.app.splash.data.network;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestGenerator;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UnsplashJsonUtils;

import java.io.IOException;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
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
        return Observable.fromCallable(new Callable<Photo>() {
            @Override
            public Photo call() throws Exception {
                return getPhotoAct(photoId);
            }
        });
    }

    private Photo getPhotoAct(String photoId)
            throws IOException, UnsplashApiException {

        try {

            // giving page number to fetch
            String url = String.format(ApiEndpoints.GET_PHOTO, photoId);

            Request request = RequestGenerator.get(url);

            String body = requestHandler.request(request).string();
            Log.i(TAG, "getPhotoAct:response-body:"+body);

            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

            Log.i(TAG, "getPhotoAct:response-body-json:"+jsonObject);

            return UnsplashJsonUtils.buildPhotoObj(jsonObject);

        } catch (IOException | UnsplashApiException e) {
            throw e;
        }
    }

    @Override
    public Observable<User> getUserDescription(final String username) {
        return Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return getUserAct(username);
            }
        });
    }

    private User getUserAct(String username)
            throws IOException, UnsplashApiException {

        try {

            // giving page number to fetch
            String url = String.format(ApiEndpoints.GET_USER, username);

            Request request = RequestGenerator.get(url);

            String body = requestHandler.request(request).string();
            Log.i(TAG, "getPhotoAct:response-body:"+body);

            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

            Log.i(TAG, "getPhotoAct:response-body-json:"+jsonObject);

            return UnsplashJsonUtils.buildUserObj(jsonObject);

        } catch (IOException | UnsplashApiException e) {
            throw e;
        }
    }
}
