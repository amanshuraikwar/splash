package com.sonu.app.splash.ui.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;

import com.sonu.app.splash.data.download.Downloader;
import com.sonu.app.splash.data.local.LocalStore;
import com.sonu.app.splash.data.media.CollectionRepository;
import com.sonu.app.splash.data.media.MediaRepository;
import com.sonu.app.splash.data.rss.RssRepository;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class HomeActivity extends DaggerAppCompatActivity {

    @Inject
    MediaRepository mediaRepository;

    @Inject
    CollectionRepository collectionRepository;

    @Inject
    RssRepository rssRepository;

    @Inject
    LocalStore localStore;

    @Inject
    Downloader downloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(
                this,
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT));
        super.onCreate(savedInstanceState);

        HomeCompose.setContent(
                this,
                mediaRepository,
                collectionRepository,
                rssRepository,
                localStore,
                downloader);
    }
}
