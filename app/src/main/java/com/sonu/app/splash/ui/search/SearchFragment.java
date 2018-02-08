package com.sonu.app.splash.ui.search;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.cache.SearchCollectionsCache;
import com.sonu.app.splash.data.cache.SearchPhotosCache;
import com.sonu.app.splash.data.cache.SearchUsersCache;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.data.network.unsplashapi.ApiEndpoints;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.ui.collection.CollectionHorizontalListItem;
import com.sonu.app.splash.ui.collection.CollectionOnClickListener;
import com.sonu.app.splash.ui.collectiondecription.CollectionDescriptionActivity;
import com.sonu.app.splash.ui.header.HeaderHorizontalListItem;
import com.sonu.app.splash.ui.list.ContentListAdapter;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.SimpleListItemOnClickListener;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.photo.PhotoHorizontalListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.search.activity.SearchActivity;
import com.sonu.app.splash.ui.search.allsearch.AllSearchActivity;
import com.sonu.app.splash.ui.user.UserHorizontalListItem;
import com.sonu.app.splash.ui.user.UserOnClickListener;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 01/02/18.
 */

public class SearchFragment
        extends BaseFragment<SearchContract.Presenter>
        implements SearchContract.View {

    private static final String TAG = LogUtils.getLogTag(SearchFragment.class);

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.photosRv)
    RecyclerView photosRv;

    @BindView(R.id.collectionsRv)
    RecyclerView collectionsRv;

    @BindView(R.id.usersRv)
    RecyclerView usersRv;

    @BindView(R.id.photosPb)
    MaterialProgressBar photosPb;

    @BindView(R.id.photosBtn)
    Button photosBtn;

    @BindView(R.id.collectionsPb)
    MaterialProgressBar collectionsPb;

    @BindView(R.id.collectionsBtn)
    Button collectionsBtn;

    @BindView(R.id.usersPb)
    MaterialProgressBar usersPb;

    @BindView(R.id.usersBtn)
    Button usersBtn;

    private ContentListAdapter<Photo> photosAdapter;
    private ContentListAdapter<Collection> collectionsAdapter;
    private ContentListAdapter<User> usersAdapter;

    private SimpleListItemOnClickListener morePhotosOnClickListener =
            new SimpleListItemOnClickListener() {
                @Override
                public void onClick() {

                    Log.d(TAG, "morePhotosOnClick:called");
                    Log.i(TAG, "morePhotosOnClick:query="+searchView.getQuery());

                    Intent i = new Intent(getActivity(), SearchActivity.class);
                    i.putExtra(SearchActivity.KEY_SEARCH_TYPE, SearchActivity.TYPE_PHOTOS);
                    i.putExtra(SearchActivity.KEY_QUERY, searchView.getQuery().toString());
                    startActivity(i);
                }
            };

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

    private ContentListAdapter.AdapterListener<Photo> photosListener =
            new ContentListAdapter.AdapterListener<Photo>() {

                @Override
                public ListItem createListItem(Photo photo) {
                    PhotoHorizontalListItem listItem = new PhotoHorizontalListItem(photo);
                    listItem.setOnClickListener(photoOnClickListener);
                    return listItem;
                }

                @Override
                public List<ListItem> createListItems(List<Photo> photos) {
                    List<ListItem> list = super.createListItems(photos);
                    if (photos.size() == ApiEndpoints.PER_PAGE) {
                        HeaderHorizontalListItem listItem =
                                new HeaderHorizontalListItem("MORE PHOTOS");
                        listItem.setOnClickListener(morePhotosOnClickListener);
                        list.add(listItem);
                    }
                    return list;
                }

                @Override
                public void showIoException(int titleStringRes, int messageStringRes) {
                    showPhotosError();
                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
                    showPhotosError();
                }

                @Override
                public void showUnknownException(String message) {
                    showPhotosError();
                }

                @Override
                public void showLoading() {
                    showPhotosLoading();
                }

                @Override
                public void hideLoading() {
                    photosPb.setVisibility(View.GONE);
                }
    };

    private SimpleListItemOnClickListener moreCollectionsOnClickListener =
            new SimpleListItemOnClickListener() {
                @Override
                public void onClick() {
                    Intent i = new Intent(getActivity(), SearchActivity.class);
                    i.putExtra(SearchActivity.KEY_SEARCH_TYPE, SearchActivity.TYPE_COLLECTIONS);
                    i.putExtra(SearchActivity.KEY_QUERY, searchView.getQuery().toString());
                    startActivity(i);
                }
            };

    private CollectionOnClickListener collectionOnClickListener =
            new CollectionOnClickListener() {
                @Override
                public void onClick(Collection collection, View transitionView) {

                    Intent i = new Intent(getActivity(), CollectionDescriptionActivity.class);
                    i.putExtra(CollectionDescriptionActivity.KEY_COLLECTION, collection);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(),
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
                    CollectionHorizontalListItem listItem = new CollectionHorizontalListItem(collection);
                    listItem.setOnClickListener(collectionOnClickListener);
                    return listItem;
                }

                @Override
                public List<ListItem> createListItems(List<Collection> collections) {
                    List<ListItem> list = super.createListItems(collections);
                    if (collections.size() == ApiEndpoints.PER_PAGE) {
                        HeaderHorizontalListItem listItem =
                                new HeaderHorizontalListItem("MORE COLLECTIONS");
                        listItem.setOnClickListener(moreCollectionsOnClickListener);
                        list.add(listItem);
                    }
                    return list;
                }

                @Override
                public void showIoException(int titleStringRes, int messageStringRes) {
                    showCollectionsError();
                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
                    showCollectionsError();
                }

                @Override
                public void showUnknownException(String message) {
                    showCollectionsError();
                }

                @Override
                public void showLoading() {
                    showCollectionsLoading();
                }

                @Override
                public void hideLoading() {
                    collectionsPb.setVisibility(View.GONE);
                }
            };

    private SimpleListItemOnClickListener moreUsersOnClickListener =
            new SimpleListItemOnClickListener() {
                @Override
                public void onClick() {
                    Intent i = new Intent(getActivity(), SearchActivity.class);
                    i.putExtra(SearchActivity.KEY_SEARCH_TYPE, SearchActivity.TYPE_USERS);
                    i.putExtra(SearchActivity.KEY_QUERY, searchView.getQuery().toString());
                    startActivity(i);
                }
            };

    private UserOnClickListener userOnClickListener =
            new UserOnClickListener() {
                @Override
                public void onClick(User user,
                                    View transitionView) {

                    Intent i = new Intent(getActivity(), UserDescriptionActivity.class);
                    i.putExtra(UserDescriptionActivity.KEY_USER, user);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                                    transitionView,
                                    getString(R.string.transition_artist_pic));

                    startActivity(i, options.toBundle());
                }
            };

    private ContentListAdapter.AdapterListener<User> usersListener =
            new ContentListAdapter.AdapterListener<User>() {

                @Override
                public ListItem createListItem(User user) {
                    UserHorizontalListItem listItem = new UserHorizontalListItem(user);
                    listItem.setOnClickListener(userOnClickListener);
                    return listItem;
                }

                @Override
                public List<ListItem> createListItems(List<User> userDescriptions) {
                    List<ListItem> list = super.createListItems(userDescriptions);
                    if (userDescriptions.size() == ApiEndpoints.PER_PAGE) {
                        HeaderHorizontalListItem listItem =
                                new HeaderHorizontalListItem("MORE USERS");
                        listItem.setOnClickListener(moreUsersOnClickListener);
                        list.add(listItem);
                    }
                    return list;
                }

                @Override
                public void showIoException(int titleStringRes, int messageStringRes) {
                    showUsersError();
                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
                    showUsersError();
                }

                @Override
                public void showUnknownException(String message) {
                    showUsersError();
                }

                @Override
                public void showLoading() {
                    showUsersLoading();
                }

                @Override
                public void hideLoading() {
                    usersPb.setVisibility(View.GONE);
                }
            };

    @Inject
    public SearchFragment() {
        // empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {

            if (getArguments().getString(AllSearchActivity.KEY_QUERY) != null) {

                searchView.setQuery(getArguments().getString(AllSearchActivity.KEY_QUERY), false);
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onSearchClick:called");
                Log.i(TAG, "onSearchClick:query="+s);

                getPresenter().onSearchClick(s);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        photosRv.setLayoutManager(
                new LinearLayoutManager(
                        getContext(), LinearLayoutManager.HORIZONTAL, false));

        collectionsRv.setLayoutManager(
                new LinearLayoutManager(
                        getContext(), LinearLayoutManager.HORIZONTAL, false));

        usersRv.setLayoutManager(
                new LinearLayoutManager(
                        getContext(), LinearLayoutManager.HORIZONTAL, false));

        photosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (photosAdapter != null) {

                    photosAdapter.getAllContent();
                }
            }
        });

        collectionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (collectionsAdapter != null) {

                    collectionsAdapter.getAllContent();
                }
            }
        });

        usersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (usersAdapter != null) {

                    usersAdapter.getAllContent();
                }
            }
        });
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
    public void updatePhotos(SearchPhotosCache searchPhotosCache) {

        Log.d(TAG, "updatePhotos:called");
        Log.i(TAG, "updatePhotos:cache="+searchPhotosCache);

        photosAdapter =
                new ContentListAdapter<>(
                        getActivity(),
                        new ListItemTypeFactory(),
                        searchPhotosCache,
                        photosListener);
        photosRv.setAdapter(photosAdapter);
        photosAdapter.getAllContent();
    }

    @Override
    public void updateCollections(SearchCollectionsCache searchCollectionsCache) {

        Log.d(TAG, "updateCollections:called");
        Log.i(TAG, "updateCollections:cache="+searchCollectionsCache);

        collectionsAdapter =
                new ContentListAdapter<>(
                        getActivity(),
                        new ListItemTypeFactory(),
                        searchCollectionsCache,
                        collectionsListener);
        collectionsRv.setAdapter(collectionsAdapter);
        collectionsAdapter.getAllContent();
    }

    @Override
    public void updateUsers(SearchUsersCache searchUsersCache) {

        Log.d(TAG, "updateUsers:called");
        Log.i(TAG, "updateUsers:cache="+searchUsersCache);

        usersAdapter =
                new ContentListAdapter<>(
                        getActivity(),
                        new ListItemTypeFactory(),
                        searchUsersCache,
                        usersListener);
        usersRv.setAdapter(usersAdapter);
        usersAdapter.getAllContent();
    }

    @Override
    public String getInitialQuery() {
        if (getArguments() != null) {
            return getArguments().getString(AllSearchActivity.KEY_QUERY);
        } else {
            return null;
        }

    }

    private void showPhotosError() {
        photosPb.setVisibility(View.GONE);
        photosBtn.setVisibility(View.VISIBLE);
    }

    private void showPhotosLoading() {
        photosPb.setVisibility(View.VISIBLE);
        photosBtn.setVisibility(View.GONE);
    }

    private void showCollectionsError() {
        collectionsPb.setVisibility(View.GONE);
        collectionsBtn.setVisibility(View.VISIBLE);
    }

    private void showCollectionsLoading() {
        collectionsPb.setVisibility(View.VISIBLE);
        collectionsBtn.setVisibility(View.GONE);
    }

    private void showUsersError() {
        usersPb.setVisibility(View.GONE);
        usersBtn.setVisibility(View.VISIBLE);
    }

    private void showUsersLoading() {
        usersPb.setVisibility(View.VISIBLE);
        usersBtn.setVisibility(View.GONE);
    }

    private void startUserDescriptionActivity(Collection collection, View transitionView) {

        Intent i = new Intent(getActivity(), UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_USER, collection.getUser());

        transitionView.setTransitionName(collection.getUser().getId());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        transitionView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }
}
