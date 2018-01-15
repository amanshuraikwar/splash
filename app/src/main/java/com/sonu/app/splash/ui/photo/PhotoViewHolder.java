package com.sonu.app.splash.ui.photo;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.ui.widget.WidthRelativeAspectRatioImageView;
import com.sonu.app.splash.util.ColorHelper;
import com.sonu.app.splash.util.DrawableUtils;
import com.sonu.app.splash.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class PhotoViewHolder extends ViewHolder<PhotoListItem> {

    private static final String TAG = LogUtils.getLogTag(PhotoViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_photo;

    @BindView(R.id.photoIv)
    public WidthRelativeAspectRatioImageView photoIv;

    @BindView(R.id.parent)
    View parent;

    @BindView(R.id.photoDescriptionTv)
    TextView photoDescriptionTv;

    @BindView(R.id.photoLikesTv)
    TextView photoLikesTv;

    @BindView(R.id.artistNameTv)
    TextView artistNameTv;

    @BindView(R.id.artistProfileImageIv)
    ImageView artistProfileImageIv;

    @BindView(R.id.downloadIb)
    ImageButton downloadIb;

    @BindView(R.id.photoResolutionTv)
    TextView photoResolutionTv;


    public PhotoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final PhotoListItem listItem, FragmentActivity parentActivity) {

        Log.i(TAG, "bind:photo="+listItem.getPhoto());
        Log.i(TAG, "bind:url="+listItem.getPhoto().getUrlRegular());

        photoDescriptionTv.setText(listItem.getPhoto().getDescription());
        photoLikesTv.setText(String.valueOf(listItem.getPhoto().getLikes()+" likes"));
        artistNameTv.setText(listItem.getPhoto().getArtistName());

        photoIv.setAspectRatio(((float)listItem.getPhoto().getHeight())
                        / ((float)listItem.getPhoto().getWidth()));

        Glide.with(parentActivity)
                .load(listItem.getPhoto().getUrlSmall())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photoIv);

        Glide.with(parentActivity)
                .load(listItem.getPhoto().getArtistProfileImageUrl())
                .apply(new RequestOptions().centerCrop().circleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(artistProfileImageIv);

        downloadIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.getOnClickListener().onDownloadBtnClick(listItem.getPhoto());
            }
        });

        int color = Color.parseColor(listItem.getPhoto().getColor());

        if (ColorHelper.isDark(color)) {
            artistNameTv.setTextColor(ColorHelper.LIGHT_TEXT_COLOR);
        } else {
            artistNameTv.setTextColor(ColorHelper.DARK_TEXT_COLOR);
        }

        // setting foreground ripple dynamically
        parent.setForeground(DrawableUtils.createRippleDrawable(color));

        parent.setBackgroundColor(color);

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.getOnClickListener().onClick(listItem.getPhoto());
            }
        });

        photoResolutionTv
                .setText(
                        new StringBuffer(
                                listItem.getPhoto().getWidth()
                                        +" x "+listItem.getPhoto().getHeight()));

    }
}
