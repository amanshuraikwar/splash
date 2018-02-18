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
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class AboutFragment extends BaseFragment<AboutContract.Presenter>
        implements AboutContract.View {

    private static final String TAG = LogUtils.getLogTag(AboutFragment.class);

    @BindView(R.id.projectGithubIv)
    ImageView projectGithubIv;

    @BindView(R.id.arLinkedinIv)
    ImageView arLinkedinIv;

    @BindView(R.id.arGithubIv)
    ImageView arGithubIv;

    @BindView(R.id.arInstagramIv)
    ImageView arInstagramIv;

    @OnClick(R.id.slLinkIv)
    void onSlIvClick() {
        startBrowserActivity("https://developer.android.com/topic/libraries/support-library");
    }

    @OnClick(R.id.bnLinkIv)
    void onBnIvClick() {
        startBrowserActivity("http://jakewharton.github.io/butterknife/");
    }

    @OnClick(R.id.glideLinkIv)
    void onGlideIvClick() {
        startBrowserActivity("https://github.com/bumptech/glide");
    }

    @OnClick(R.id.okhttpLinkIv)
    void onOkhttpIvClick() {
        startBrowserActivity("http://square.github.io/okhttp/");
    }

    @OnClick(R.id.rfLinkIv)
    void onRfIvClick() {
        startBrowserActivity("http://square.github.io/retrofit/");
    }

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

        projectGithubIv.setOnClickListener(
                v -> startBrowserActivity(getString(R.string.project_github)));

        arLinkedinIv.setOnClickListener(
                v -> startBrowserActivity(getString(R.string.developer_linkedin)));

        arGithubIv.setOnClickListener(
                v -> startBrowserActivity(getString(R.string.developer_github)));

        arInstagramIv.setOnClickListener(
                v -> startBrowserActivity(getString(R.string.developer_instagram)));
    }

    private void startBrowserActivity(String url) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public int getHomeNavItemId() {
        return AboutFragment.class.hashCode();
    }
}