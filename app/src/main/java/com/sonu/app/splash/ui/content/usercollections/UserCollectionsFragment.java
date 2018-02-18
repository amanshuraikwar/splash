package com.sonu.app.splash.ui.content.usercollections;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.sonu.app.splash.R;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.collection.CollectionListItem;
import com.sonu.app.splash.ui.collection.CollectionOnClickListener;
import com.sonu.app.splash.ui.collectiondecription.CollectionDescriptionActivity;
import com.sonu.app.splash.ui.content.ContentFragment;
import com.sonu.app.splash.ui.content.userphotos.UserPhotosContract;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 12/02/18.
 */

public class UserCollectionsFragment
        extends ContentFragment<UserCollectionsContract.Presenter, Collection>
        implements UserCollectionsContract.View {

    private static final String TAG = LogUtils.getLogTag(UserCollectionsFragment.class);
    public static final String KEY_USERNAME = "username";

    private CollectionOnClickListener collectionOnClickListener =
            new CollectionOnClickListener() {
                @Override
                public void onClick(Collection collection, View transitionView) {
                    startCollectionDescriptionActivity(collection, transitionView);
                }

                @Override
                public void onArtistClick(Collection collection, View transitionView) {
                    startUserDescriptionActivity(collection, transitionView);
                }
            };

    private String username;

    @Inject
    Activity host;

    @Inject
    public UserCollectionsFragment() {
        // empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            username = getArguments().getString(KEY_USERNAME);
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {

        if (!this.username.equals(username)) {
            this.username = username;
            getPresenter().onUsernameChanged();
        }
    }

    @Override
    protected ListItem getListItem(Collection collection) {
        CollectionListItem listItem = new CollectionListItem(collection);
        listItem.setOnClickListener(collectionOnClickListener);
        return listItem;
    }

    @Override
    protected int getSpanCount() {
        return 1;
    }

    private void startCollectionDescriptionActivity(Collection collection, View transitionView) {

        Intent i = new Intent(getActivity(), CollectionDescriptionActivity.class);
        i.putExtra(CollectionDescriptionActivity.KEY_COLLECTION, collection);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        transitionView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }

    private void startUserDescriptionActivity(Collection collection, View transitionView) {

        Intent i = new Intent(getActivity(), UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_USER, collection.getUser());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        transitionView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }
}
