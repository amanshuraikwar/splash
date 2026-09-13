package com.sonu.app.splash.di;

import android.app.Application;

import com.sonu.app.splash.MyApp;
import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.bus.AppBusModule;
import com.sonu.app.splash.data.AppDataStore;
import com.sonu.app.splash.data.AppDataStoreModule;
import com.sonu.app.splash.data.media.MediaRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

@Singleton
@Component(modules = {AppModule.class,
        AppDataStoreModule.class,
        AppBusModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MyApp> {

    AppDataStore getAppDataStore();
    MediaRepository getMediaRepository();
    AppBus getAppBus();

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
