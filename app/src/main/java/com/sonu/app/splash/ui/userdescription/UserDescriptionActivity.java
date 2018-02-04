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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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
import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.sonu.app.splash.R;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.ui.collection.Collection;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.ContentListAdapter;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.ui.widget.NestedScrollAppBarLayout;
import com.sonu.app.splash.ui.widget.SwipeBackCoordinatorLayout;
import com.sonu.app.splash.util.ConnectionUtil;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public class UserDescriptionActivity
        extends BaseActivity<UserDescriptionContract.Presenter>
        implements UserDescriptionContract.View {

    public static final String KEY_PHOTO = "photo";

    public static final String KEY_USER = "user";

    public static final String KEY_COLLECTION = "collection";

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

        Photo photo = getIntent().getParcelableExtra(KEY_PHOTO);
        UserDescription userDescription = getIntent().getParcelableExtra(KEY_USER);
        Collection collection = getIntent().getParcelableExtra(KEY_COLLECTION);

        if (photo != null) {

            updateUi(photo);
        } else if (userDescription != null){

            updateUi(userDescription);
        } else {

            updateUi(collection);
        }


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

        // todo temp
        downloadManager =
                (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    }

    private void updateUi(Photo photo) {

        artistNameTv.setText(photo.getArtistName().toLowerCase());

        artistUsernameTv.setText(
                String.format("@%s", photo.getArtistUsername()));

        Glide.with(this)
                .load(photo.getArtistProfileImageUrl())
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

    private void updateUi(UserDescription userDescription) {

        artistNameTv.setText(userDescription.getName().toLowerCase());

        artistUsernameTv.setText(
                String.format("@%s", userDescription.getUsername()));

        Glide.with(this)
                .load(userDescription.getProfileImageUrl())
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

    private void updateUi(Collection collection) {

        artistNameTv.setText(collection.getArtistName().toLowerCase());

        artistUsernameTv.setText(
                String.format("@%s", collection.getArtistUsername()));

        Glide.with(this)
                .load(collection.getArtistProfileImageUrl())
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

//        userDataLl.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        // Layout has happened here.
//
//                        Log.d(TAG, "yoyoyo===="+userDataLl.getBottom());
//
//                        itemsRv.setPaddingRelative(itemsRv.getPaddingStart(),
//                                userDataLl.getBottom(),
//                                itemsRv.getPaddingEnd(),
//                                itemsRv.getPaddingBottom());
//
//                        userDataHeight = userDataLl.getBottom();
//
//                        // Don't forget to remove your listener when you are done with it.
//                        userDataLl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    }
//                });
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
    public void displayUserDescription(final UserDescription userDescription) {

        artistPhotosCountBtn.setText(String.format("%d photos", userDescription.getTotalPhotos()));
        artistFollowersCountBtn.setText(String.format("%d followers", userDescription.getFollowersCount()));
        artistLikesCountBtn.setText(String.format("%d likes", userDescription.getTotalLikes()));

        if (!userDescription.getBio().equals("")) {

            artistBioTv.setVisibility(View.VISIBLE);
            artistBioTv.setText(userDescription.getBio().trim());
        }

        if (!userDescription.getLocation().equals("")) {

            artistLocationFl.setVisibility(View.VISIBLE);
            artistLocationTv.setText(userDescription.getLocation());
        }

        itemsRv.scrollToPosition(0);

        if (userDescription.getTags().length > 0) {
            for (String tag : userDescription.getTags()) {
                tagsParentLl.addView(getTagView(tag));
            }
        }


        artistPortfolioLinkIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!userDescription.getPortfolioUrl().equals("")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(userDescription.getPortfolioUrl()));
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
        return parent;
    }

    @Override
    public String getCurArtistUsername() {
        if (getIntent().getParcelableExtra(UserDescriptionActivity.KEY_PHOTO) != null) {
            return ((Photo)
                    getIntent()
                            .getParcelableExtra(
                                    UserDescriptionActivity.KEY_PHOTO)).getArtistUsername();
        } else if (getIntent().getParcelableExtra(UserDescriptionActivity.KEY_USER) != null) {
            return ((UserDescription)
                    getIntent()
                            .getParcelableExtra(
                                    UserDescriptionActivity.KEY_USER)).getUsername();
        } else {
            return ((Collection)
                    getIntent()
                            .getParcelableExtra(
                                    UserDescriptionActivity.KEY_COLLECTION)).getArtistUsername();
        }
    }

    @Override
    public void startPhotoDescriptionActivity(Photo photo) {
        Intent i = new Intent(this, PhotoDescriptionActivity.class);
        i.putExtra(PhotoDescriptionActivity.KEY_PHOTO, photo);
        startActivity(i);
    }

    // todo temp
    DownloadManager downloadManager;
    LongSparseArray<PhotoDownload> queuedDownloads = new LongSparseArray<>();
    IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

    @Override
    public void downloadPhoto(PhotoDownload photoDownload) {

        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(photoDownload.getDownloadUrl()));

        request.setTitle("Downloading photo");
        request.setDescription(photoDownload.getDownloadedFileName());

        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                photoDownload.getDownloadedFileName());

        long downloadReference = downloadManager.enqueue(request);
        queuedDownloads.append(downloadReference, photoDownload);
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            Toast.makeText(
                    UserDescriptionActivity.this,
                    "Download complete "
                            +queuedDownloads.get(referenceId).getDownloadedFileName(),
                    Toast.LENGTH_SHORT).show();

            queuedDownloads.remove(referenceId);
        }
    };
}
