package com.sonu.app.splash.ui.about;

import android.os.Bundle;


import com.sonu.app.splash.R;
import com.sonu.app.splash.util.ActivityUtils;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 30/12/17.
 */

public class AboutActivity extends DaggerAppCompatActivity {

    @BindView(R.id.eddfl)
    ElasticDragDismissFrameLayout eddfl;

    @Inject
    AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

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
                finish();
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
