package com.sonu.app.splash.ui.architecture;

import android.support.annotation.NonNull;

import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.util.NumberUtils;
import com.sonu.app.splash.util.PermissionsHelper;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 07/02/18.
 */

public class PresenterPlugin {

    public static class DownloadPhoto {

        @NonNull
        public static Disposable downloadPhoto(Photo photo, BasePresenterImpl presenter) {

            if (PermissionsHelper.checkStoragePermission(presenter.getActivity())) {
                // handling it to downloader
                long downloadReference =
                        presenter.getDataManager().downloadPhoto(photo);

                presenter.getAppBus().downloadStarted.onNext(downloadReference);

                PhotoDownload.Builder builder =
                        new PhotoDownload.Builder(
                                downloadReference,
                                NumberUtils.getCurrentTimeStamp());

                builder.photo(photo);

                // adding download to local db
                return presenter
                        .getDataManager()
                        .addPhotoDownload(builder.build())
                        .observeOn(Schedulers.newThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            }

            return new Disposable() {

                @Override
                public void dispose() {

                }

                @Override
                public boolean isDisposed() {
                    return false;
                }
            };
        }
    }
}
