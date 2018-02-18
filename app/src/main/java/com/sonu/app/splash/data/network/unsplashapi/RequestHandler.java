package com.sonu.app.splash.data.network.unsplashapi;

import android.util.Log;

import com.sonu.app.splash.util.LogUtils;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.exceptions.UndeliverableException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RequestHandler {

    private static final String TAG = LogUtils.getLogTag(RequestHandler.class);

    private OkHttpClient okHttpClient;

    @Inject
    public RequestHandler(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public ResponseBody request(Request request) throws IOException, UnsplashApiException, UndeliverableException {

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody body = response.body();

        Log.i(TAG, "request:response-body="+body);

        if (response.isSuccessful()) {
            return body;
        } else {
            handleUnsuccessfulResponse(response);
            return null;
        }
    }

    private void handleUnsuccessfulResponse(Response response)
            throws UnsplashApiException {

        try {
            int apiLimitRemaining =
                    Integer.parseInt(response.headers().get("X-Ratelimit-Remaining"));
            int apiRateLimit =
                    Integer.parseInt(response.headers().get("X-Ratelimit-Limit"));

            if (apiLimitRemaining == 0) {
                throw new UnsplashApiException(
                        UnsplashApiException.CODE_API_LIMIT_EXCEEDED,
                        apiRateLimit);
            } else {
                throw new RuntimeException(response.message());
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(response.message());
        }
    }
}