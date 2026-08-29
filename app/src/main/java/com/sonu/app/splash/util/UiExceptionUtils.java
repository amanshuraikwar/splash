package com.sonu.app.splash.util;

import android.content.Context;

import com.sonu.app.splash.R;
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
        } else {
            adapterListener.showError(0, "", e.getMessage());
        }
    }
}
