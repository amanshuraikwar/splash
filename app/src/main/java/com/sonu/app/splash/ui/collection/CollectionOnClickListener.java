package com.sonu.app.splash.ui.collection;

import android.view.View;

/**
 * Created by amanshuraikwar on 28/01/18.
 */

public interface CollectionOnClickListener {
    void onClick(Collection collection, View transitionView);
    void onArtistClick(Collection collection, View transitionView);
}
