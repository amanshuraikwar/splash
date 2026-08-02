package com.sonu.app.splash.ui.home;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.sonu.app.splash.R;
import com.sonu.app.splash.util.ActivityUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class HomeActivity extends DaggerAppCompatActivity {
    FrameLayout contentFl;

    @Inject
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.sonu.app.splash.R.layout.activity_home);
        contentFl = findViewById(com.sonu.app.splash.R.id.contentFl);
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), homeFragment, R.id.contentFl);
    }

    @Override
    public void onBackPressed() {
        if (!homeFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
