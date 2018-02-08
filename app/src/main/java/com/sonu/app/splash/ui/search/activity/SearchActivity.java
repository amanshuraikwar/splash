package com.sonu.app.splash.ui.search.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.ui.collection.CollectionListItem;
import com.sonu.app.splash.ui.collection.CollectionOnClickListener;
import com.sonu.app.splash.ui.collectiondecription.CollectionDescriptionActivity;
import com.sonu.app.splash.ui.list.ContentListAdapter;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.photo.PhotoLight;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.user.UserListItem;
import com.sonu.app.splash.ui.user.UserOnClickListener;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 03/02/18.
 */

public class SearchActivity
        extends BaseActivity<SearchContract.Presenter>
        implements SearchContract.View {

    private static final String TAG = LogUtils.getLogTag(SearchActivity.class);

    public static final String KEY_SEARCH_TYPE = "type";
    public static final String TYPE_PHOTOS = "photos";
    public static final String TYPE_COLLECTIONS = "collections";
    public static final String TYPE_USERS = "users";

    public static final String KEY_QUERY = "query";

    private ContentListAdapter<Photo> photosAdapter;
    private ContentListAdapter<Collection> collectionsAdapter;
    private ContentListAdapter<User> usersAdapter;

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

                    Intent i = new Intent(SearchActivity.this, PhotoDescriptionActivity.class);
                    i.putExtra(PhotoDescriptionActivity.KEY_PHOTO, photo);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this,
                                    Pair.create(itemView,
                                            getString(R.string.transition_photo)),
                                    Pair.create(itemView,
                                            getString(R.string.transition_photo_description_background)));

                    startActivity(i, options.toBundle());
                }
            };

    private ContentListAdapter.AdapterListener<Photo> photosListener =
            new ContentListAdapter.AdapterListener<Photo>() {

                @Override
                public ListItem createListItem(Photo photo) {
                    PhotoListItem listItem = new PhotoListItem(photo);
                    listItem.setOnClickListener(photoOnClickListener);
                    return listItem;
                }

                @Override
                public void showIoException(int titleStringRes, int messageStringRes) {
                    showError();
                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
                    showError();
                }

                @Override
                public void showUnknownException(String message) {
                    showError();
                }

                @Override
                public void showLoading() {
                    SearchActivity.this.showLoading();
                }

                @Override
                public void hideLoading() {
                    SearchActivity.this.hideLoading();
                }
            };

    private CollectionOnClickListener collectionOnClickListener =
            new CollectionOnClickListener() {
                @Override
                public void onClick(Collection collection, View transitionView) {

                    Intent i = new Intent(SearchActivity.this, CollectionDescriptionActivity.class);
                    i.putExtra(CollectionDescriptionActivity.KEY_COLLECTION, collection);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this,
                                    transitionView,
                                    getString(R.string.transition_artist_pic));

                    startActivity(i, options.toBundle());
                }

                @Override
                public void onArtistClick(Collection collection, View transitionView) {
                    startUserDescriptionActivity(collection, transitionView);
                }
            };

    private ContentListAdapter.AdapterListener<Collection> collectionsListener =
            new ContentListAdapter.AdapterListener<Collection>() {

                @Override
                public ListItem createListItem(Collection collection) {
                    CollectionListItem listItem = new CollectionListItem(collection);
                    listItem.setOnClickListener(collectionOnClickListener);
                    return listItem;
                }

                @Override
                public void showIoException(int titleStringRes, int messageStringRes) {
                    showError();
                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
                    showError();
                }

                @Override
                public void showUnknownException(String message) {
                    showError();
                }

                @Override
                public void showLoading() {
                    SearchActivity.this.showLoading();
                }

                @Override
                public void hideLoading() {
                    SearchActivity.this.hideLoading();
                }
            };

    private UserOnClickListener userOnClickListener =
            new UserOnClickListener() {
                @Override
                public void onClick(User user,
                                    View transitionView) {

                    Intent i = new Intent(SearchActivity.this, UserDescriptionActivity.class);
                    i.putExtra(UserDescriptionActivity.KEY_USER, user);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this,
                                    transitionView,
                                    getString(R.string.transition_artist_pic));

                    startActivity(i, options.toBundle());
                }
            };

    private ContentListAdapter.AdapterListener<User> usersListener =
            new ContentListAdapter.AdapterListener<User>() {

                @Override
                public ListItem createListItem(User user) {
                    UserListItem listItem = new UserListItem(user);
                    listItem.setOnClickListener(userOnClickListener);
                    return listItem;
                }

                @Override
                public void showIoException(int titleStringRes, int messageStringRes) {
                    showError();
                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
                    showError();
                }

                @Override
                public void showUnknownException(String message) {
                    showError();
                }

                @Override
                public void showLoading() {
                    SearchActivity.this.showLoading();
                }

                @Override
                public void hideLoading() {
                    SearchActivity.this.hideLoading();
                }
            };

    private RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.errorWrapperLl)
    View errorWrapperLl;

    @BindView(R.id.retryBtn)
    Button retryBtn;

    @BindView(R.id.progressBar)
    MaterialProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setupLayoutManager();

        searchView.setQuery(getQuery(), false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getPresenter().onQuerySubmit(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().onQuerySubmit(searchView.getQuery().toString());
            }
        });
    }

    private void setupLayoutManager() {

        switch (getSearchType()) {
            case TYPE_PHOTOS:
                layoutManager =
                        new StaggeredGridLayoutManager(
                                2, StaggeredGridLayoutManager.VERTICAL);
                break;
            case TYPE_COLLECTIONS:
                layoutManager =
                        new LinearLayoutManager(this);
                break;
            case TYPE_USERS:
                layoutManager =
                        new LinearLayoutManager(this);
                break;
        }

        itemsRv.setLayoutManager(layoutManager);
    }

    @Override
    public void showIoException(int titleStringRes, int messageStringRes) {

    }

    @Override
    public void showUnsplashApiException(int titleStringRes, int messageStringRes) {

    }

    @Override
    public void showUnknownException(String message) {

    }

    @Override
    public String getSearchType() {
        return getIntent().getStringExtra(KEY_SEARCH_TYPE);
    }

    @Override
    public void updateUi(SearchPhotosCache cache) {

        photosAdapter =
                new ContentListAdapter<>(
                        this,
                        new ListItemTypeFactory(),
                        cache,
                        photosListener);
        itemsRv.setAdapter(photosAdapter);
        photosAdapter.getAllContent();

        itemsRv.clearOnScrollListeners();

        itemsRv.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                        int lastVisibleItems[] = new int[3];

                        ((StaggeredGridLayoutManager)
                                layoutManager).findLastVisibleItemPositions(lastVisibleItems);

                        if (lastVisibleItems[0] == photosAdapter.getItemCount()-1
                                || lastVisibleItems[1] == photosAdapter.getItemCount()-1) {

                            if (photosAdapter.getItemCount() != 0) {
                                photosAdapter.getMoreContent();
                            }
                        }
                    }
                });
    }

    @Override
    public void updateUi(SearchCollectionsCache cache) {

        collectionsAdapter =
                new ContentListAdapter<>(
                        this,
                        new ListItemTypeFactory(),
                        cache,
                        collectionsListener);
        itemsRv.setAdapter(collectionsAdapter);
        collectionsAdapter.getAllContent();

        itemsRv.clearOnScrollListeners();

        itemsRv.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                        int lastVisibleItem =
                                ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                        if (lastVisibleItem == collectionsAdapter.getItemCount()-1) {

                            if (collectionsAdapter.getItemCount() != 0) {
                                collectionsAdapter.getMoreContent();
                            }
                        }
                    }
                });
    }

    @Override
    public void updateUi(SearchUsersCache cache) {

        usersAdapter =
                new ContentListAdapter<>(
                        this,
                        new ListItemTypeFactory(),
                        cache,
                        usersListener);
        itemsRv.setAdapter(usersAdapter);
        usersAdapter.getAllContent();

        itemsRv.clearOnScrollListeners();

        itemsRv.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                        int lastVisibleItem =
                                ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                        if (lastVisibleItem == usersAdapter.getItemCount()-1) {

                            if (usersAdapter.getItemCount() != 0) {
                                usersAdapter.getMoreContent();
                            }
                        }
                    }
                });
    }

    @Override
    public String getQuery() {

        Log.d(TAG, "getQuery:called");
        Log.i(TAG, "getQuery:query="+getIntent().getStringExtra(KEY_QUERY));

        return getIntent().getStringExtra(KEY_QUERY);
    }

    private void showError() {
        if (isListEmpty()) {
            errorWrapperLl.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showLoading() {
        if (isListEmpty()) {
            errorWrapperLl.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideLoading() {
        errorWrapperLl.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private boolean isListEmpty() {

        switch (getSearchType()) {
            case TYPE_PHOTOS:
                return photosAdapter.getItemCount() == 0;
            case TYPE_COLLECTIONS:
                return collectionsAdapter.getItemCount() == 0;
            case TYPE_USERS:
                return usersAdapter.getItemCount() == 0;
        }

        return false;
    }

    private void startUserDescriptionActivity(Collection collection, View transitionView) {

        Intent i = new Intent(SearchActivity.this, UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_USER, collection.getUser());

        transitionView.setTransitionName(collection.getUser().getId());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this,
                        transitionView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }
}
