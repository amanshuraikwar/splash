package com.sonu.app.splash.data.network.unsplashapi;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public interface ApiEndpoints {

    int PER_PAGE = 30;

    String BASE_ENDPOINT = "https://api.unsplash.com/";

    String GET_ALL_PHOTOS = BASE_ENDPOINT + "photos?page=%s&order_by=%s&per_page="+PER_PAGE;

    String GET_CURATED_PHOTOS = BASE_ENDPOINT + "photos/curated?page=%s&order_by=%s&per_page="+PER_PAGE;

    String GET_PHOTO_DESCRIPTION = BASE_ENDPOINT + "photos/%s";

    String SEARCH_PHOTOS = BASE_ENDPOINT + "search/photos?query=%s&page=%s&per_page="+PER_PAGE;

    String GET_USER_DESCRIPTION = BASE_ENDPOINT + "users/%s";

    String GET_USER_PHOTOS = BASE_ENDPOINT + "users/%s/photos?page=%s&order_by=%s&per_page="+PER_PAGE;

    String SEARCH_USERS = BASE_ENDPOINT + "search/users?query=%s&page=%s&per_page="+PER_PAGE;

    String GET_ALL_COLLECTIONS = BASE_ENDPOINT + "collections?page=%s&per_page="+PER_PAGE;

    String GET_FEATURED_COLLECTIONS = BASE_ENDPOINT + "collections/featured?page=%s&per_page="+PER_PAGE;

    String GET_CURATED_COLLECTIONS = BASE_ENDPOINT + "collections/curated?page=%s&per_page="+PER_PAGE;

    String SEARCH_COLLECTIONS = BASE_ENDPOINT + "search/collections?query=%s&page=%s&per_page="+PER_PAGE;

    String COLLECTION_DESCRIPTION = BASE_ENDPOINT + "collections/%s";

    String GET_COLLECTION_PHOTOS = BASE_ENDPOINT + "collections/%s/photos?page=%s&per_page="+PER_PAGE;
}
