package com.sonu.app.splash.ui.search.allsearch;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.search.SearchFragment;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.fragment.FragmentUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by amanshuraikwar on 05/02/18.
 */

public class AllSearchActivity extends DaggerAppCompatActivity {

    private static final String TAG = LogUtils.getLogTag(AllSearchActivity.class);

    public static final String KEY_QUERY = "query";

    @BindView(R.id.contentFl)
    FrameLayout contentFl;

    @Inject
    SearchFragment searchFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_empty_frame);

        ButterKnife.bind(this);

        Bundle arguments = new Bundle();
        arguments.putString(KEY_QUERY, getIntent().getStringExtra(KEY_QUERY));

        searchFragment.setArguments(arguments);
        FragmentUtils.addFragmentToUi(getSupportFragmentManager(),
                searchFragment, R.id.contentFl);
    }
}
