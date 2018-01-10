package com.android.sonu.ummsplash.ui.home;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sonu.ummsplash.R;
import com.android.sonu.ummsplash.ui.about.AboutActivity;
import com.android.sonu.ummsplash.ui.allphotos.AllPhotosFragment;
import com.android.sonu.ummsplash.ui.architecture.BaseFragment;
import com.android.sonu.ummsplash.ui.downloadinfo.DownloadInfoFragment;
import com.android.sonu.ummsplash.util.AnimUtils;
import com.android.sonu.ummsplash.util.ConnectionUtil;
import com.android.sonu.ummsplash.util.FragmentUtils;
import com.android.sonu.ummsplash.util.LogUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amanshuraikwar on 18/12/17.
 */

public class HomeFragment extends BaseFragment<HomeContract.Presenter>
        implements HomeContract.View {

    private static final String TAG = LogUtils.getLogTag(HomeFragment.class);

    private final HashMap<Integer, Integer> homeNavItemIdPosition = new HashMap<>();

    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    @BindView(R.id.supl)
    SlidingUpPanelLayout supl;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.persistentMessageFl)
    View networkErrorFl;

    @Inject
    AllPhotosFragment allPhotosFragment;

    @Inject
    DownloadInfoFragment downloadInfoFragment;

    private int curHomeNavItemId;

    private ConnectivityManager.NetworkCallback networkCallback =
            new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(Network network) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            networkErrorFl.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onLost(Network network) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            networkErrorFl.setVisibility(View.VISIBLE);
                        }
                    });
                }
            };

    @Inject
    public HomeFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeNavItemIdPosition.put(allPhotosFragment.getHomeNavItemId(), 0);

        // loading default fragment i.e AllPhotosFragment
        FragmentUtils.addFragmentToUi(getChildFragmentManager(),
                allPhotosFragment, R.id.contentFl);

        // loading default fragment i.e AllPhotosFragment
        FragmentUtils.addFragmentToUi(getChildFragmentManager(),
                downloadInfoFragment, R.id.bottomSheetContentFl);

        // initial home nav item id
        curHomeNavItemId = allPhotosFragment.getHomeNavItemId();

        // setting up bottom navigation view
        setupBnv();

        animateToolbar();
        animateFab();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ConnectionUtil.isConnected(getContext())) {
            networkErrorFl.setVisibility(View.GONE);
        } else {
            networkErrorFl.setVisibility(View.VISIBLE);
        }

        ConnectionUtil.registerNetworkCallback(getContext(), networkCallback);
    }

    @Override
    public void onPause() {
        super.onPause();

        ConnectionUtil.unregisterNetworkCallback(getContext(), networkCallback);
    }

    // this part is stolen from the inspirational app PLAID by legendary Nick Butcher
    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(300)
                    .setDuration(900)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getContext()));
        }
    }

    private void animateFab() {

        fab.setAlpha(0f);
        fab.setScaleX(0.8f);
        fab.setScaleY(0.8f);

        fab.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setStartDelay(300)
                .setDuration(900)
                .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getContext()));
    }

    private void setupBnv() {

        // bottom navigation view config
//        bnv.setTextVisibility(false);

        // on item selected listener
        bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Log.d(TAG, "onNavigationItemSelected:called");
                        Log.i(TAG, "onNavigationItemSelected:item="+item);

                        switch (item.getItemId()) {

                            case R.id.navigation_images:

                                if (curHomeNavItemId != allPhotosFragment.getHomeNavItemId()) {
                                    FragmentUtils.replaceFragmentInUi(getChildFragmentManager(),
                                            allPhotosFragment, R.id.contentFl);
                                    curHomeNavItemId = allPhotosFragment.getHomeNavItemId();
                                }
                                return true;
                        }
                        return false;
                    }
                }
        );

        // on item reselected listener
        bnv.setOnNavigationItemReselectedListener(
                new BottomNavigationView.OnNavigationItemReselectedListener() {
                    @Override
                    public void onNavigationItemReselected(@NonNull MenuItem item) {

                        Log.d(TAG, "onNavigationItemReselected:called");
                        Log.i(TAG, "onNavigationItemReselected:item="+item);
                        // do nothing
                    }
                }
        );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        FragmentManager fragmentManager = getChildFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        } else {
            return super.onBackPressed();
        }
    }

    @Override
    public void setBnvItemSelected(int homeNavItemId) {
        curHomeNavItemId = homeNavItemId;
        bnv.setSelectedItemId(
                bnv.getMenu().getItem(homeNavItemIdPosition.get(homeNavItemId)).getItemId());
    }

    @Override
    public void hideBottomSheet() {
        supl.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    @Override
    public void showBottomSheet() {
        supl.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
