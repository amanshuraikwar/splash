package com.sonu.app.splash.ui.photodescription;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.sonu.app.splash.R;
import com.sonu.app.splash.model.unsplash.Exif;
import com.sonu.app.splash.model.unsplash.Location;
import com.sonu.app.splash.model.unsplash.User;
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.RecyclerViewAdapter;
import com.sonu.app.splash.ui.photofullscreen.PhotoFullscreenActivity;
import com.sonu.app.splash.ui.photostats.PhotoStatsActivity;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.ui.widget.FABToggle;
import com.sonu.app.splash.ui.widget.ParallaxScrimageView;
import com.sonu.app.splash.util.ColorHelper;
import com.sonu.app.splash.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amanshuraikwar on 11/01/18.
 */

public class PhotoDescriptionActivity
        extends BaseActivity<PhotoDescriptionContract.Presenter>
        implements PhotoDescriptionContract.View {

    private static final String TAG = LogUtils.getLogTag(PhotoDescriptionActivity.class);

    public static final String KEY_PHOTO = "curPhoto";

    @BindView(R.id.eddfl)
    ElasticDragDismissFrameLayout eddfl;

    @BindView(R.id.photoIv)
    ParallaxScrimageView photoIv;

    @BindView(R.id.backIb)
    ImageButton backIb;

    @BindView(R.id.downloadFab)
    FABToggle downloadFab;

    @BindView(R.id.photoInfoPb)
    ProgressBar photoInfoPb;

    @BindView(R.id.photoInfoLoadingWrapperLl)
    View photoInfoLoadingWrapperFl;

    @BindView(R.id.photoInfoRetryBtn)
    Button photoInfoRetryBtn;

    @BindView(R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(R.id.addToFavFab)
    FABToggle addToFavFab;

    private boolean contentLoaded;
    private int fabOffset;

    private PhotoDescriptionUiElements
            .PhotoUserListItemOnClickListener photoUserListItemOnClickListener =
            (user, animatingView) -> startUserDescriptionActivity(getCurPhoto(), animatingView);

    private PhotoDescriptionUiElements
            .PhotoInfoListItemOnClickListener photoInfoListItemOnClickListener =
            photoId -> startPhotoStatsActivity(getCurPhoto());

    private PhotoDescriptionUiElements
            .LocationListItemOnClickListener locationListItemOnClickListener =
            location -> {
                Uri locationUri =
                        Uri.parse(
                                "geo:"
                                        + location.getLat()
                                        + ","
                                        + location.getLon()
                                        + "?z=14");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
                startActivity(mapIntent);
            };

    private View firstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate:called");

        setContentView(R.layout.activity_photo_description);
        ButterKnife.bind(this);

        updateUi(getCurPhoto());

        downloadFab.setOnClickListener(
                view -> getPresenter().downloadPhoto(getCurPhoto()));

        backIb.setOnClickListener(view -> finishAfterTransition());

        eddfl.addListener(new ElasticDragDismissListener() {
            @Override
            public void onDrag(float elasticOffset,
                               float elasticOffsetPixels,
                               float rawOffset,
                               float rawOffsetPixels) {

            }

            @Override
            public void onDragDismissed() {
                finishAfterTransition();
            }
        });


        addToFavFab.setOnClickListener(view -> getPresenter().onAddToFavClick());

        photoIv.setOnClickListener(view -> startPhotoFullscreenActivity(getCurPhoto()));

        photoInfoRetryBtn.setOnClickListener(view -> getPresenter().getData());

        itemsRv.setLayoutManager(new LinearLayoutManager(this));

        itemsRv.addOnScrollListener(scrollListener);
        itemsRv.setOnFlingListener(flingListener);

        photoIv.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        photoIv.getViewTreeObserver().removeOnPreDrawListener(this);
                        calculateFabPosition();
                        return true;
                    }
                });
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            if (firstView!= null) {
                final int scrollY = firstView.getTop();
                photoIv.setOffset(scrollY);
                addToFavFab.setOffset(fabOffset + scrollY);
                downloadFab.setOffset(fabOffset + scrollY);
                Log.d(TAG, ""+scrollY);
            } else {
                firstView = itemsRv.getChildAt(0);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            // as we animate the main image's elevation change when it 'pins' at it's min height
            // a fling can cause the title to go over the image before the animation has a chance to
            // run. In this case we short circuit the animation and just jump to state.
            photoIv.setImmediatePin(newState == RecyclerView.SCROLL_STATE_SETTLING);
        }
    };

    private RecyclerView.OnFlingListener flingListener =
            new RecyclerView.OnFlingListener() {
                @Override
                public boolean onFling(int velocityX, int velocityY) {
                    photoIv.setImmediatePin(true);
                    return false;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart:called");

        // for some weird behaviour when loadingView gets
        // visible when returning to the activity after onStop()
        if (contentLoaded) {

            hideLoading();
        }
    }

    @Override
    public Photo getCurPhoto() {
        return getIntent().getParcelableExtra(KEY_PHOTO);
    }

    private void updateUi(final Photo photo) {

        int color = Color.parseColor(photo.getColor());

        getWindow().setStatusBarColor(color);

        downloadFab.setBackgroundTintList(ColorStateList.valueOf(color));

        addToFavFab.setBackgroundTintList(ColorStateList.valueOf(color));

        if (!ColorHelper.isDark(color)) {

            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            downloadFab.setImageTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.activeIcon)));

            addToFavFab.setImageTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.activeIcon)));
        }

        photoIv.setBackgroundColor(color);

        Glide.with(this)
                .asBitmap()
                .load(photo.getPhotoUrls().getSmall())
                .apply(new RequestOptions().centerCrop())
                .listener(new RequestListener<Bitmap>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model,
                                                Target<Bitmap> target,
                                                boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource,
                                                   Object model,
                                                   Target<Bitmap> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        updateIconColors(resource);
                        return false;
                    }
                })
                .into(photoIv);
    }

    private void updateIconColors(final Bitmap resource) {

        final int twentyFourDip =
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        24,
                        getResources().getDisplayMetrics());

        final int sixteenDip =
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        16,
                        getResources().getDisplayMetrics());

        Palette.from(resource)
                .maximumColorCount(3)
                .clearFilters()
                .setRegion(
                        sixteenDip,
                        sixteenDip,
                        sixteenDip + twentyFourDip,
                        sixteenDip + twentyFourDip)
                .generate(
                        palette ->
                                backIb
                                        .setImageTintList(
                                                ColorStateList
                                                        .valueOf(
                                                                getDrawableColor(palette, resource))));

        Palette.from(resource)
                .maximumColorCount(3)
                .clearFilters()
                .setRegion(
                        photoIv.getWidth() - 1 - sixteenDip - twentyFourDip,
                        sixteenDip,
                        photoIv.getWidth() - 1 - sixteenDip,
                        sixteenDip + twentyFourDip)
                .generate(palette -> {

                    ColorStateList drawableColorStateList =
                            ColorStateList.valueOf(
                                    getDrawableColor(palette, resource));

                    // for icon in top right corner
                });

        // todo
        /*
        Palette.from(resource)
                .maximumColorCount(3)
                .clearFilters()
                .setRegion(
                        picParentFl.getWidth() - 1 - sixteenDip - twentyFourDip,
                        picParentFl.getHeight() - sixteenDip - twentyFourDip,
                        picParentFl.getWidth() - 1 - sixteenDip,
                        picParentFl.getHeight() - sixteenDip)
                .generate(palette -> {

                    ColorStateList drawableColorStateList =
                            ColorStateList.valueOf(
                                    getDrawableColor(palette, resource));

                    addToFavIb.setImageTintList(
                            drawableColorStateList);
                });
                */
    }

    private @ColorInt int getDrawableColor(Palette palette, Bitmap resource) {

        boolean isDark;
        @ColorHelper.Lightness int lightness = ColorHelper.isDark(palette);
        if (lightness == ColorHelper.LIGHTNESS_UNKNOWN) {
            isDark = ColorHelper.isDark(
                    resource,
                    photoIv.getWidth() / 2,
                    0);
        } else {
            isDark = lightness == ColorHelper.IS_DARK;
        }

        if (isDark) {

            return ContextCompat.getColor(
                    this,
                    R.color.activeIconLight);
        } else {

            return ContextCompat.getColor(
                    this,
                    R.color.activeIcon);
        }
    }

    void calculateFabPosition() {
        // calculate 'natural' position i.e. with full height image. Store it for use when scrolling
        fabOffset = photoIv.getHeight() - (addToFavFab.getHeight() / 2);
        downloadFab.setOffset(fabOffset);
        addToFavFab.setOffset(fabOffset);

        // calculate min position i.e. pinned to the collapsed image when scrolled
        downloadFab.setMinOffset(photoIv.getMinimumHeight() - (addToFavFab.getHeight() / 2));
        addToFavFab.setMinOffset(photoIv.getMinimumHeight() - (addToFavFab.getHeight() / 2));
    }

    public void startUserDescriptionActivity(Photo photo, View animatingView) {

        Intent i = new Intent(this, UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_USER, photo.getUser());

        animatingView.setTransitionName(getCurPhotoId());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this,
                        animatingView,
                        getString(R.string.transition_artist_pic));

        startActivity(i, options.toBundle());
    }

    public void startPhotoFullscreenActivity(Photo photo) {

        Intent i = new Intent(this, PhotoFullscreenActivity.class);
        i.putExtra(PhotoFullscreenActivity.KEY_PHOTO, photo);

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this,
                        photoIv,
                        getString(R.string.transition_photo_fullscreen));

        startActivity(i, options.toBundle());
    }

    public void startPhotoStatsActivity(Photo photo) {

        Intent i = new Intent(this, PhotoStatsActivity.class);
        i.putExtra(PhotoStatsActivity.KEY_PHOTO_ID, photo.getId());
        startActivity(i);
    }

    // to be thread safe
    @Override
    public synchronized void hideLoading() {

        Log.d(TAG, "hideLoading:called");

        photoInfoLoadingWrapperFl.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

        Log.d(TAG, "showLoading:called");

        photoInfoLoadingWrapperFl.setVisibility(View.VISIBLE);
        photoInfoPb.setVisibility(View.VISIBLE);
        photoInfoRetryBtn.setVisibility(View.GONE);
    }

    @Override
    public void setFavActive() {

        addToFavFab
                .setImageDrawable(
                        ContextCompat.getDrawable(
                                this, R.drawable.bookmark_check_black_24dp));
    }

    @Override
    public void setFavInactive() {

        addToFavFab
                .setImageDrawable(
                        ContextCompat.getDrawable(
                                this, R.drawable.bookmark_plus_outline_black_24dp));
    }

    @Override
    public ListItem getLocationListItem(Location location) {
        PhotoDescriptionUiElements.LocationListItem listItem
                = new PhotoDescriptionUiElements.LocationListItem(location);
        listItem.setOnClickListener(locationListItemOnClickListener);
        return listItem;
    }

    @Override
    public ListItem getDescriptionTextListItem(String description) {
        return new PhotoDescriptionUiElements.PhotoDescriptionTextListItem(description);
    }

    @Override
    public ListItem getPhotoUserListItem(User user) {
        PhotoDescriptionUiElements.PhotoUserListItem listItem
                = new PhotoDescriptionUiElements.PhotoUserListItem(user);
        listItem.setOnClickListener(photoUserListItemOnClickListener);
        return listItem;
    }

    @Override
    public ListItem getPhotoInfoListItem(String photoId, Exif photoExif) {
        PhotoDescriptionUiElements.PhotoInfoListItem listItem
                = new PhotoDescriptionUiElements.PhotoInfoListItem(photoId, photoExif);
        listItem.setOnClickListener(photoInfoListItemOnClickListener);
        return listItem;
    }

    @Override
    public void showError() {

        Toast.makeText(this, R.string.content_error_text, Toast.LENGTH_SHORT).show();

        photoInfoLoadingWrapperFl.setVisibility(View.VISIBLE);
        photoInfoPb.setVisibility(View.GONE);
        photoInfoRetryBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayItems(List<ListItem> listItems) {

        itemsRv.setAdapter(
                new RecyclerViewAdapter(
                        this,
                        new ListItemTypeFactory(),
                        listItems));

        contentLoaded = true;
    }

    @Override
    public String getCurPhotoId() {
        return ((Photo) getIntent().getParcelableExtra(PhotoDescriptionActivity.KEY_PHOTO)).getId();
    }
}
