package com.sonu.app.splash.ui.downloadinfo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.architecture.BaseFragment;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

public class DownloadInfoFragment
        extends BaseFragment<DownloadInfoContract.Presenter>
        implements DownloadInfoContract.View {

    @BindView(R.id.downloadIconIv)
    ImageView downloadIconIv;

    @BindView(R.id.downloadTitleTv)
    TextView downloadTitleTv;

    @BindView(R.id.downloadFileInfoTv)
    TextView downloadFileInfoTv;

    @BindView(R.id.progressBar)
    MaterialProgressBar progressBar;

    @BindView(R.id.downloadProgressTv)
    TextView downloadProgressTv;

    @BindView(R.id.cancelBtn)
    Button cancelBtn;

    @BindView(R.id.retryBtn)
    Button retryBtn;

    @BindView(R.id.enqueuedDownloadNoTv)
    TextView enqueuedDownloadNoTv;

    private String curPhotoId;

    @Inject
    public DownloadInfoFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_download_info, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    private View.OnClickListener retryOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getPresenter().onRetryClick(curPhotoId);
                }
            };

    private View.OnClickListener cancelOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getPresenter().onCancelClick(curPhotoId);
                }
            };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retryBtn.setOnClickListener(retryOnClickListener);
        cancelBtn.setOnClickListener(cancelOnClickListener);
    }

    @Override
    public void updateUi(String curFileName,
                         int curDownloadProgress,
                         int downloadQueueLength,
                         String curPhotoId) {

        this.curPhotoId = curPhotoId;
        downloadTitleTv.setTextColor(Color.BLACK);
        downloadFileInfoTv.setText(curFileName);
        enqueuedDownloadNoTv.setText(new StringBuffer((downloadQueueLength-1)+""));
        retryBtn.setVisibility(View.GONE);
        cancelBtn.setVisibility(View.GONE);


        downloadProgressTv.setTextColor(ContextCompat.getColor(getContext(), R.color.darkGrey3));

        downloadIconIv
                .setImageDrawable(
                        ContextCompat
                                .getDrawable(
                                        getContext(),
                                        R.drawable.ic_file_download_black_24dp));

        downloadIconIv
                .setImageTintList(
                        ColorStateList
                                .valueOf(ContextCompat.getColor(getContext(), R.color.darkGrey3)));
    }

    @Override
    public void showError(String curFileName,
                          int curDownloadProgress,
                          int downloadQueueLength,
                          String curPhotoId,
                          String error) {
        updateUi(curFileName, curDownloadProgress, downloadQueueLength, curPhotoId);
        downloadProgressTv.setTextColor(Color.RED);
        downloadProgressTv.setText("ERROR");
        retryBtn.setVisibility(View.VISIBLE);
        cancelBtn.setVisibility(View.VISIBLE);

        downloadIconIv
                .setImageDrawable(
                        ContextCompat
                                .getDrawable(
                                        getContext(),
                                        R.drawable.ic_error_black_24dp));

        downloadIconIv
                .setImageTintList(
                        ColorStateList
                                .valueOf(Color.RED));
    }

    @Override
    public void showProgress(long progress, long total) {
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String progressMb = decimalFormat.format(progress/((float) 1024*1024));
        String totalMb =  decimalFormat.format(total/((float) 1024*1024));
        int per = (int) ((100 * progress) / total);
        progressBar.setProgress(per);
        downloadProgressTv.setText(new StringBuffer(progressMb+"MB/"+totalMb+"MB"));
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
}
