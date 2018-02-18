package com.sonu.app.splash.ui.photostats;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.robinhood.spark.SparkView;
import com.sonu.app.splash.R;
import com.sonu.app.splash.model.unsplash.PhotoStats;
import com.sonu.app.splash.ui.architecture.BaseActivity;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.NumberUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amanshuraikwar on 13/02/18.
 */

public class PhotoStatsActivity
        extends BaseActivity<PhotoStatsContract.Presenter>
        implements PhotoStatsContract.View {

    private static final String TAG = LogUtils.getLogTag(PhotoStatsActivity.class);
    public static final String KEY_PHOTO_ID = "photo_id";

    @BindView(R.id.photoLikesCountBtn)
    Button photoLikesCountBtn;

    @BindView(R.id.photoViewsCountBtn)
    Button photoViewsCountBtn;

    @BindView(R.id.photoDownloadsCountBtn)
    Button photoDownloadsCountBtn;

    @BindView(R.id.likesChangeTv)
    TextView likesChangeTv;

    @BindView(R.id.likesChangeQuantityTv)
    TextView likesChangeQuantityTv;

    @BindView(R.id.likesChangeResolutionTv)
    TextView likesChangeResolutionTv;

    @BindView(R.id.likesSparkView)
    SparkView likesSparkView;

    @BindView(R.id.likesScrubFl)
    View likesScrubFl;

    @BindView(R.id.likesScrubTv)
    TextView likesScrubTv;

    @BindView(R.id.viewsChangeTv)
    TextView viewsChangeTv;

    @BindView(R.id.viewsChangeQuantityTv)
    TextView viewsChangeQuantityTv;

    @BindView(R.id.viewsChangeResolutionTv)
    TextView viewsChangeResolutionTv;

    @BindView(R.id.viewsSparkView)
    SparkView viewsSparkView;

    @BindView(R.id.viewsScrubFl)
    View viewsScrubFl;

    @BindView(R.id.viewsScrubTv)
    TextView viewsScrubTv;

    @BindView(R.id.downloadsChangeTv)
    TextView downloadsChangeTv;

    @BindView(R.id.downloadsChangeQuantityTv)
    TextView downloadsChangeQuantityTv;

    @BindView(R.id.downloadsChangeResolutionTv)
    TextView downloadsChangeResolutionTv;

    @BindView(R.id.downloadsSparkView)
    SparkView downloadsSparkView;

    @BindView(R.id.downloadsScrubFl)
    View downloadsScrubFl;

    @BindView(R.id.downloadsScrubTv)
    TextView downloadsScrubTv;

    @BindView(R.id.eddfl)
    ElasticDragDismissFrameLayout eddfl;

    @BindView(R.id.footerTv)
    TextView footerTv;

    @BindView(R.id.loadingWrapperFl)
    View loadingWrapperFl;

    @BindView(R.id.loadingPb)
    ProgressBar loadingPb;

    @BindView(R.id.loadingRetryBtn)
    Button loadingRetryBtn;

    @BindView(R.id.likesWrapperLl)
    View likesWrapperLl;

    @BindView(R.id.downloadsWrapperLl)
    View downloadsWrapperLl;

    @BindView(R.id.viewsWrapperLl)
    View viewsWrapperLl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photostats);
        ButterKnife.bind(this);

        initSparkViews();

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

        loadingRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPresenter().getData();
            }
        });
    }

    private void initSparkViews() {


        likesSparkView.setScrubListener(value -> {
            if (value == null) {
                likesScrubTv.setText("");
            } else {
                likesScrubTv.setText(String.valueOf(NumberUtils.format((int)value))+" likes");
            }

        });

        likesScrubTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    likesScrubFl.setVisibility(View.GONE);
                } else {
                    likesScrubFl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        viewsSparkView.setScrubListener(value -> {
            if (value == null) {
                viewsScrubTv.setText("");
            } else {
                viewsScrubTv.setText(String.valueOf(NumberUtils.format((int)value))+" views");
            }

        });

        viewsScrubTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    viewsScrubFl.setVisibility(View.GONE);
                } else {
                    viewsScrubFl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        downloadsSparkView.setScrubListener(value -> {
            if (value == null) {
                downloadsScrubTv.setText("");
            } else {
                downloadsScrubTv.setText(String.valueOf(NumberUtils.format((int)value))+" downloads");
            }

        });

        downloadsScrubTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    downloadsScrubFl.setVisibility(View.GONE);
                } else {
                    downloadsScrubFl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void updateUi(PhotoStats photoStats) {

        photoLikesCountBtn.setText(
                String.format(
                        "%s likes",
                        String.valueOf(NumberUtils.format(photoStats.getLikes().getTotal()))));

        photoViewsCountBtn
                .setText(
                        String.format("%s views",
                                String.valueOf(
                                        NumberUtils.format(photoStats.getViews().getTotal()))));

        photoDownloadsCountBtn
                .setText(
                        String.format("%s downloads",
                                String.valueOf(
                                        NumberUtils.format(photoStats.getDownloads().getTotal()))));

        likesChangeTv.setText(
                String.valueOf(
                        NumberUtils.format(photoStats.getLikes().getChange())));

        likesChangeQuantityTv.setText(
                String.valueOf(
                        NumberUtils.format(photoStats.getLikes().getQuantity())));

        likesChangeResolutionTv.setText(photoStats.getLikes().getResolution());

        ;
        likesSparkView.setAdapter(
                new SimpleSparkAdapter(
                        new ArrayList<>(photoStats.getLikes().getValues().values())));

        viewsChangeTv.setText(
                String.valueOf(
                        NumberUtils.format(photoStats.getViews().getChange())));

        viewsChangeQuantityTv.setText(
                String.valueOf(
                        NumberUtils.format(photoStats.getViews().getQuantity())));

        viewsChangeResolutionTv.setText(photoStats.getViews().getResolution());

        viewsSparkView.setAdapter(
                new SimpleSparkAdapter(
                        new ArrayList<>(photoStats.getViews().getValues().values())));

        downloadsChangeTv.setText(
                String.valueOf(
                        NumberUtils.format(photoStats.getDownloads().getChange())));

        downloadsChangeQuantityTv.setText(
                String.valueOf(
                        NumberUtils.format(photoStats.getDownloads().getQuantity())));

        downloadsChangeResolutionTv.setText(photoStats.getDownloads().getResolution());

        downloadsSparkView.setAdapter(
                new SimpleSparkAdapter(
                        new ArrayList<>(photoStats.getDownloads().getValues().values())));
    }

    @Override
    public String getPhotoId() {
        return getIntent().getStringExtra(KEY_PHOTO_ID);
    }

    @Override
    public void showLoading() {

        likesWrapperLl.setVisibility(View.GONE);
        viewsWrapperLl.setVisibility(View.GONE);
        downloadsWrapperLl.setVisibility(View.GONE);
        footerTv.setVisibility(View.GONE);
        loadingWrapperFl.setVisibility(View.VISIBLE);
        loadingPb.setVisibility(View.VISIBLE);
        loadingRetryBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {

        loadingWrapperFl.setVisibility(View.GONE);
        likesWrapperLl.setVisibility(View.VISIBLE);
        viewsWrapperLl.setVisibility(View.VISIBLE);
        downloadsWrapperLl.setVisibility(View.VISIBLE);
        footerTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {

        likesWrapperLl.setVisibility(View.GONE);
        viewsWrapperLl.setVisibility(View.GONE);
        downloadsWrapperLl.setVisibility(View.GONE);
        footerTv.setVisibility(View.GONE);
        loadingWrapperFl.setVisibility(View.VISIBLE);
        loadingPb.setVisibility(View.GONE);
        loadingRetryBtn.setVisibility(View.VISIBLE);
    }
}
