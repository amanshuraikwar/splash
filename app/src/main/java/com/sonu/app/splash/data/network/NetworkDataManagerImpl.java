package com.sonu.app.splash.data.network;

import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.data.network.unsplashapi.RequestGenerator;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.photodescription.PhotoDescription;
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
    public Observable<PhotoDescription> getPhotoDescription(final String photoId) {
        return Observable.fromCallable(new Callable<PhotoDescription>() {
            @Override
            public PhotoDescription call() throws Exception {
                return getPhotoDescriptionAct(photoId);
            }
        });
    }

    private PhotoDescription getPhotoDescriptionAct(String photoId)
            throws IOException, UnsplashApiException {

        try {

            // giving page number to fetch
            String url = String.format(ApiEndpoints.GET_PHOTO_DESCRIPTION, photoId);

            Request request = RequestGenerator.get(url);

            String body = requestHandler.request(request).string();
            Log.i(TAG, "getPhotoDescriptionAct:response-body:"+body);

            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

            Log.i(TAG, "getPhotoDescriptionAct:response-body-json:"+jsonObject);

            return UnsplashJsonUtils.getPhotoDescriptionObj(jsonObject);

        } catch (IOException | UnsplashApiException e) {
            throw e;
        }
    }
}
