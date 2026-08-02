package com.sonu.app.splash.ui.about;

import android.os.Bundle;
import android.transition.TransitionInflater;


import com.sonu.app.splash.R;
import com.sonu.app.splash.util.ActivityUtils;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 30/12/17.
 */

public class AboutActivity extends DaggerAppCompatActivity {
    ElasticDragDismissFrameLayout eddfl;

    @Inject
    AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        eddfl = findViewById(R.id.eddfl);
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), aboutFragment, R.id.contentFl);

        eddfl.addListener(new ElasticDragDismissListener() {
            @Override
            public void onDrag(float elasticOffset,
                               float elasticOffsetPixels,
                               float rawOffset,
                               float rawOffsetPixels) {

            }

            @Override
            public void onDragDismissed() {
                // if we drag dismiss downward then the default reversal of the enter
                // transition would slide content upward which looks weird. So reverse it.
                if (eddfl.getTranslationY() > 0) {
                    getWindow().setReturnTransition(
                            TransitionInflater.from(AboutActivity.this)
                                    .inflateTransition(R.transition.about_return_downward));
                }
                finishAfterTransition();
            }
        });
    }



    @Override
    public void onBackPressed() {
        if (!aboutFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
