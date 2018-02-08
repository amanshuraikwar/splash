package com.sonu.app.splash.ui.download;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.sonu.app.splash.R;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.ui.list.ViewHolder;
import com.sonu.app.splash.ui.widget.WidthRelativeAspectRatioImageView;
import com.sonu.app.splash.util.DrawableUtils;
import com.sonu.app.splash.util.LogUtils;

import butterknife.BindView;

/**
 * Created by amanshuraikwar on 20/12/17.
 */

public class DownloadViewHolder extends ViewHolder<DownloadListItem> {

    private static final String TAG = LogUtils.getLogTag(DownloadViewHolder.class);

    @LayoutRes
    public static final int LAYOUT = R.layout.item_download;

    @BindView(R.id.photoIv)
    public WidthRelativeAspectRatioImageView photoIv;

    @BindView(R.id.fileNameTv)
    TextView fileNameTv;

    @BindView(R.id.parent)
    View parent;

    @BindView(R.id.downloadStatusTv)
    TextView downloadStatusTv;

    @BindView(R.id.downloadTimestampTv)
    TextView downloadTimestampTv;

    @BindView(R.id.downloadStatusCv)
    CardView downloadStatusCv;

    public DownloadViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(final DownloadListItem listItem, FragmentActivity parentActivity) {

        PhotoDownload photoDownload = listItem.getPhotoDownload();

        Log.i(TAG, "bind:photoDownload="+photoDownload);

        fileNameTv.setText(photoDownload.getDownloadedFileName());

        switch (photoDownload.getStatus()) {
            case PENDING:
                downloadStatusTv.setText("pending");
                downloadStatusCv
                        .setCardBackgroundColor(
                                parentActivity.getColor(R.color.yellow));
                downloadStatusTv.setTextColor(
                        parentActivity.getColor(R.color.primaryText));
                break;
            case FAILED:
                downloadStatusTv.setText("failed");
                downloadStatusCv
                        .setCardBackgroundColor(
                                parentActivity.getColor(R.color.red));
                downloadStatusTv.setTextColor(
                        parentActivity.getColor(R.color.primaryTextLight));
                break;
            case PAUSED:
                downloadStatusTv.setText("paused");
                downloadStatusCv
                        .setCardBackgroundColor(
                                parentActivity.getColor(R.color.orange));
                downloadStatusTv.setTextColor(
                        parentActivity.getColor(R.color.primaryTextLight));
                break;
            case RUNNING:
                downloadStatusTv.setText("downloading");
                downloadStatusCv
                        .setCardBackgroundColor(
                                parentActivity.getColor(R.color.lightBlueN));
                downloadStatusTv.setTextColor(
                        parentActivity.getColor(R.color.primaryTextLight));
                break;
            case SUCCESSFUL:
                downloadStatusTv.setText("downloaded");
                downloadStatusCv
                        .setCardBackgroundColor(
                                parentActivity.getColor(R.color.green));
                downloadStatusTv.setTextColor(
                        parentActivity.getColor(R.color.primaryTextLight));
                break;

        }

        downloadTimestampTv.setText(listItem.getPhotoDownload().getDownloadTimestamp());

        photoIv.setAspectRatio(((float) photoDownload.getHeight())
                        / ((float) photoDownload.getWidth()));

        Glide.with(parentActivity)
                .load(photoDownload.getPhotoUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photoIv);

        int color = Color.parseColor(photoDownload.getColor());

        // setting foreground ripple dynamically
        photoIv.setForeground(DrawableUtils.createRippleDrawable(color));

        photoIv.setBackgroundColor(color);

        // unique transition name
        photoIv.setTransitionName(photoDownload.getPhotoId());

        photoIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listItem.getOnClickListener().onPhotoClick(listItem.getPhotoDownload(), photoIv);
            }
        });

    }
}
