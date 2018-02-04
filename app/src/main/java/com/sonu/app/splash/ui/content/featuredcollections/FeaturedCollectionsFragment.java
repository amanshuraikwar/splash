package com.sonu.app.splash.ui.content.featuredcollections;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.cache.FeaturedCollectionsCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.collection.Collection;
import com.sonu.app.splash.ui.collection.CollectionListItem;
import com.sonu.app.splash.ui.collection.CollectionOnClickListener;
import com.sonu.app.splash.ui.collectiondecription.CollectionDescriptionActivity;
import com.sonu.app.splash.ui.content.ContentFragment;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosContract;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class FeaturedCollectionsFragment
        extends ContentFragment<FeaturedCollectionsContract.Presenter, Collection>
        implements FeaturedCollectionsContract.View {

    private static final String TAG = LogUtils.getLogTag(FeaturedCollectionsFragment.class);

    private CollectionOnClickListener collectionOnClickListener =
            new CollectionOnClickListener() {


                @Override
                public void onClick(Collection collection, View transitionView) {

                    Log.d(TAG, "onClick:called");

                    Intent i = new Intent(getActivity(), CollectionDescriptionActivity.class);
                    i.putExtra(CollectionDescriptionActivity.KEY_COLLECTION, collection);
                    startActivity(i);
                }

                @Override
                public void onArtistClick(Collection collection, View transitionView) {
                    startUserDescriptionActivity(collection, transitionView);
                }
            };

    @Inject
    public FeaturedCollectionsFragment() {
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

    private void startUserDescriptionActivity(Collection collection, View transitionView) {

        Intent i = new Intent(getActivity(), UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_COLLECTION, collection);

        transitionView.setTransitionName(collection.getArtistId());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        transitionView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }
}
