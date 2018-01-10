package com.android.sonu.ummsplash.bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

@Module
public class AppBusModule {

    @Singleton
    @Provides
    static AppBus getAppBus() {
        return new AppBus();
    }
}
