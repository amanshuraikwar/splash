package com.sonu.app.splash.ui.photo;

import android.view.View;

/**
 * Created by amanshuraikwar on 22/12/17.
 */

public interface PhotoOnClickListener {
    void onDownloadBtnClick(Photo photo);
    void onClick(Photo photo, View transitionView);
}
