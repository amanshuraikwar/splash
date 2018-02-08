package com.sonu.app.splash.ui.downloads;

import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.local.room.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.download.DownloadListItem;
import com.sonu.app.splash.ui.download.DownloadOnClickListener;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.RecyclerViewAdapter;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photofullscreen.PhotoFullscreenActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 24/12/17.
 */

public class DownloadsFragment
        extends BaseFragment<DownloadsContract.Presenter>
        implements DownloadsContract.View {

    @BindView(R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(R.id.errorWrapperLl)
    View errorWrapperLl;

    @BindView(R.id.retryBtn)
    Button retryBtn;

    @BindView(R.id.errorProgressBar)
    MaterialProgressBar errorProgressBar;

    private RecyclerViewAdapter adapter;

    private DownloadOnClickListener downloadOnClickListener =
            new DownloadOnClickListener() {

                @Override
                public void onPhotoClick(PhotoDownload photoDownload, View transitionView) {

                    // todo
//                    Intent i = new Intent(getActivity(), PhotoFullscreenActivity.class);
//                    i.putExtra(PhotoFullscreenActivity.KEY_PHOTO, photo);
//
//                    ActivityOptions options =
//                            ActivityOptions.makeSceneTransitionAnimation(getActivity(),
//                                    transitionView,
//                                    getString(R.string.transition_photo_fullscreen));
//
//                    startActivity(i, options.toBundle());
                }
            };

    @Inject
    public DownloadsFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_downloads, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPresenter().getPhotos();
            }
        });

        itemsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void displayPhotos(List<PhotoDownload> photoDownloads) {

        adapter =
                new RecyclerViewAdapter(
                        getActivity(), new ListItemTypeFactory(), getListItems(photoDownloads));

        itemsRv.setAdapter(adapter);
    }

    private List<ListItem> getListItems(List<PhotoDownload> photoDownloads) {

        List<ListItem> listItems = new ArrayList<>();

        // reversing the order
        int index = photoDownloads.size() - 1;

        while (index >= 0) {

            DownloadListItem listItem = new DownloadListItem(photoDownloads.get(index));
            listItem.setOnClickListener(downloadOnClickListener);
            listItems.add(listItem);
            index--;
        }
        return listItems;
    }

    @Override
    public void showLoading() {
        errorWrapperLl.setVisibility(View.GONE);
        errorProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        errorWrapperLl.setVisibility(View.GONE);
        errorProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        errorWrapperLl.setVisibility(View.VISIBLE);
        errorProgressBar.setVisibility(View.GONE);
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
