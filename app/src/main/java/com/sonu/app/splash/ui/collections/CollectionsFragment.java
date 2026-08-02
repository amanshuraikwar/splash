package com.sonu.app.splash.ui.collections;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.content.allcollections.AllCollectionsFragment;
import com.sonu.app.splash.ui.content.featuredcollections.FeaturedCollectionsFragment;
import com.sonu.app.splash.ui.home.ViewPagerAdapter;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by amanshuraikwar on 01/02/18.
 */

public class CollectionsFragment extends DaggerFragment {
    ViewPager viewPager;
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
        viewPager = root.findViewById(R.id.viewPager);
        tabLayout = root.findViewById(R.id.tabLayout);
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
