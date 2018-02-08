package com.sonu.app.splash.ui.collectiondecription;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sonu.app.splash.R;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.ui.list.ContentListAdapter;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.photo.PhotoLight;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.search.allsearch.AllSearchActivity;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.ui.widget.NestedScrollAppBarLayout;
import com.sonu.app.splash.ui.widget.SwipeBackCoordinatorLayout;
import com.sonu.app.splash.util.ColorHelper;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 04/02/18.
 */

public class CollectionDescriptionActivity
        extends BaseActivity<CollectionDescriptionContract.Presenter>
        implements CollectionDescriptionContract.View {

    public static final String KEY_COLLECTION = "collection";

    private static final String TAG = LogUtils.getLogTag(CollectionDescriptionActivity.class);

    @BindView(R.id.parent)
    SwipeBackCoordinatorLayout parent;

    @BindView(R.id.appBar)
    NestedScrollAppBarLayout appBar;

    @BindView(R.id.coverPhotoIv)
    ImageView coverPhotoIv;

    @BindView(R.id.collectionTitleTv)
    TextView collectionTitleTv;

    @BindView(R.id.tagsParentLl)
    LinearLayout tagsParentLl;

    @BindView(R.id.userPicIv)
    ImageView userPicIv;

    @BindView(R.id.artistPicCv)
    CardView artistPicCv;

    @BindView(R.id.artistNameTv)
    TextView artistNameTv;

    @BindView(R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(R.id.errorWrapperLl)
    View errorWrapperLl;

    @BindView(R.id.retryBtn)
    Button retryBtn;

    @BindView(R.id.errorProgressBar)
    MaterialProgressBar errorProgressBar;

    private ContentListAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    private PhotoOnClickListener photoOnClickListener =
            new PhotoOnClickListener() {
                @Override
                public void onDownloadBtnClick(Photo photo) {
                    Log.d(TAG, "onDownloadBtnClick:called");
                    getPresenter().downloadImage(photo);
                }

                @Override
                public void onClick(Photo photo, View transitionView) {
                    Log.d(TAG, "onPhotoClick:called");
                    startPhotoDescriptionActivity(photo, transitionView);
                }
            };

    private ContentListAdapter.AdapterListener<Photo> adapterListener =
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
                    CollectionDescriptionActivity.this.showLoading();
                }

                @Override
                public void hideLoading() {
                    CollectionDescriptionActivity.this.hideLoading();
                }
            };

    private SwipeBackCoordinatorLayout.OnSwipeListener onSwipeListener =
            new SwipeBackCoordinatorLayout.OnSwipeListener() {
                @Override
                public boolean canSwipeBack(int dir) {
                    return dir == SwipeBackCoordinatorLayout.UP_DIR || appBar.getY() >= 0;
                }

                @Override
                public void onSwipeProcess(float percent) {

                }

                @Override
                public void onSwipeFinish(int dir) {
                    finishAfterTransition();
                }
            };

    private View.OnClickListener tagOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    startAllSearchActivity(
                            ((TextView)
                                    ((CardView) view).getChildAt(0)
                            ).getText().toString());
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_collection_description);

        ButterKnife.bind(this);

        updateUi((Collection) getIntent().getParcelableExtra(KEY_COLLECTION));

        parent.setOnSwipeListener(onSwipeListener);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllPhotos();
            }
        });

        artistPicCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startUserDescriptionActivity(
                        (Collection) getIntent().getParcelableExtra(KEY_COLLECTION),
                        userPicIv);
            }
        });
    }

    private void updateUi(Collection collection) {

        collectionTitleTv.setText(collection.getTitle());

        Glide.with(this)
                .load(collection.getCoverPhoto().getPhotoUrls().getRegular())
                .apply(new RequestOptions().centerCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(coverPhotoIv);

        artistNameTv.setText(collection.getUser().getName().toLowerCase());

        Glide.with(this)
                .load(collection.getUser().getProfileImage().getLarge())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(userPicIv);

        if (collection.getTags().length > 0) {
            for (String tag : collection.getTags()) {
                tagsParentLl.addView(getTagView(tag));
            }
        }

        int color = Color.parseColor(collection.getCoverPhoto().getColor());

        getWindow().setStatusBarColor(color);

        if (!ColorHelper.isDark(color)) {

            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    private CardView getTagView(String tag) {
        CardView parent =
                (CardView) getLayoutInflater().inflate(
                        R.layout.item_artist_tag, tagsParentLl, false);
        TextView tagTv = (TextView) parent.getChildAt(0);
        tagTv.setText(tag);
        parent.setOnClickListener(tagOnClickListener);
        return parent;
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
    public String getCollectionId() {
        return String.valueOf(((Collection)getIntent().getParcelableExtra(KEY_COLLECTION)).getId());
    }

    @Override
    public void setupList(ContentCache contentCache) {

        // changing grid size for orientation changes
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            layoutManager =
                    new StaggeredGridLayoutManager(3,
                            StaggeredGridLayoutManager.VERTICAL);
        } else {

            layoutManager =
                    new StaggeredGridLayoutManager(2,
                            StaggeredGridLayoutManager.VERTICAL);
        }

        itemsRv.setLayoutManager(layoutManager);

        adapter = new ContentListAdapter(
                this,
                new ListItemTypeFactory(),
                contentCache,
                adapterListener);

        itemsRv.setAdapter(adapter);

        itemsRv.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                        Log.d(TAG, "onScrollStateChanged:called");

                        int lastVisibleItems[] = new int[3];

                        layoutManager.findLastVisibleItemPositions(lastVisibleItems);

                        if (lastVisibleItems[0] == adapter.getItemCount()-1
                                || lastVisibleItems[1] == adapter.getItemCount()-1) {

                            Log.i(TAG, "onScrollStateChanged:loading-more");

                            adapter.getMoreContent();
                        }
                    }
                });
    }

    @Override
    public void getAllPhotos() {
        adapter.getAllContent();
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getItemCount() == 0;
    }

    private void showError() {
        if (isListEmpty()) {
            errorWrapperLl.setVisibility(View.VISIBLE);
            errorProgressBar.setVisibility(View.GONE);
        }
    }

    private void showLoading() {
        if (isListEmpty()) {
            errorWrapperLl.setVisibility(View.GONE);
            errorProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideLoading() {
        errorWrapperLl.setVisibility(View.GONE);
        errorProgressBar.setVisibility(View.GONE);
    }

    private void startUserDescriptionActivity(Collection collection, View transitionView) {

        Intent i = new Intent(this, UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_USER, collection.getUser());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this,
                        transitionView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }

    private void startPhotoDescriptionActivity(Photo photo, View transitionView) {

        Intent i =
                new Intent(
                        CollectionDescriptionActivity.this,
                        PhotoDescriptionActivity.class);
        i.putExtra(PhotoDescriptionActivity.KEY_PHOTO, photo);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(
                        CollectionDescriptionActivity.this,
                        Pair.create(transitionView,
                                CollectionDescriptionActivity.this.getString(
                                        R.string.transition_photo)),
                        Pair.create(transitionView,
                                CollectionDescriptionActivity.this.getString(
                                        R.string.transition_photo_description_background)));

        startActivity(i, options.toBundle());
    }

    private void startAllSearchActivity(String query) {

        Intent i = new Intent(CollectionDescriptionActivity.this,
                AllSearchActivity.class);
        i.putExtra(AllSearchActivity.KEY_QUERY, query);
        startActivity(i);
    }
}
