package com.sonu.app.splash.ui.favs;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sonu.app.splash.R;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.collection.CollectionListItem;
import com.sonu.app.splash.ui.collection.CollectionOnClickListener;
import com.sonu.app.splash.ui.collectiondecription.CollectionDescriptionActivity;
import com.sonu.app.splash.ui.header.HeaderListItem;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.RecyclerViewAdapter;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.user.UserListItem;
import com.sonu.app.splash.ui.user.UserOnClickListener;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 17/02/18.
 */

public class FavsFragment
        extends BaseFragment<FavsContract.Presenter>
        implements FavsContract.View {

    private static final String TAG = LogUtils.getLogTag(FavsFragment.class);

    @BindView(R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(R.id.errorWrapperLl)
    View errorWrapperLl;

    @BindView(R.id.retryBtn)
    Button retryBtn;

    @BindView(R.id.errorProgressBar)
    MaterialProgressBar errorProgressBar;

    private RecyclerViewAdapter adapter;

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

                    startPhotoDescriptionActivity(photo, itemView);
                }
            };

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

    private UserOnClickListener userOnClickListener =
            new UserOnClickListener() {
                @Override
                public void onClick(User userDescription, View transitionView) {
                    startUserDescriptionActivity(userDescription, transitionView);
                }
            };

    @Inject
    public FavsFragment() {
        // empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favs, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retryBtn.setOnClickListener(view1 -> getPresenter().getData());

        itemsRv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void showLoading() {
        errorWrapperLl.setVisibility(View.GONE);
        errorProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        errorWrapperLl.setVisibility(View.GONE);
        errorProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        errorWrapperLl.setVisibility(View.VISIBLE);
        errorProgressBar.setVisibility(View.GONE);
    }

    @Override
    public ListItem getListItem(Photo photo) {
        PhotoListItem listItem = new PhotoListItem(photo);
        listItem.setOnClickListener(photoOnClickListener);
        return listItem;
    }

    @Override
    public ListItem getListItem(Collection collection) {
        CollectionListItem listItem = new CollectionListItem(collection);
        listItem.setOnClickListener(collectionOnClickListener);
        return listItem;
    }

    @Override
    public ListItem getListItem(User user) {
        UserListItem listItem = new UserListItem(user);
        listItem.setOnClickListener(userOnClickListener);
        return listItem;
    }

    @Override
    public ListItem getFavHeaderListItem(String text) {
        return new HeaderListItem(text);
    }

    @Override
    public void displayData(List<ListItem> listItems) {
        Log.d(TAG, "displayData:called");
        for (ListItem listItem : listItems) {
            Log.i(TAG, "displayData:item="+ listItem);
        }

        adapter =
                new RecyclerViewAdapter(
                        getActivity(), new ListItemTypeFactory(), listItems);

        itemsRv.setAdapter(adapter);
    }

    private void startPhotoDescriptionActivity(Photo photo, View itemView) {

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
