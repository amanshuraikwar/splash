package com.sonu.app.splash.ui.photo;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.ui.widget.HeightRelativeAspectRatioImageView;
import com.sonu.app.splash.util.DrawableUtils;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class PhotoHorizontalViewHolder extends ViewHolder<PhotoHorizontalListItem> {

    private static final String TAG = LogUtils.getLogTag(PhotoHorizontalViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_photo_horizontal;

    @BindView(R.id.photoIv)
    public HeightRelativeAspectRatioImageView photoIv;

    @BindView(R.id.parent)
    View parent;

    @BindView(R.id.downloadIb)
    ImageButton downloadIb;

    public PhotoHorizontalViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final PhotoHorizontalListItem listItem, FragmentActivity parentActivity) {

        Log.i(TAG, "bind:photo="+listItem.getPhoto());

        photoIv.setAspectRatio(((float)listItem.getPhoto().getHeight())
                        / ((float)listItem.getPhoto().getWidth()));

        Glide.with(parentActivity)
                .load(listItem.getPhoto().getPhotoUrls().getSmall())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photoIv);

        downloadIb.setOnClickListener(
                view -> listItem.getOnClickListener().onDownloadBtnClick(listItem.getPhoto()));

        int color = Color.parseColor(listItem.getPhoto().getColor());

        // setting foreground ripple dynamically
        parent.setForeground(DrawableUtils.createRippleDrawable(color));

        parent.setBackgroundColor(color);

        // unique transition name
        photoIv.setTransitionName(listItem.getPhoto().getId());

        parent.setOnClickListener(
                view -> listItem.getOnClickListener().onClick(listItem.getPhoto(), photoIv));
    }
}
