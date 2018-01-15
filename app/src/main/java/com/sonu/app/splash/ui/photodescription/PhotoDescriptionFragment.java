package com.sonu.app.splash.ui.photodescription;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photoinfodialog.PhotoInfoDialog;
import com.sonu.app.splash.util.ColorHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 12/01/18.
 */

public class PhotoDescriptionFragment extends BaseFragment<PhotoDescriptionContract.Presenter>
        implements PhotoDescriptionContract.View {

    @BindView(R.id.picParentWfsl)
    View picParentWfsl;

    @BindView(R.id.photoIv)
    ImageView photoIv;

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

    private int loadCount;
    private PhotoDescription photoDescription;

    @Inject
    public PhotoDescriptionFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photo_description, container, false);
        ButterKnife.bind(this, root);
        return  root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Photo photo = getArguments().getParcelable(PhotoDescriptionActivity.KEY_PHOTO);

        int color = Color.parseColor(photo.getColor());

        getActivity().getWindow().setStatusBarColor(color);

        if (!ColorHelper.isDark(color)) {
            getActivity()
                    .getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        picParentWfsl.setBackgroundColor(color);

        if (photo.getDescription().equals("")) {
            photoDescriptionTv.setVisibility(View.GONE);
        } else {
            photoDescriptionTv.setText(photo.getDescription());
        }

        photoLikesCountBtn.setText(String.format("%s likes", String.valueOf(photo.getLikes())));

        artistNameTv.setText(photo.getArtistName());

        artistUsernameTv.setText(
                String.format("@%s", photo.getArtistUsername()));

        Glide.with(getActivity())
                .load(photo.getArtistProfileImageUrl())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(artistPicIv);

        Glide.with(getActivity())
                .asBitmap()
                .load(photo.getUrlRegular())
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
                    public boolean onResourceReady(final Bitmap resource,
                                                   Object model,
                                                   Target<Bitmap> target,
                                                   DataSource dataSource, boolean isFirstResource) {

                        hideLoading();

                        topGradientView.setVisibility(View.GONE);

                        final int twentyFourDip =
                                (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        24,
                                        getActivity().getResources().getDisplayMetrics());

                        final int sixteenDip =
                                (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        16,
                                        getActivity().getResources().getDisplayMetrics());

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
                                        picParentWfsl.getWidth() - 1 - sixteenDip - twentyFourDip,
                                        sixteenDip,
                                        picParentWfsl.getWidth() - 1 - sixteenDip,
                                        sixteenDip + twentyFourDip)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(@NonNull Palette palette) {

                                        ColorStateList drawableColorStateList =
                                                ColorStateList.valueOf(
                                                        getDrawableColor(palette, resource));

                                        mainProgressBar.setIndeterminateTintList(
                                                drawableColorStateList);

                                        photoInfoIb.setImageTintList(
                                                drawableColorStateList);
                                    }
                                });

                        return false;
                    }
                })
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(photoIv);

        backIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        photoInfoIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PhotoInfoDialog(getContext(), photoDescription).show();
            }
        });
    }

    private @ColorInt int getDrawableColor(Palette palette, Bitmap resource) {

        boolean isDark;
        @ColorHelper.Lightness int lightness = ColorHelper.isDark(palette);
        if (lightness == ColorHelper.LIGHTNESS_UNKNOWN) {
            isDark = ColorHelper.isDark(
                    resource,
                    picParentWfsl.getWidth() / 2,
                    0);
        } else {
            isDark = lightness == ColorHelper.IS_DARK;
        }

        if (isDark) {

            return ContextCompat.getColor(
                            getActivity(),
                            R.color.fog);
        } else {

            return ContextCompat.getColor(
                    getActivity(),
                    R.color.coal);
        }
    }

    // to be thread safe
    @Override
    public synchronized void hideLoading() {
        loadCount += 1;
        if (loadCount == 2) {
            mainProgressBar.setVisibility(View.GONE);
            photoInfoIb.setVisibility(View.VISIBLE);
        }
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
    public void displayPhotoDescription(final PhotoDescription photoDescription) {

        this.photoDescription = photoDescription;

        photoViewsCountBtn
                .setText(String.format("%s views", String.valueOf(photoDescription.getViews())));

        photoDownloadsCountBtn
                .setText(String.format("%s downloads", String.valueOf(photoDescription.getDownloads())));

        if (!photoDescription.getLocationTitle().equals("")) {
            photoLocationFl.setVisibility(View.VISIBLE);
            photoLocationTv.setText(photoDescription.getLocationTitle());
            photoLocationFl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri location =
                            Uri.parse(
                                    "geo:"
                                            + photoDescription.getLocationLat()
                                            + ","
                                            + photoDescription.getLocationLon()
                                            + "?z=14");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                    startActivity(mapIntent);
                }
            });
        }
    }

    @Override
    public String getCurPhotoId() {
        return ((Photo)getArguments().getParcelable(PhotoDescriptionActivity.KEY_PHOTO)).getId();
    }
}
