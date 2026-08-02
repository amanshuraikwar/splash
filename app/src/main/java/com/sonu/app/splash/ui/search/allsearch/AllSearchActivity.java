package com.sonu.app.splash.ui.search.allsearch;

import android.app.FragmentManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener;
import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.search.SearchFragment;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.fragment.FragmentUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 05/02/18.
 */

public class AllSearchActivity extends DaggerAppCompatActivity {

    private static final String TAG = LogUtils.getLogTag(AllSearchActivity.class);

    public static final String KEY_QUERY = "query";
    FrameLayout contentFl;
    ElasticDragDismissFrameLayout eddfl;

    @Inject
    SearchFragment searchFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_empty_frame);

        contentFl = findViewById(R.id.contentFl);

        eddfl = findViewById(R.id.eddfl);
        Bundle arguments = new Bundle();
        arguments.putString(KEY_QUERY, getIntent().getStringExtra(KEY_QUERY));

        searchFragment.setSearchIconDrawableId(R.drawable.ic_arrow_back_black_24dp);
        searchFragment.setSearchIbOnClickListener(view -> finishAfterTransition());

        searchFragment.setArguments(arguments);
        FragmentUtils.addFragmentToUi(getSupportFragmentManager(),
                searchFragment, R.id.contentFl);

        eddfl.addListener(new ElasticDragDismissListener() {
            @Override
            public void onDrag(float elasticOffset,
                               float elasticOffsetPixels,
                               float rawOffset,
                               float rawOffsetPixels) {

            }

            @Override
            public void onDragDismissed() {
                finishAfterTransition();
            }
        });
    }
}
