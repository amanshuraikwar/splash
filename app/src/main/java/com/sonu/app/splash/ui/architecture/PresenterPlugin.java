package com.sonu.app.splash.ui.architecture;

import androidx.annotation.NonNull;

import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.NumberUtils;
import com.sonu.app.splash.util.PermissionsHelper;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class PresenterPlugin {

    public static class DownloadPhoto {

        @NonNull
        public static void downloadPhoto(Photo photo, BasePresenterImpl presenter) {

            if (PermissionsHelper.checkStoragePermission(presenter.getActivity())) {
                // handling it to downloader
                long downloadReference =
                        presenter.getAppDataStore().downloadPhoto(photo);

                presenter.getAppBus().downloadStarted.emit(downloadReference);

                PhotoDownload.Builder builder =
                        new PhotoDownload.Builder(
                                downloadReference,
                                NumberUtils.getCurrentTimeStamp());

                builder.photo(photo);

                // adding download to local db
                presenter.runInBackground(
                        () -> presenter.getAppDataStore().addPhotoDownload(builder.build()),
                        ignored -> { },
                        throwable -> { });
            }
        }
    }
}
