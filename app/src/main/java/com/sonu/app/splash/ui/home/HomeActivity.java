package com.sonu.app.splash.ui.home;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.sonu.app.splash.util.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class HomeActivity extends DaggerAppCompatActivity {

    @BindView(com.sonu.app.splash.R.id.contentFl)
    FrameLayout contentFl;

    @Inject
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.sonu.app.splash.R.layout.activity_home);
        ButterKnife.bind(this);

        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), homeFragment, com.sonu.app.splash.R.id.contentFl);
    }

    @Override
    public void onBackPressed() {
        if (!homeFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
