package com.sonu.app.splash.ui.photodescription;

import com.sonu.app.splash.model.unsplash.Exif;
import com.sonu.app.splash.model.unsplash.Location;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.ui.list.ListItem;

import java.util.List;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

public interface PhotoDescriptionContract {

    interface View extends BaseView {
        String getCurPhotoId();
        Photo getCurPhoto();
        void displayItems(List<ListItem> listItems);
        void showError();
        void hideLoading();
        void showLoading();
        void setFavActive();
        void setFavInactive();
        ListItem getLocationListItem(Location location);
        ListItem getDescriptionTextListItem(String description);
        ListItem getPhotoUserListItem(User user);
        ListItem getPhotoInfoListItem(String photoId, Exif photoExif);
    }

    interface Presenter extends BasePresenter<View> {
        void getData();
        void downloadPhoto(Photo photo);
        void onAddToFavClick();
    }
}
