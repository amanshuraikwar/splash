package com.sonu.app.splash.ui.allphotos;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.home.HomeNavItem;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.RecyclerViewAdapter;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class AllPhotosFragment extends BaseFragment<AllPhotosContract.Presenter>
        implements AllPhotosContract.View, HomeNavItem {

    private static final String TAG = LogUtils.getLogTag(AllPhotosFragment.class);
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;

    @BindView(com.sonu.app.splash.R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(com.sonu.app.splash.R.id.progressBar)
    MaterialProgressBar progressBar;

    @BindView(com.sonu.app.splash.R.id.noConnection)
    ImageView noConnection;

    @BindView(com.sonu.app.splash.R.id.errorLl)
    View errorLl;

    @BindView(com.sonu.app.splash.R.id.retryBtn)
    Button retryBtn;

    @BindView(com.sonu.app.splash.R.id.errorTitleTv)
    TextView errorTitleTv;

    @BindView(com.sonu.app.splash.R.id.errorMessageTv)
    TextView errorMessageTv;

    @BindView(com.sonu.app.splash.R.id.parent)
    CoordinatorLayout coordinatorLayout;

    private RecyclerViewAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    private MessageDialog messageDialog;

    @Inject
    public AllPhotosFragment() {
        // required empty constructor
    }

    // for testing
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach:called");
    }

    // for testing
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate:called");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView:called");

        View root = inflater.inflate(com.sonu.app.splash.R.layout.fragment_all_photos, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    // for testing
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated:called");

        // todo temp
        downloadManager =
                (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
    }

    // for testing
    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart:called");
    }

    // for testing
    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume:called");

        getActivity().registerReceiver(downloadReceiver, filter);
    }

    // for testing
    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause:called");

        getActivity().unregisterReceiver(downloadReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onPause:called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d(TAG, "onDestroyView:called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy:called");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d(TAG, "onDetach:called");
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (layoutManager == null) {
            layoutManager =
                    new StaggeredGridLayoutManager(2,
                            StaggeredGridLayoutManager.VERTICAL);
            itemsRv.setLayoutManager(layoutManager);
        }

        // changing grid size for orientation changes
        if (getActivity().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager =
                    new StaggeredGridLayoutManager(3,
                            StaggeredGridLayoutManager.VERTICAL);
            itemsRv.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new RecyclerViewAdapter(
                    getActivity(),
                    new ListItemTypeFactory(),
                    new ArrayList<ListItem>());
        }

        itemsRv.setAdapter(adapter);

        itemsRv.clearOnScrollListeners();

        itemsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastVisibleItems[] = new int[3];
                layoutManager.findLastVisibleItemPositions(lastVisibleItems);
                Log.d(TAG, "onScrollStateChanged:lastVisibleItems="
                        +Arrays.toString(lastVisibleItems));
                if (lastVisibleItems[0] == adapter.getItemCount()-1
                        || lastVisibleItems[1] == adapter.getItemCount()-1) {
                    if (!isListEmpty()) {
                        getPresenter().getMorePhotos();
                    }
                }
            }
        });

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().getAllPhotos();
            }
        });

        messageDialog = new MessageDialog(getContext());
    }

    @Override
    public int getHomeNavItemId() {
        return AllPhotosFragment.class.hashCode();
    }

    @Override
    public void displayPhotos(List<ListItem> photoListItems) {
        adapter.setListItems(photoListItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addPhotos(List<ListItem> photoListItems) {
        int lastIndex = adapter.getItemCount() - 1;
        adapter.addListItems(photoListItems);
        adapter.notifyItemRangeInserted(lastIndex+1, photoListItems.size());
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getItemCount() == 0;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        errorLl.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showIoException(int titleStringRes, int messageStringRes) {

        if (isListEmpty()) {
            progressBar.setVisibility(View.GONE);
            errorLl.setVisibility(View.VISIBLE);
            errorTitleTv.setVisibility(View.GONE);
            errorMessageTv.setVisibility(View.GONE);

            final AnimatedVectorDrawable avd =
                    (AnimatedVectorDrawable)
                            ContextCompat.getDrawable(getContext(), com.sonu.app.splash.R.drawable.avd_no_connection);
            if (avd != null) {
                noConnection.setImageDrawable(avd);
                avd.start();
            }
        }
    }

    @Override
    public void showUnsplashApiException(int titleStringRes, int messageStringRes) {

        if (isListEmpty()) {
            progressBar.setVisibility(View.GONE);
            errorLl.setVisibility(View.VISIBLE);
            errorTitleTv.setVisibility(View.VISIBLE);
            errorMessageTv.setVisibility(View.VISIBLE);

            noConnection.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), com.sonu.app.splash.R.drawable.ic_cloud_off_grey_56dp));

            errorTitleTv.setText(getString(titleStringRes));
            errorMessageTv.setText(getString(messageStringRes));
        } else {
            MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
            messageDialogConfig.color(getContext().getColor(com.sonu.app.splash.R.color.darkRed));
            messageDialogConfig.actionText("OK");
            messageDialogConfig.iconDrawable(com.sonu.app.splash.R.drawable.ic_cloud_off_grey_56dp);
            messageDialogConfig.title(getString(titleStringRes));
            messageDialogConfig.message(getString(messageStringRes));
            messageDialog.setConfig(messageDialogConfig);
            messageDialog.show();
        }
    }

    @Override
    public void showUnknownException(String message) {

        if (isListEmpty()) {
            progressBar.setVisibility(View.GONE);
            errorLl.setVisibility(View.VISIBLE);
            errorTitleTv.setVisibility(View.VISIBLE);
            errorMessageTv.setVisibility(View.VISIBLE);

            noConnection.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), com.sonu.app.splash.R.drawable.ic_error_grey_56dp));

            errorTitleTv.setText(getString(com.sonu.app.splash.R.string.unknown_exception_title));
            if (message != null) {
                if (!message.equals("")) {
                    errorMessageTv.setText(message);
                } else {
                    errorMessageTv.setText(getString(com.sonu.app.splash.R.string.unknown_exception_message));
                }
            }
        } else {
            MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
            messageDialogConfig.color(getContext().getColor(com.sonu.app.splash.R.color.darkRed));
            messageDialogConfig.actionText("OK");
            messageDialogConfig.iconDrawable(com.sonu.app.splash.R.drawable.ic_error_grey_56dp);
            messageDialogConfig.title(getString(com.sonu.app.splash.R.string.unknown_exception_title));
            if (message != null) {
                if (!message.equals("")) {
                    messageDialogConfig.message(message);
                } else {
                    messageDialogConfig.message(getString(com.sonu.app.splash.R.string.unknown_exception_message));
                }
            }
            messageDialog.setConfig(messageDialogConfig);
            messageDialog.show();
        }
    }

    @Override
    public boolean checkPermissions() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
                messageDialogConfig
                        .color(getContext().getColor(com.sonu.app.splash.R.color.darkOrange));
                messageDialogConfig.actionText("GIVE PERMISSIONS");
                messageDialogConfig.iconDrawable(R.drawable.ic_error_grey_56dp);
                messageDialogConfig
                        .title(getString(R.string.write_external_storage_permission_title));
                messageDialogConfig
                        .message(getString(R.string.write_external_storage_permission_message));
                messageDialogConfig
                        .actionOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                                messageDialog.dismiss();
                            }
                        });
                messageDialog.setConfig(messageDialogConfig);
                messageDialog.show();

                return false;
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                return false;
            }
        }

        return true;
    }

    @Override
    public void startPhotoDescriptionActivity(Photo photo) {
        Intent i = new Intent(getActivity(), PhotoDescriptionActivity.class);
        i.putExtra(PhotoDescriptionActivity.KEY_PHOTO, photo);
        startActivity(i);
    }

    // todo temp
    DownloadManager downloadManager;
    LongSparseArray<PhotoDownload> queuedDownloads = new LongSparseArray<>();
    IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

    @Override
    public void downloadPhoto(PhotoDownload photoDownload) {

        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(photoDownload.getDownloadUrl()));

        request.setTitle("Downloading photo");
        request.setDescription(photoDownload.getDownloadedFileName());

        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                photoDownload.getDownloadedFileName());

        long downloadReference = downloadManager.enqueue(request);
        queuedDownloads.append(downloadReference, photoDownload);
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            Toast.makeText(
                    getContext(),
                    "Download complete "
                            +queuedDownloads.get(referenceId).getDownloadedFileName(),
                    Toast.LENGTH_SHORT).show();

            queuedDownloads.remove(referenceId);
        }
    };
}