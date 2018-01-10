package com.android.sonu.ummsplash.data.download;

import javax.inject.Inject;
import javax.inject.Singleton;

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
