package com.sonu.app.splash.ui.userdescription;

import android.os.Bundle;

import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.sonu.app.splash.R;
import com.sonu.app.splash.util.FragmentUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 16/01/18.
 */

public class UserDescriptionActivity extends DaggerAppCompatActivity {

    public static final String KEY_PHOTO = "photo";

    @BindView(R.id.eddfl)
    ElasticDragDismissFrameLayout eddfl;

    @Inject
    UserDescriptionFragment userDescriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_description);
        ButterKnife.bind(this);

        Bundle arguments = new Bundle();
        arguments.putParcelable(KEY_PHOTO, getIntent().getParcelableExtra(KEY_PHOTO));
        userDescriptionFragment.setArguments(arguments);

        FragmentUtils
                .addFragmentToUi(
                        getSupportFragmentManager(), userDescriptionFragment,
                        R.id.contentFl);

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
        if (!userDescriptionFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
