package com.sonu.app.splash.bus;

import android.util.Pair;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class AppBus {

    // navigation related subjects
    public PublishSubject<Integer> onHomeNavItemVisible;

    // download related subjects
    public PublishSubject<Integer> onDownloadStateChange;
    public PublishSubject<Pair<Long, Long>> updateDownloadProgress;

    // general ui subjects
    public PublishSubject<String> sendQuickMessage;

    public AppBus() {

        // initialising all the publish subjects
        onHomeNavItemVisible = PublishSubject.create();

        onDownloadStateChange = PublishSubject.create();
        updateDownloadProgress = PublishSubject.create();

        sendQuickMessage = PublishSubject.create();
    }
}
