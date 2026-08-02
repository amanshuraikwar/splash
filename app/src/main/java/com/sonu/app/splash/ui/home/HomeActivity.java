package com.sonu.app.splash.ui.home;

import android.os.Bundle;

import com.sonu.app.splash.data.DataManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class HomeActivity extends DaggerAppCompatActivity {

    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomeCompose.setContent(this, dataManager);
    }
}
