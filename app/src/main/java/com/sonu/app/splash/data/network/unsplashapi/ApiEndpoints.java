package com.sonu.app.splash.data.network.unsplashapi;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public interface ApiEndpoints {

    String BASE_ENDPOINT = "https://api.unsplash.com/";

    String GET_NEW_PHOTOS = BASE_ENDPOINT + "photos?page=%s&per_page=30";

    String GET_PHOTO_DESCRIPTION = BASE_ENDPOINT + "photos/%s";

    String GET_USER_DESCRIPTION = BASE_ENDPOINT + "users/%s";

    String GET_USER_PHOTOS = BASE_ENDPOINT + "users/%s/photos?page=%s&per_page=30";
}
