package com.sonu.app.splash.bus;

import android.util.Pair;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class AppBus {

    // navigation related subjects
    public EventChannel<Integer> onHomeNavItemVisible;

    // download related subjects
    public EventChannel<Integer> onDownloadStateChange;
    public EventChannel<Pair<Long, Long>> updateDownloadProgress;

    // general ui subjects
    public EventChannel<String> sendQuickMessage;

    // download notification
    public EventChannel<Long> downloadStarted;

    public AppBus() {

        // initialising all the publish subjects
        onHomeNavItemVisible = new EventChannel<>();

        onDownloadStateChange = new EventChannel<>();
        updateDownloadProgress = new EventChannel<>();

        sendQuickMessage = new EventChannel<>();

        downloadStarted = new EventChannel<>();
    }
}
