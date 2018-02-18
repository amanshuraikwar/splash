package com.sonu.app.splash.ui.content.allcollections;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.sonu.app.splash.R;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.ui.collection.CollectionListItem;
import com.sonu.app.splash.ui.collection.CollectionOnClickListener;
import com.sonu.app.splash.ui.collectiondecription.CollectionDescriptionActivity;
import com.sonu.app.splash.ui.content.ContentFragment;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class AllCollectionsFragment
        extends ContentFragment<AllCollectionsContract.Presenter, Collection>
        implements AllCollectionsContract.View {

    private static final String TAG = LogUtils.getLogTag(AllCollectionsFragment.class);

    private CollectionOnClickListener collectionOnClickListener =
            new CollectionOnClickListener() {


                @Override
                public void onClick(Collection collection, View transitionView) {

                    Log.d(TAG, "onClick:called");

                    startCollectionDescriptionActivity(collection, transitionView);
                }

                @Override
                public void onArtistClick(Collection collection, View transitionView) {
                    startUserDescriptionActivity(collection, transitionView);
                }
            };

    @Inject
    public AllCollectionsFragment() {
        // empty constructor
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
