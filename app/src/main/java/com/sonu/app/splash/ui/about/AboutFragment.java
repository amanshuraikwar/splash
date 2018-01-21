package com.sonu.app.splash.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.home.HomeNavItem;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class AboutFragment extends BaseFragment<AboutContract.Presenter>
        implements AboutContract.View, HomeNavItem {

    private static final String TAG = LogUtils.getLogTag(AboutFragment.class);

    @BindView(R.id.projectGithubIv)
    ImageView projectGithubIv;

    @BindView(R.id.arLinkedinIv)
    ImageView arLinkedinIv;

    @BindView(R.id.arGithubIv)
    ImageView arGithubIv;

    @BindView(R.id.arInstagramIv)
    ImageView arInstagramIv;

    @Inject
    public AboutFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, root);
        return  root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectGithubIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.project_github)));
                startActivity(intent);
            }
        });

        arLinkedinIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.developer_linkedin)));
                startActivity(intent);
            }
        });

        arGithubIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.developer_github)));
                startActivity(intent);
            }
        });

        arInstagramIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getString(R.string.developer_instagram)));
                startActivity(intent);
            }
        });
    }

    @Override
    public int getHomeNavItemId() {
        return AboutFragment.class.hashCode();
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
}