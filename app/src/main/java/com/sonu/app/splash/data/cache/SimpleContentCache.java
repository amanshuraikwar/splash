package com.sonu.app.splash.data.cache;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.sonu.app.splash.data.network.unsplashapi.RequestGenerator;
import com.sonu.app.splash.data.network.unsplashapi.RequestHandler;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.photo.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import okhttp3.Request;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public abstract class SimpleContentCache<DataModel> implements ContentCache<DataModel> {

    private RequestHandler requestHandler;
    private List<DataModel> cachedContent;
    private int curPage = 1;

    // volatile to give thread safety
    private volatile STATE state;

    private enum STATE {NORMAL, FETCHING}

    SimpleContentCache(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        cachedContent = new ArrayList<>();
        setState(STATE.NORMAL);
    }

    // synchronized to give thread safety
    @Override
    public synchronized Observable<List<DataModel>> getMoreContent() {
        return Observable.fromCallable(new Callable<List<DataModel>>() {
            @Override
            public List<DataModel> call() throws Exception {

                if (state == STATE.FETCHING) {
                    return Collections.emptyList();
                }

                return getMoreContentAct();
            }
        });
    }

    @Override
    public synchronized Observable<List<DataModel>> getCachedContent() {
        return Observable.fromCallable(new Callable<List<DataModel>>() {
            @Override
            public List<DataModel> call() throws Exception {
                return cachedContent;
            }
        });
    }

    @Override
    public synchronized boolean isCacheEmpty() {
        return cachedContent.size() == 0;
    }

    @Override
    public synchronized void resetCache() {

        cachedContent = new ArrayList<>();
        curPage = 1;
        state = STATE.NORMAL;
    }

    abstract String getApiEndpoint();
    abstract String getTag();
    abstract DataModel getDataModelFromJson(JsonElement element);

    private List<DataModel> getMoreContentAct() throws IOException, UnsplashApiException {
        Log.d(getTag(), "getMoreContentAct():called");

        setState(STATE.FETCHING);

        List<DataModel> contentList;

        try {

            // giving page number to fetch
            String url = String.format(getApiEndpoint(), curPage);

            Request request = RequestGenerator.get(url);

            String body = requestHandler.request(request).string();
            Log.i(getTag(), "getMoreContentAct():response-body:"+body);

            JsonArray jsonArray = getMeaningFullData(body).getAsJsonArray();
            Log.i(getTag(), "getMoreContentAct():response-body-json:"+jsonArray);

            contentList = new ArrayList<>();

            for (JsonElement element : jsonArray) {

                contentList.add(getDataModelFromJson(element));
            }

            // updating cache
            updateCache(contentList);
        } catch (IOException | UnsplashApiException e) {
            setState(STATE.NORMAL);
            throw e;
        }

        setState(STATE.NORMAL);

        return contentList;
    }

    protected JsonElement getMeaningFullData(String body) throws JsonParseException {

        return new JsonParser().parse(body);
    }

    private synchronized void setState(STATE state) {
        this.state = state;
    }

    private synchronized STATE getState() {
        return state;
    }

    private synchronized void updateCache(List<DataModel> photoList) {
        cachedContent.addAll(photoList);
        curPage += 1;
    }
}
