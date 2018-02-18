package com.sonu.app.splash.ui.collection;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.ui.widget.HeightRelativeAspectRatioImageView;
import com.sonu.app.splash.ui.widget.WidthRelativeAspectRatioImageView;
import com.sonu.app.splash.util.DrawableUtils;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class CollectionHorizontalViewHolder extends ViewHolder<CollectionHorizontalListItem> {

    private static final String TAG = LogUtils.getLogTag(CollectionHorizontalViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_collection_horizontal;

    @BindView(R.id.photoIv)
    public HeightRelativeAspectRatioImageView photoIv;

    @BindView(R.id.photosCountTv)
    TextView photosCountTv;

    @BindView(R.id.collectionTitleTv)
    TextView collectionTitleTv;

    @BindView(R.id.artistProfileImageIv)
    ImageView artistProfileImageIv;

    @BindView(R.id.artistNameTv)
    TextView artistNameTv;

    @BindView(R.id.artistPicCv)
    CardView artistPicCv;

    @BindView(R.id.parent)
    View parent;

    public CollectionHorizontalViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final CollectionHorizontalListItem listItem,
                     final FragmentActivity parentActivity) {

        photoIv.setAspectRatio(((float)listItem.getCollection().getCoverPhoto().getHeight())
                / ((float)listItem.getCollection().getCoverPhoto().getWidth()));

        Glide.with(parentActivity)
                .load(listItem.getCollection().getCoverPhoto().getPhotoUrls().getRegular())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photoIv);

        int color = Color.parseColor(listItem.getCollection().getCoverPhoto().getColor());

        // setting foreground ripple dynamically
        parent.setForeground(DrawableUtils.createRippleDrawable(color));

        parent.setBackgroundColor(color);

        // unique transition name
        artistProfileImageIv.setTransitionName(listItem.getCollection().getId()+"");

        parent.setOnClickListener(
                view ->
                        listItem.getOnClickListener().onClick(
                                listItem.getCollection(), artistProfileImageIv));

        collectionTitleTv.setText(listItem.getCollection().getTitle());

        photosCountTv.setText(String.format("%d photos", listItem.getCollection().getTotalPhotos()));

        artistNameTv.setText(listItem.getCollection().getUser().getName().toLowerCase());

        Glide.with(parentActivity)
                .load(listItem.getCollection().getUser().getProfileImage().getLarge())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().circleCrop())
                .into(artistProfileImageIv);

        artistPicCv.setOnClickListener(
                view ->
                        listItem.getOnClickListener().onArtistClick(
                                listItem.getCollection(), artistProfileImageIv));
    }
}
