package com.sonu.app.splash.ui.content.searchusers;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sonu.app.splash.R;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.collection.CollectionListItem;
import com.sonu.app.splash.ui.collection.CollectionOnClickListener;
import com.sonu.app.splash.ui.collectiondecription.CollectionDescriptionActivity;
import com.sonu.app.splash.ui.content.ContentFragment;
import com.sonu.app.splash.ui.content.searchcollections.SearchCollectionsContract;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.user.UserListItem;
import com.sonu.app.splash.ui.user.UserOnClickListener;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class SearchUsersFragment
        extends ContentFragment<SearchUsersContract.Presenter, User>
        implements SearchUsersContract.View {

    private static final String TAG = LogUtils.getLogTag(SearchUsersFragment.class);
    public static final String KEY_QUERY = "query";

    private UserOnClickListener userOnClickListener =
            new UserOnClickListener() {
                @Override
                public void onClick(User userDescription, View transitionView) {
                    startUserDescriptionActivity(userDescription, transitionView);
                }
            };

    private String query;

    @Inject
    public SearchUsersFragment() {
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
    protected ListItem getListItem(User user) {
        UserListItem listItem = new UserListItem(user);
        listItem.setOnClickListener(userOnClickListener);
        return listItem;
    }

    @Override
    public String getCurSearchQuery() {
        return query;
    }

    @Override
    protected int getSpanCount() {
        return 1;
    }

    public void setQuery(@NonNull String query) {
        this.query = query;
        getPresenter().onSearchQueryChanged();
    }

    private void startUserDescriptionActivity(User user, View transitionView) {

        Intent i = new Intent(getActivity(), UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_USER, user);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        transitionView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }
}
