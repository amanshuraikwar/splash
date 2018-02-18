package com.sonu.app.splash.ui.favs;

import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.ui.list.ListItem;

import java.util.List;

/**
 * Created by amanshuraikwar on 17/02/18.
 */

public interface FavsContract {

    interface View extends BaseView {

        void showLoading();
        void hideLoading();
        void showError();
        ListItem getListItem(Photo photo);
        ListItem getListItem(Collection collection);
        ListItem getListItem(User user);
        ListItem getFavHeaderListItem(String text);
        void displayData(List<ListItem> listItems);
    }

    interface Presenter extends BasePresenter<View> {

        void getData();
        void downloadPhoto(Photo photo);
    }
 }
