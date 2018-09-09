package io.github.amanshuraikwar.splash;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.github.amanshuraikwar.splash.di.DaggerAppComponent;

/**
 * Application Class.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 18/12/17.
 */
public class MyApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
