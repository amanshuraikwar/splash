package com.sonu.app.splash.ui.search;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.content.searchcollections.SearchCollectionsFragment;
import com.sonu.app.splash.ui.content.searchphotos.SearchPhotosFragment;
import com.sonu.app.splash.ui.content.searchusers.SearchUsersFragment;
import com.sonu.app.splash.ui.home.ViewPagerAdapter;
import com.sonu.app.splash.ui.search.allsearch.AllSearchActivity;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 01/02/18.
 */

public class SearchFragment
        extends BaseFragment<SearchContract.Presenter>
        implements SearchContract.View {

    private static final String TAG = LogUtils.getLogTag(SearchFragment.class);
    ImageButton searchIb;
    EditText searchEt;
    ImageButton clearIb;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Inject
    SearchPhotosFragment searchPhotosFragment;

    @Inject
    SearchCollectionsFragment searchCollectionsFragment;

    @Inject
    SearchUsersFragment searchUsersFragment;

    private ViewPagerAdapter adapter;
    private boolean firstQuery = true;

    private int searchIconDrawableId = R.drawable.ic_search_active_24dp;
    private View.OnClickListener searchIbOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // do nothing
                }
            };

    @Inject
    Activity host;

    @Inject
    public SearchFragment() {
        // empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);
        searchIb = root.findViewById(R.id.searchIb);
        searchEt = root.findViewById(R.id.searchEt);
        clearIb = root.findViewById(R.id.clearIb);
        tabLayout = root.findViewById(R.id.tabLayout);
        viewPager = root.findViewById(R.id.viewPager);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getInitialQuery() != null) {

            searchEt.setText(getInitialQuery());
        }

        searchEt.setOnEditorActionListener((textView, i, keyEvent) -> {

            boolean handled = false;
            if (i == EditorInfo.IME_ACTION_SEARCH) {

                getPresenter().onSearchClick(textView.getText().toString());
                handled = true;
            }
            return handled;
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 == 0) {
                    clearIb.setVisibility(View.GONE);
                } else {
                    clearIb.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        clearIb.setOnClickListener(view1 -> searchEt.setText(""));

        searchIb.setImageDrawable(ContextCompat.getDrawable(host, searchIconDrawableId));
        searchIb.setOnClickListener(searchIbOnClickListener);
    }

    public void setSearchIconDrawableId(int searchIconDrawableId) {
        this.searchIconDrawableId = searchIconDrawableId;
    }

    public void setSearchIbOnClickListener(View.OnClickListener searchIbOnClickListener) {
        this.searchIbOnClickListener = searchIbOnClickListener;
    }

    @Override
    public void initViewPager(String query) {

        Bundle b = new Bundle();
        b.putString(
                SearchPhotosFragment.KEY_QUERY,
                query);
        b.putString(
                SearchCollectionsFragment.KEY_QUERY,
                query);
        b.putString(
                SearchUsersFragment.KEY_QUERY,
                query);

        searchPhotosFragment.setArguments(b);
        searchCollectionsFragment.setArguments(b);
        searchUsersFragment.setArguments(b);

        if (adapter == null) {

            adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(searchPhotosFragment, "photos");
            adapter.addFragment(searchCollectionsFragment, "collections");
            adapter.addFragment(searchUsersFragment, "users");
        }

        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        firstQuery = false;
    }

    @Override
    public void setQuery(String query) {

        searchPhotosFragment.setQuery(query);
        searchCollectionsFragment.setQuery(query);
        searchUsersFragment.setQuery(query);
    }

    @Override
    public boolean isFirstQuery() {
        return firstQuery;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d(TAG, "onDestroyView:called");
        firstQuery = true;
    }

    @Override
    public String getInitialQuery() {
        if (getArguments() != null) {
            return getArguments().getString(AllSearchActivity.KEY_QUERY);
        } else {
            return null;
        }

    }
}
