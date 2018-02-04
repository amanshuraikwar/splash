package com.sonu.app.splash.util;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.ui.list.ContentListAdapter;

import java.io.IOException;

/**
 * Created by amanshuraikwar on 18/01/18.
 */

public class UiExceptionUtils {

    public static void handleUiException(Throwable e, BaseView view) {

        if (e instanceof IOException) {

            view.showIoException(
                    R.string.io_exception_title,
                    R.string.io_exception_message
            );
        } else if (e instanceof UnsplashApiException) {

            if (((UnsplashApiException) e)
                    .getCode() == UnsplashApiException.CODE_API_LIMIT_EXCEEDED) {

                view.showUnsplashApiException(
                        R.string.unsplash_api_rate_limit_exceed_title,
                        R.string.unsplash_api_rate_limit_exceed_message);
            } else {

                view.showUnsplashApiException(
                        R.string.unsplash_api_unknown_exception_title,
                        R.string.unsplash_api_unknown_exception_message);
            }
        } else {
            view.showUnknownException(e.getMessage());
        }
    }

    public static void handleUiException(Throwable e, ContentListAdapter.AdapterListener adapterListener) {

        if (e instanceof IOException) {

            adapterListener.showIoException(
                    R.string.io_exception_title,
                    R.string.io_exception_message
            );
        } else if (e instanceof UnsplashApiException) {

            if (((UnsplashApiException) e)
                    .getCode() == UnsplashApiException.CODE_API_LIMIT_EXCEEDED) {

                adapterListener.showUnsplashApiException(
                        R.string.unsplash_api_rate_limit_exceed_title,
                        R.string.unsplash_api_rate_limit_exceed_message);
            } else {

                adapterListener.showUnsplashApiException(
                        R.string.unsplash_api_unknown_exception_title,
                        R.string.unsplash_api_unknown_exception_message);
            }
        } else {
            adapterListener.showUnknownException(e.getMessage());
        }
    }
}
