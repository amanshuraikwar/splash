package com.sonu.app.splash.util;

import android.content.Context;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.network.unsplashapi.UnsplashApiException;
import com.sonu.app.splash.ui.architecture.BaseView;
import com.sonu.app.splash.ui.list.ContentListAdapter;

import java.io.IOException;

/**
 * Created by amanshuraikwar on 18/01/18.
 */

public class UiExceptionUtils {

    public static void handleUiException(Throwable e,
                                         ContentListAdapter.AdapterListener adapterListener,
                                         Context context) {

        if (e instanceof IOException) {

            adapterListener.showError(0,
                    context.getString(R.string.io_exception_title),
                    context.getString(R.string.io_exception_message)
            );
        } else if (e instanceof UnsplashApiException) {

            if (((UnsplashApiException) e)
                    .getCode() == UnsplashApiException.CODE_API_LIMIT_EXCEEDED) {

                adapterListener.showError(0,
                        context.getString(R.string.unsplash_api_rate_limit_exceed_title),
                        context.getString(R.string.unsplash_api_rate_limit_exceed_message)
                );
            } else {

                adapterListener.showError(0,
                        context.getString(R.string.unsplash_api_unknown_exception_title),
                        context.getString(R.string.unsplash_api_unknown_exception_message)
                );
            }
        } else {
            adapterListener.showError(0, "", e.getMessage());
        }
    }
}
