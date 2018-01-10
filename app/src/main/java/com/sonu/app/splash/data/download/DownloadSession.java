package com.sonu.app.splash.data.download;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 29/12/17.
 */

// final to prevent subclassing

public final class DownloadSession {

    private boolean active;
    private PhotoDownloadService.DownloadState downloadState;

    @Inject
    public DownloadSession() {

        active = false;
    }

    public boolean isSessionActive() {
        return active;
    }

    void setSessionActive(boolean active) {
        this.active = active;
    }

    void setDownloadState(PhotoDownloadService.DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public PhotoDownloadService.DownloadState getDownloadState() {
        return downloadState;
    }
}
