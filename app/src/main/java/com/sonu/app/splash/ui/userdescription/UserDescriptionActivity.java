package com.sonu.app.splash.ui.userdescription;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sonu.app.splash.R;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.model.unsplash.Collection;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.ContentListAdapter;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.photo.PhotoLight;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.search.allsearch.AllSearchActivity;
import com.sonu.app.splash.ui.widget.NestedScrollAppBarLayout;
import com.sonu.app.splash.ui.widget.SwipeBackCoordinatorLayout;
import com.sonu.app.splash.util.ConnectionUtil;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.NumberUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public class UserDescriptionActivity
        extends BaseActivity<UserDescriptionContract.Presenter>
        implements UserDescriptionContract.View {

    public static final String KEY_USER = "User";

    private static final String TAG = LogUtils.getLogTag(UserDescriptionActivity.class);

    @BindView(R.id.eddfl)
    SwipeBackCoordinatorLayout eddfl;

    @BindView(R.id.userPicIv)
    ImageView userPicIv;

    @BindView(R.id.artistNameTv)
    TextView artistNameTv;

    @BindView(R.id.artistUsernameTv)
    TextView artistUsernameTv;

    @BindView(R.id.artistBioTv)
    TextView artistBioTv;

    @BindView(R.id.artistLocationFl)
    View artistLocationFl;

    @BindView(R.id.artistLocationTv)
    TextView artistLocationTv;

    @BindView(R.id.artistPhotosCountBtn)
    Button artistPhotosCountBtn;

    @BindView(R.id.artistFollowersCountBtn)
    Button artistFollowersCountBtn;

    @BindView(R.id.artistLikesCountBtn)
    Button artistLikesCountBtn;

    @BindView(R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(R.id.progressBar)
    MaterialProgressBar progressBar;

    @BindView(R.id.userDataLl)
    LinearLayout userDataLl;

    @BindView(R.id.overlay)
    View overlay;

    @BindView(R.id.tagsParentLl)
    LinearLayout tagsParentLl;

    @BindView(R.id.artistPortfolioLinkIb)
    ImageButton artistPortfolioLinkIb;

    @BindView(R.id.appBar)
    NestedScrollAppBarLayout appBar;

    private int loadCount;

    private PhotoOnClickListener photoOnClickListener =
            new PhotoOnClickListener() {
                @Override
                public void onDownloadBtnClick(Photo photo) {

                    Log.d(TAG, "onDownloadBtnClick:called");

                    getPresenter().onDownloadBtnClick(photo);
                }

                @Override
                public void onClick(Photo photo, View itemView) {

                    Log.d(TAG, "onPhotoClick:called");

                    Intent i = new Intent(UserDescriptionActivity.this, PhotoDescriptionActivity.class);
                    i.putExtra(PhotoDescriptionActivity.KEY_PHOTO, photo);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(UserDescriptionActivity.this,
                                    Pair.create(itemView,
                                            UserDescriptionActivity.this.getString(R.string.transition_photo)),
                                    Pair.create(itemView,
                                            UserDescriptionActivity.this.getString(R.string.transition_photo_description_background)));

                    startActivity(i, options.toBundle());
                }
            };

    private ContentListAdapter adapter;

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

                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {

                }

                @Override
                public void showUnknownException(String message) {

                }

                @Override
                public void showLoading() {
//                    UserDescriptionFragment.this.showLoading();
                }

                @Override
                public void hideLoading() {
//                    UserDescriptionFragment.this.hideLoading();
                }
            };
    private StaggeredGridLayoutManager layoutManager;

    private MessageDialog messageDialog;
    private boolean dataLoaded;

    private ConnectivityManager.NetworkCallback networkCallback =
            new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(Network network) {

                    UserDescriptionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!dataLoaded) {
                                getPresenter().getData();
                            }
                        }
                    });
                }

                @Override
                public void onLost(Network network) {

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_description);
        ButterKnife.bind(this);

        User user = getIntent().getParcelableExtra(KEY_USER);

        updateUi(user);

        eddfl.setOnSwipeListener(new SwipeBackCoordinatorLayout.OnSwipeListener() {
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
        });

        messageDialog = new MessageDialog(this) ;
    }

    private void updateUi(User user) {

        artistNameTv.setText(user.getName().toLowerCase());

        artistUsernameTv.setText(
                String.format("@%s", user.getUsername()));

        Glide.with(this)
                .load(user.getProfileImage().getLarge())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Drawable> target,
                                                boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource,
                                                   Object model,
                                                   Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {

                        hideLoading();
                        return false;
                    }
                })
                .into(userPicIv);
    }

    @Override
    public void onResume() {
        super.onResume();

        ConnectionUtil.registerNetworkCallback(this, networkCallback);
    }

    @Override
    public void onPause() {
        super.onPause();

        ConnectionUtil.unregisterNetworkCallback(this, networkCallback);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setupList(DataManager dataManager, ContentCache contentCache) {

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

                        Log.d(TAG, "onScrolled:called");
                        Log.i(TAG, "onScrolled:scrolledY="+recyclerView.computeVerticalScrollOffset());

                        if (recyclerView.computeVerticalScrollOffset() > 200) {
                            overlay.setVisibility(View.VISIBLE);
                        } else {
                            overlay.setVisibility(View.GONE);
                        }
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

    @Override
    public synchronized void hideLoading() {

        loadCount += 1;
        if (loadCount >= 2) {
            progressBar.setVisibility(View.INVISIBLE);
            artistPortfolioLinkIb.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showIoException(int titleStringRes, int messageStringRes) {
        MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
        messageDialogConfig.color(getColor(R.color.darkRed));
        messageDialogConfig.actionText("OK");
        messageDialogConfig.iconDrawable(R.drawable.ic_perm_scan_wifi_black_48dp);
        messageDialogConfig.title(getString(titleStringRes));
        messageDialogConfig.message(getString(messageStringRes));
        messageDialog.setConfig(messageDialogConfig);
        messageDialog.show();
    }

    @Override
    public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
        MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
        messageDialogConfig.color(getColor(R.color.darkRed));
        messageDialogConfig.actionText("OK");
        messageDialogConfig.iconDrawable(R.drawable.ic_cloud_off_grey_56dp);
        messageDialogConfig.title(getString(titleStringRes));
        messageDialogConfig.message(getString(messageStringRes));
        messageDialog.setConfig(messageDialogConfig);
        messageDialog.show();
    }

    @Override
    public void showUnknownException(String message) {
        MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
        messageDialogConfig.color(getColor(R.color.darkRed));
        messageDialogConfig.actionText("OK");
        messageDialogConfig.iconDrawable(R.drawable.ic_perm_scan_wifi_black_48dp);
        messageDialogConfig.title(getString(R.string.unknown_exception_title));
        if (!message.equals("")) {
            messageDialogConfig.message(message);
        } else {
            messageDialogConfig.message(getString(R.string.unknown_exception_message));
        }
        messageDialog.setConfig(messageDialogConfig);
        messageDialog.show();
    }

    @Override
    public void displayUserDescription(final User user) {

        artistPhotosCountBtn.setText(
                String.format(
                        "%s photos",
                        String.valueOf(NumberUtils.format(user.getTotalPhotos()))));

        artistFollowersCountBtn.setText(
                String.format(
                        "%s followers",
                        String.valueOf(NumberUtils.format(user.getFollowersCount()))));

        artistLikesCountBtn.setText(
                String.format(
                        "%s likes",
                        String.valueOf(NumberUtils.format(user.getTotalLikes()))));

        if (user.getBio() != null) {

            artistBioTv.setVisibility(View.VISIBLE);
            artistBioTv.setText(user.getBio().trim());
        }

        if (user.getLocation() != null) {

            artistLocationFl.setVisibility(View.VISIBLE);
            artistLocationTv.setText(user.getLocation());
        }

        itemsRv.scrollToPosition(0);

        for (String tag : user.getUserTags().getCustom()) {
            tagsParentLl.addView(getTagView(tag));
        }

        for (String tag : user.getUserTags().getAggregated()) {
            tagsParentLl.addView(getTagView(tag));
        }

        artistPortfolioLinkIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user.getPortfolioUrl() != null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(user.getPortfolioUrl()));
                    startActivity(intent);
                } else {
                    Toast.makeText(UserDescriptionActivity.this, "No portfolio url", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dataLoaded = true;
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

    private View.OnClickListener tagOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(UserDescriptionActivity.this,
                            AllSearchActivity.class);
                    i.putExtra(
                            AllSearchActivity.KEY_QUERY,
                            ((TextView)((CardView) view).getChildAt(0)).getText());
                    startActivity(i);
                }
            };

    @Override
    public String getCurArtistUsername() {

            return ((User)
                    getIntent()
                            .getParcelableExtra(
                                    UserDescriptionActivity.KEY_USER)).getUsername();
    }

    @Override
    public void startPhotoDescriptionActivity(Photo photo) {
        Intent i = new Intent(this, PhotoDescriptionActivity.class);
        i.putExtra(PhotoDescriptionActivity.KEY_PHOTO, photo);
        startActivity(i);
    }
}
