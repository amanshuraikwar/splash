package com.sonu.app.splash.ui.collections;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.content.allcollections.AllCollectionsFragment;
import com.sonu.app.splash.ui.content.allphotos.AllPhotosFragment;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosFragment;
import com.sonu.app.splash.ui.content.featuredcollections.FeaturedCollectionsFragment;
import com.sonu.app.splash.ui.home.ViewPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by amanshuraikwar on 01/02/18.
 */

public class CollectionsFragment extends DaggerFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private ViewPagerAdapter viewPagerAdapter;

    @Inject
    AllCollectionsFragment allCollectionsFragment;

    @Inject
    FeaturedCollectionsFragment featuredCollectionsFragment;

    @Inject
    public CollectionsFragment() {
        // empty contrsuctor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_collections, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (viewPagerAdapter == null) {

            viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
            viewPagerAdapter.addFragment(allCollectionsFragment, "all collections");
            viewPagerAdapter.addFragment(featuredCollectionsFragment, "featured collections");
        }

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
