package com.sonu.app.splash.ui.downloadinfo;

import com.sonu.app.splash.ui.architecture.BasePresenter;
import com.sonu.app.splash.ui.architecture.BaseView;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

public interface DownloadInfoContract {

    interface View extends BaseView {

        void updateUi(String curFileName,
                      int curDownloadProgress,
                      int downloadQueueLength,
                      String curPhotoId);

        void showError(String curFileName,
                       int curDownloadProgress,
                       int downloadQueueLength,
                       String curPhotoId,
                       String error);

        void showProgress(long progress, long total);
    }

    interface Presenter extends BasePresenter<View> {
        void onCancelClick(String photoId);
        void onRetryClick(String photoId);
    }
}
