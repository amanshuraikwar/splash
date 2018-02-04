package com.sonu.app.splash.ui.home;

import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.collections.CollectionsFragment;
import com.sonu.app.splash.ui.content.curatedphotos.CuratedPhotosFragment;
import com.sonu.app.splash.ui.content.featuredcollections.FeaturedCollectionsFragment;
import com.sonu.app.splash.ui.photos.PhotosFragment;
import com.sonu.app.splash.ui.search.SearchFragment;
import com.sonu.app.splash.util.AnimUtils;
import com.sonu.app.splash.util.ConnectionUtil;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.fragment.FragNavController;
import com.sonu.app.splash.util.fragment.FragmentHistory;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class HomeFragment
        extends
        BaseFragment<HomeContract.Presenter>
        implements
        HomeContract.View,
        FragNavController.TransactionListener,
        FragNavController.RootFragmentListener {

    private static final String TAG = LogUtils.getLogTag(HomeFragment.class);

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.persistentMessage)
    View persistentMessage;

    @BindArray(R.array.tab_name)
    String[] TABS;

    @BindView(R.id.bottomTl)
    TabLayout bottomTl;

    private ConnectivityManager.NetworkCallback networkCallback =
            new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(Network network) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            persistentMessage.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onLost(Network network) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            persistentMessage.setVisibility(View.VISIBLE);
                        }
                    });
                }
            };

    private int[] mTabIconsDrawable = {
            R.drawable.landscape_drawable_selector,
            R.drawable.burst_mode_drawable_selector,
            R.drawable.search_drawable_selector,
            R.drawable.downloads_drawable_selector,
            R.drawable.person_drawable_selector};

    private FragNavController navController;

    private FragmentHistory fragmentHistory;

    @Inject
    PhotosFragment photosFragment;

    @Inject
    CollectionsFragment collectionsFragment;

    @Inject
    SearchFragment searchFragment;

    @Inject
    public HomeFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(com.sonu.app.splash.R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animateToolbar();

        initTab();

        animateBottomTl();

        fragmentHistory = new FragmentHistory();

        navController =
                FragNavController
                        .newBuilder(
                                savedInstanceState,
                                getChildFragmentManager(),
                                R.id.contentFl)
                .transactionListener(this)
                .rootFragmentListener(this, TABS.length)
                .build();

        switchTab(0);

        bottomTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                fragmentHistory.push(tab.getPosition());

                switchTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                navController.clearStack();

                switchTab(tab.getPosition());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ConnectionUtil.isConnected(getContext())) {
            persistentMessage.setVisibility(View.GONE);
        } else {
            persistentMessage.setVisibility(View.VISIBLE);
        }

        ConnectionUtil.registerNetworkCallback(getContext(), networkCallback);
    }

    @Override
    public void onPause() {
        super.onPause();

        ConnectionUtil.unregisterNetworkCallback(getContext(), networkCallback);
    }

    // this part is "stolen" from the inspirational app PLAID by legendary Nick Butcher
    private void animateToolbar() {

        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            title.setAlpha(0f);
            title.setTranslationX(100f);

            title.animate()
                    .alpha(1f)
                    .translationXBy(-100f)
                    .setStartDelay(300)
                    .setDuration(600)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getContext()));
        }
    }

    private void animateBottomTl() {

        bottomTl.setAlpha(0f);
        bottomTl.setTranslationY(60f);

        bottomTl.animate()
                .alpha(1f)
                .translationYBy(-60f)
                .setStartDelay(300)
                .setDuration(600)
                .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getContext()));
    }

    private void initTab() {

        if (bottomTl != null) {
            for (int i = 0; i < TABS.length; i++) {
                bottomTl.addTab(bottomTl.newTab());
                TabLayout.Tab tab = bottomTl.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(getTabView(i));
            }
        }
    }

    private View getTabView(int position) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_item_bottom, null);
        ImageView icon = view.findViewById(R.id.tab_icon);
        icon.setImageDrawable(ContextCompat.getDrawable(getActivity(), mTabIconsDrawable[position]));
        return view;
    }

    private void switchTab(int position) {

        navController.switchTab(position);
        updateToolbarTitle(position);
    }

    private void updateToolbarTitle(int position) {
        toolbar.setTitle(TABS[position]);
    }

    @Override
    public boolean onBackPressed() {

        if (!navController.isRootFragment()) {
            navController.popFragment();
            return true;
        } else {

            if (fragmentHistory.isEmpty()) {
                return super.onBackPressed();
            } else {


                if (fragmentHistory.getStackSize() > 1) {

                    int position = fragmentHistory.popPrevious();

                    switchTab(position);

                    updateTabSelection(position);

                    return true;

                } else {

                    switchTab(0);

                    updateTabSelection(0);

                    fragmentHistory.emptyStack();

                    return true;
                }
            }

        }
    }


    private void updateTabSelection(int currentTab){

        for (int i = 0; i <  TABS.length; i++) {
            TabLayout.Tab selectedTab = bottomTl.getTabAt(i);
            if(currentTab != i) {
                selectedTab.getCustomView().setSelected(false);
            }else{
                selectedTab.getCustomView().setSelected(true);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (navController != null) {
            navController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showIoException(int titleStringRes, int messageStringRes) {

    }

    @Override
    public void showUnsplashApiException(int titleStringRes, int messageStringRes) {

    }

    @Override
    public void showUnknownException(String message) {

    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
    }

    @Override
    public void onFragmentTransaction(Fragment fragment,
                                      FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
    }

    @Override
    public Fragment getRootFragment(int index) {

        switch (index) {

            case FragNavController.TAB1:
                return photosFragment;
            case FragNavController.TAB2:
                return collectionsFragment;
            case FragNavController.TAB3:
                return searchFragment;
            case FragNavController.TAB4:
                return photosFragment;
            case FragNavController.TAB5:
                return photosFragment;
        }

        throw new IllegalStateException("Need to send an index that we know");
    }
}
