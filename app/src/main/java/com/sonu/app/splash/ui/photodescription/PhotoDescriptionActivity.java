package com.sonu.app.splash.ui.photodescription;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.photofullscreen.PhotoFullscreenActivity;
import com.sonu.app.splash.ui.userdescription.UserDescriptionActivity;
import com.sonu.app.splash.ui.widget.WidthRelativeAspectRatioImageView;
import com.sonu.app.splash.util.ColorHelper;
import com.sonu.app.splash.util.ConnectionUtil;
import com.sonu.app.splash.util.NumberUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 11/01/18.
 */

public class PhotoDescriptionActivity
        extends BaseActivity<PhotoDescriptionContract.Presenter>
        implements PhotoDescriptionContract.View {

    public static final String KEY_PHOTO = "curPhoto";

    @BindView(R.id.eddfl)
    ElasticDragDismissFrameLayout eddfl;

    @BindView(R.id.picParentFl)
    View picParentFl;

    @BindView(R.id.photoIv)
    WidthRelativeAspectRatioImageView photoIv;

    @BindView(R.id.mainProgressBar)
    MaterialProgressBar mainProgressBar;

    @BindView(R.id.photoDescriptionTv)
    TextView photoDescriptionTv;

    @BindView(R.id.photoLikesCountBtn)
    Button photoLikesCountBtn;

    @BindView(R.id.photoViewsCountBtn)
    Button photoViewsCountBtn;

    @BindView(R.id.photoDownloadsCountBtn)
    Button photoDownloadsCountBtn;

    @BindView(R.id.artistPicIv)
    ImageView artistPicIv;

    @BindView(R.id.artistNameTv)
    TextView artistNameTv;

    @BindView(R.id.photoLocationFl)
    View photoLocationFl;

    @BindView(R.id.photoLocationTv)
    TextView photoLocationTv;

    @BindView(R.id.topGradientView)
    View topGradientView;

    @BindView(R.id.backIb)
    ImageButton backIb;

    @BindView(R.id.photoInfoIb)
    ImageButton photoInfoIb;

    @BindView(R.id.artistUsernameTv)
    TextView artistUsernameTv;

    @BindView(R.id.artistPicCv)
    CardView artistPicCv;

    // exif info
    @BindView(R.id.exifExposureTimeBtn)
    Button exifExposureTimeBtn;

    @BindView(R.id.exifFocalLengthBtn)
    Button exifFocalLengthBtn;

    @BindView(R.id.exifIsoBtn)
    Button exifIsoBtn;

    @BindView(R.id.exifMakeBtn)
    Button exifMakeBtn;

    @BindView(R.id.exifModelBtn)
    Button exifModelBtn;

    @BindView(R.id.exifApertureBtn)
    Button exifApertureBtn;

    @BindView(R.id.photoResolutionBtn)
    Button photoResolutionBtn;

    @BindView(R.id.fullScreenIb)
    ImageButton fullScreenIb;

    @BindView(R.id.downloadFab)
    FloatingActionButton downloadFab;

    private int loadCount;

    private MessageDialog messageDialog;
    private boolean dataLoaded;

    private ConnectivityManager.NetworkCallback networkCallback =
            new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(Network network) {

                    PhotoDescriptionActivity.this.runOnUiThread(new Runnable() {
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

        setContentView(R.layout.activity_photo_description);
        ButterKnife.bind(this);

        messageDialog = new MessageDialog(this);

        updateUi(getCurPhoto());

        downloadFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter()
                        .downloadPhoto(getCurPhoto());
            }
        });

        artistPicCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUserDescriptionActivity(getCurPhoto());
            }
        });

        backIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });

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

        fullScreenIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPhotoFullscreenActivity(getCurPhoto());
            }
        });
    }

    private Photo getCurPhoto() {
        return getIntent().getParcelableExtra(KEY_PHOTO);
    }

    private void updateUi(final Photo photo) {

        int color = Color.parseColor(photo.getColor());

        getWindow().setStatusBarColor(color);

        downloadFab.setBackgroundTintList(ColorStateList.valueOf(color));

        if (!ColorHelper.isDark(color)) {

            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            downloadFab.setImageTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.activeIcon)));
        }

        picParentFl.setBackgroundColor(color);

        if (photo.getDescription() == null) {
            photoDescriptionTv.setVisibility(View.GONE);
        } else {
            photoDescriptionTv.setText(photo.getDescription().trim());
        }

        photoLikesCountBtn.setText(
                String.format(
                        "%s likes",
                        String.valueOf(NumberUtils.format(photo.getLikes()))));

        artistNameTv.setText(photo.getUser().getName().toLowerCase());

        artistUsernameTv.setText(
                String.format("@%s", photo.getUser().getUsername()));

        Glide.with(this)
                .load(photo.getUser().getProfileImage().getLarge())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(artistPicIv);

        photoIv.setAspectRatio(((float) photo.getHeight())
                / ((float) photo.getWidth()));

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
                        hideLoading();
                        topGradientView.setVisibility(View.GONE);
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
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@NonNull Palette palette) {

                        backIb.setImageTintList(
                                ColorStateList.valueOf(
                                        getDrawableColor(palette, resource)
                                ));
                    }
                });

        Palette.from(resource)
                .maximumColorCount(3)
                .clearFilters()
                .setRegion(
                        picParentFl.getWidth() - 1 - sixteenDip - twentyFourDip,
                        sixteenDip,
                        picParentFl.getWidth() - 1 - sixteenDip,
                        sixteenDip + twentyFourDip)
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@NonNull Palette palette) {

                        ColorStateList drawableColorStateList =
                                ColorStateList.valueOf(
                                        getDrawableColor(palette, resource));

                        mainProgressBar.setIndeterminateTintList(
                                drawableColorStateList);
                    }
                });

        Palette.from(resource)
                .maximumColorCount(3)
                .clearFilters()
                .setRegion(
                        picParentFl.getWidth() - 1 - sixteenDip - twentyFourDip,
                        picParentFl.getHeight() - sixteenDip - twentyFourDip,
                        picParentFl.getWidth() - 1 - sixteenDip,
                        picParentFl.getHeight() - sixteenDip)
                .generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@NonNull Palette palette) {

                        ColorStateList drawableColorStateList =
                                ColorStateList.valueOf(
                                        getDrawableColor(palette, resource));

                        fullScreenIb.setImageTintList(
                                drawableColorStateList);
                    }
                });
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

    // to be thread safe
    @Override
    public synchronized void hideLoading() {
        loadCount += 1;
        if (loadCount >= 2) {
            mainProgressBar.setVisibility(View.GONE);
        }
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
    public void displayPhotoDescription(final Photo photo) {

        photoViewsCountBtn
                .setText(
                        String.format("%s views",
                                String.valueOf(
                                        NumberUtils.format(photo.getViews()))));

        photoDownloadsCountBtn
                .setText(
                        String.format("%s downloads",
                                String.valueOf(
                                        NumberUtils.format(photo.getDownloads()))));

        if (photo.getLocation().getTitle() != null) {

            photoLocationTv.setText(photo.getLocation().getTitle());

            photoLocationFl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri location =
                            Uri.parse(
                                    "geo:"
                                            + photo.getLocation().getLat()
                                            + ","
                                            + photo.getLocation().getLon()
                                            + "?z=14");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                    startActivity(mapIntent);
                }
            });
        }


        if (photo.getExif().getMake()!=null) {
            exifMakeBtn.setText(
                    photo.getExif().getMake() != null ? photo.getExif().getMake() : "--");
        }

        if (photo.getExif().getModel()!=null) {
            exifModelBtn.setText(
                    photo.getExif().getModel() != null ? photo.getExif().getModel() : "--");
        }

        if (photo.getExif().getExposureTime() != null) {
            exifExposureTimeBtn.setText(String.format("%ss", photo.getExif().getExposureTime()));
        }

        if (photo.getExif().getAperture() != null) {
            exifApertureBtn.setText(String.format("f/%s", photo.getExif().getAperture()));
        }

        if (photo.getExif().getIso() != 0) {
            exifIsoBtn.setText(String.format("%d", photo.getExif().getIso()));
        }

        if (photo.getExif().getFocalLength() != null) {
            exifFocalLengthBtn.setText(String.format("%smm", photo.getExif().getFocalLength()));
        }

        photoResolutionBtn.setText(String.format("%d x %d", photo.getWidth(), photo.getHeight()));

        dataLoaded = true;
    }

    @Override
    public String getCurPhotoId() {
        return ((Photo) getIntent().getParcelableExtra(PhotoDescriptionActivity.KEY_PHOTO)).getId();
    }

    @Override
    public void showLoading() {
        mainProgressBar.setVisibility(View.VISIBLE);
        photoInfoIb.setVisibility(View.GONE);
    }

    private @ColorInt
    int getDrawableColor(Palette palette, Bitmap resource) {

        boolean isDark;
        @ColorHelper.Lightness int lightness = ColorHelper.isDark(palette);
        if (lightness == ColorHelper.LIGHTNESS_UNKNOWN) {
            isDark = ColorHelper.isDark(
                    resource,
                    picParentFl.getWidth() / 2,
                    0);
        } else {
            isDark = lightness == ColorHelper.IS_DARK;
        }

        if (isDark) {

            return ContextCompat.getColor(
                    this,
                    R.color.grey2);
        } else {

            return ContextCompat.getColor(
                    this,
                    R.color.darkGrey1);
        }
    }

    public void startUserDescriptionActivity(Photo photo) {

        Intent i = new Intent(this, UserDescriptionActivity.class);
        i.putExtra(UserDescriptionActivity.KEY_USER, photo.getUser());

        artistPicIv.setTransitionName(getCurPhotoId());

        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this,
                        artistPicIv,
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
}
