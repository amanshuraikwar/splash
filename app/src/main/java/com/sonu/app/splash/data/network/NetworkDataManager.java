package com.sonu.app.splash.data.network;

import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photodescription.PhotoDescription;
import com.sonu.app.splash.ui.userdescription.UserDescription;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface NetworkDataManager {

    Observable<PhotoDescription> getPhotoDescription(String photoId);

    Observable<UserDescription> getUserDescription(String username);
}
