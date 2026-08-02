package com.sonu.app.splash.ui.collection;

import android.graphics.Color;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentActivity;
import androidx.cardview.widget.CardView;
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

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public class CollectionHorizontalViewHolder extends ViewHolder<CollectionHorizontalListItem> {

    private static final String TAG = LogUtils.getLogTag(CollectionHorizontalViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_collection_horizontal;
    public HeightRelativeAspectRatioImageView photoIv;
    TextView photosCountTv;
    TextView collectionTitleTv;
    ImageView artistProfileImageIv;
    TextView artistNameTv;
    CardView artistPicCv;
    View parent;

    public CollectionHorizontalViewHolder(View itemView) {
        super(itemView);
        photoIv = itemView.findViewById(R.id.photoIv);
        photosCountTv = itemView.findViewById(R.id.photosCountTv);
        collectionTitleTv = itemView.findViewById(R.id.collectionTitleTv);
        artistProfileImageIv = itemView.findViewById(R.id.artistProfileImageIv);
        artistNameTv = itemView.findViewById(R.id.artistNameTv);
        artistPicCv = itemView.findViewById(R.id.artistPicCv);
        parent = itemView.findViewById(R.id.parent);
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
