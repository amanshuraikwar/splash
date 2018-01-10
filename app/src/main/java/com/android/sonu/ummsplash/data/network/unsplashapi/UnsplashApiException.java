package com.android.sonu.ummsplash.data.network.unsplashapi;

import java.io.IOException;

/**
 * Created by amanshuraikwar on 30/12/17.
 */

public class UnsplashApiException extends Exception {

    public static final int CODE_API_LIMIT_EXCEEDED = 1000;

    private int code;
    private int apiRateLimit;

    public UnsplashApiException(int code, int apiRateLimit) {
        super();
        this.code = code;
        this.apiRateLimit = apiRateLimit;
    }

    public int getCode() {
        return code;
    }

    public int getApiRateLimit() {
        return apiRateLimit;
    }
}
