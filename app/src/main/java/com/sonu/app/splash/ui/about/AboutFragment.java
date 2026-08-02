package com.sonu.app.splash.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sonu.app.splash.R;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class AboutFragment extends BaseFragment<AboutContract.Presenter>
        implements AboutContract.View {

    private static final String TAG = LogUtils.getLogTag(AboutFragment.class);
    ImageView projectGithubIv;
    ImageView arLinkedinIv;
    ImageView arGithubIv;
    ImageView arInstagramIv;

    void onSlIvClick() {
        startBrowserActivity("https://developer.android.com/topic/libraries/support-library");
    }

    void onBnIvClick() {
        startBrowserActivity("http://jakewharton.github.io/butterknife/");
    }

    void onGlideIvClick() {
        startBrowserActivity("https://github.com/bumptech/glide");
    }

    void onOkhttpIvClick() {
        startBrowserActivity("http://square.github.io/okhttp/");
    }

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
        projectGithubIv = root.findViewById(R.id.projectGithubIv);
        arLinkedinIv = root.findViewById(R.id.arLinkedinIv);
        arGithubIv = root.findViewById(R.id.arGithubIv);
        arInstagramIv = root.findViewById(R.id.arInstagramIv);
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

        view.findViewById(R.id.slLinkIv).setOnClickListener(v -> onSlIvClick());
        view.findViewById(R.id.bnLinkIv).setOnClickListener(v -> onBnIvClick());
        view.findViewById(R.id.glideLinkIv).setOnClickListener(v -> onGlideIvClick());
        view.findViewById(R.id.okhttpLinkIv).setOnClickListener(v -> onOkhttpIvClick());
        view.findViewById(R.id.rfLinkIv).setOnClickListener(v -> onRfIvClick());
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
