package com.sonu.app.splash.ui.content.collectionphotos;

import android.app.ActivityOptions;
import android.content.Intent;
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

public class CollectionPhotosFragment
        extends ContentFragment<CollectionPhotosContract.Presenter, Photo>
        implements CollectionPhotosContract.View {

    private static final String TAG = LogUtils.getLogTag(CollectionPhotosFragment.class);

    public static final String KEY_COLLECTION_ID = "collection_id";

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

    @Inject
    public CollectionPhotosFragment() {
        // empty constructor
    }

    @Override
    protected ListItem getListItem(Photo photo) {
        PhotoListItem listItem = new PhotoListItem(photo);
        listItem.setOnClickListener(photoOnClickListener);
        return listItem;
    }

    @Override
    public String getCollectionId() {
        return getArguments().getString(KEY_COLLECTION_ID);
    }
}
