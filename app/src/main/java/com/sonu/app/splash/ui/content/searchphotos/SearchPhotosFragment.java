package com.sonu.app.splash.ui.content.searchphotos;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.sonu.app.splash.R;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.content.ContentFragment;
import com.sonu.app.splash.ui.content.allphotos.AllPhotosContract;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosFragment;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class SearchPhotosFragment
        extends ContentFragment<SearchPhotosContract.Presenter, Photo>
        implements SearchPhotosContract.View {

    private static final String TAG = LogUtils.getLogTag(SearchPhotosFragment.class);
    public static final String KEY_QUERY = "query";

    private PhotoOnClickListener photoOnClickListener =
            new PhotoOnClickListener() {
                @Override
                public void onDownloadBtnClick(Photo photo) {

                    Log.d(TAG, "onDownloadBtnClick:called");

                    getPresenter().downloadPhoto(photo);
                }

                @Override
                public void onClick(Photo photo, View itemView) {

                    Log.d(TAG, "onPhotoClick:called");

                    Intent i = new Intent(getActivity(), PhotoDescriptionActivity.class);
                    i.putExtra(PhotoDescriptionActivity.KEY_PHOTO, photo);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                                    Pair.create(itemView,
                                            getActivity().getString(R.string.transition_photo)),
                                    Pair.create(itemView,
                                            getActivity().getString(R.string.transition_photo_description_background)));

                    startActivity(i, options.toBundle());
                }
            };

    private String query;

    @Inject
    public SearchPhotosFragment() {
        // empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            query = getArguments().getString(KEY_QUERY);
        }
    }

    @Override
    protected ListItem getListItem(Photo photo) {
        PhotoListItem listItem = new PhotoListItem(photo);
        listItem.setOnClickListener(photoOnClickListener);
        return listItem;
    }

    @Override
    public String getCurSearchQuery() {
        return query;
    }

    public void setQuery(@NonNull String query) {
        this.query = query;
        getPresenter().onSearchQueryChanged();
    }
}
