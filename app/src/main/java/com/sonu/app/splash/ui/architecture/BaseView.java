package com.sonu.app.splash.ui.architecture;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public interface BaseView {

    void showIoException(int titleStringRes, int messageStringRes);
    void showUnsplashApiException(int titleStringRes, int messageStringRes);
    void showUnknownException(String message);
}
