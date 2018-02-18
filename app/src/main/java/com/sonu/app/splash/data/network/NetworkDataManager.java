package com.sonu.app.splash.data.network;

import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.PhotoStats;
import com.sonu.app.splash.model.unsplash.User;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface NetworkDataManager {

    Observable<Photo> getPhotoDescription(String photoId);

    Observable<User> getUserDescription(String username);

    Observable<PhotoStats> getPhotoStats(String photoId);
}
