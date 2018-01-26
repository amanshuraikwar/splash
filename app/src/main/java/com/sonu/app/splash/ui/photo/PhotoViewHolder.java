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

    @BindView(R.id.downloadIb)
    ImageButton downloadIb;

    public PhotoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final PhotoListItem listItem, FragmentActivity parentActivity) {

        Log.i(TAG, "bind:photo="+listItem.getPhoto());
        Log.i(TAG, "bind:url="+listItem.getPhoto().getUrlRegular());

        photoIv.setAspectRatio(((float)listItem.getPhoto().getHeight())
                        / ((float)listItem.getPhoto().getWidth()));

        Glide.with(parentActivity)
                .load(listItem.getPhoto().getUrlSmall())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photoIv);

        downloadIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.getOnClickListener().onDownloadBtnClick(listItem.getPhoto());
            }
        });

        int color = Color.parseColor(listItem.getPhoto().getColor());

        // setting foreground ripple dynamically
        parent.setForeground(DrawableUtils.createRippleDrawable(color));

        parent.setBackgroundColor(color);

        // unique transition name
        photoIv.setTransitionName(listItem.getPhoto().getId());

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.getOnClickListener().onClick(listItem.getPhoto(), photoIv);
            }
        });
    }
}
