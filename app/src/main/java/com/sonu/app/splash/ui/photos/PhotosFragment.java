package com.sonu.app.splash.ui.photos;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.content.allphotos.AllPhotosFragment;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosFragment;
import com.sonu.app.splash.ui.home.ViewPagerAdapter;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by amanshuraikwar on 01/02/18.
 */

public class PhotosFragment extends DaggerFragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    private ViewPagerAdapter viewPagerAdapter;

    @Inject
    AllPhotosFragment allPhotosFragment;

    @Inject
    CuratedPhotosFragment curatedPhotosFragment;

    @Inject
    public PhotosFragment() {
        // empty contrsuctor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_photos, container, false);
        viewPager = root.findViewById(R.id.viewPager);
        tabLayout = root.findViewById(R.id.tabLayout);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (viewPagerAdapter == null) {

            viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
            viewPagerAdapter.addFragment(allPhotosFragment, "all photos");
            viewPagerAdapter.addFragment(curatedPhotosFragment, "curated photos");
        }

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
