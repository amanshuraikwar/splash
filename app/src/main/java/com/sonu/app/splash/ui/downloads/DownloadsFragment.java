package com.sonu.app.splash.ui.downloads;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.local.room.photodownload.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.download.DownloadListItem;
import com.sonu.app.splash.ui.download.DownloadOnClickListener;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.RecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

                }

                @Override
                public void onOpenFileClick(PhotoDownload photoDownload) {
                    getPresenter().onOpenFileClick(photoDownload.getDownloadReference());
                }
            };

    @Inject
    Activity host;

    @Inject
    public DownloadsFragment() {
        // required empty constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(downloadReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(downloadReceiver);
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

        retryBtn.setOnClickListener(view1 -> getPresenter().getPhotos());

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
    public List<PhotoDownload> getPhotoDownloadsOnScreen() {

        List<PhotoDownload> photoDownloads = new ArrayList<>();
        for (ListItem item : adapter.getListItems()) {
            photoDownloads.add(((DownloadListItem)item).getPhotoDownload());
        }
        return photoDownloads;
    }

    @Override
    public void updatePhotoDownload(PhotoDownload photoDownload) {

        int index = 0;
        for (ListItem item : adapter.getListItems()) {
            DownloadListItem listItem = (DownloadListItem) item;
            if (listItem.getPhotoDownload().getDownloadReference()
                    == photoDownload.getDownloadReference()) {
                listItem.getPhotoDownload().setStatus(photoDownload.getStatus());
                adapter.notifyItemChanged(index);
                break;
            }
            index++;
        }

    }

    @Override
    public void sendFileIntent(Uri uri) {

        // todo
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("file", uri.getPath().split("/file:")[1]);
        Uri apkURI = FileProvider.getUriForFile(
                host,
                host.getApplicationContext()
                        .getPackageName() + ".provider", new File(uri.getPath().split("/file:")[1]));
        intent.setDataAndType(apkURI, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            getPresenter().onDownloadComplete(referenceId);
        }
    };

}
