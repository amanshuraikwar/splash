package com.sonu.app.splash;

import com.sonu.app.splash.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class MyApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // uncomment if you want to ignore exceptions by rxjava
//        RxJavaPlugins.setErrorHandler(Functions.<Throwable>emptyConsumer());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
