package com.sonu.app.splash.ui.allphotos;

import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.NewPhotosCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.list.photolist.PhotoListAdapter;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.home.HomeNavItem;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photo.PhotoOnClickListener;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

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

    @BindView(R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(R.id.progressBar)
    MaterialProgressBar progressBar;

    @BindView(R.id.noConnection)
    ImageView noConnection;

    @BindView(R.id.errorLl)
    View errorLl;

    @BindView(R.id.retryBtn)
    Button retryBtn;

    @BindView(R.id.errorTitleTv)
    TextView errorTitleTv;

    @BindView(R.id.errorMessageTv)
    TextView errorMessageTv;

    @BindView(R.id.parent)
    CoordinatorLayout coordinatorLayout;

    private PhotoOnClickListener photoOnClickListener =
            new PhotoOnClickListener() {
                @Override
                public void onDownloadBtnClick(Photo photo) {

                    Log.d(TAG, "onDownloadBtnClick:called");

                    getPresenter().onDownloadBtnClick(photo);
                }

                @Override
                public void onClick(Photo photo, View itemView) {

                    Log.d(TAG, "onPhotoClick:called");

                    Intent i = new Intent(getActivity(), PhotoDescriptionActivity.class);
                    i.putExtra(PhotoDescriptionActivity.KEY_PHOTO, photo);

                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                                    Pair.create(itemView,
                                            getActivity().getString(R.string.transition_photo)),
                                    Pair.create(itemView,
                                            getActivity().getString(R.string.transition_photo_description_background)));

                    startActivity(i, options.toBundle());

                    // todo temp
                    //getPresenter().onPhotoClick(photo);
                }
            };

    private PhotoListAdapter adapter;
    private PhotoListAdapter.AdapterListener adapterListener =
            new PhotoListAdapter.AdapterListener() {
                @Override
                public ListItem createListItem(Photo photo) {
                    PhotoListItem listItem = new PhotoListItem(photo);
                    listItem.setOnClickListener(photoOnClickListener);
                    return listItem;
                }

                @Override
                public void showIoException(int titleStringRes, int messageStringRes) {
                    AllPhotosFragment
                            .this.showIoException(titleStringRes, messageStringRes);
                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
                    AllPhotosFragment
                            .this.showUnsplashApiException(titleStringRes, messageStringRes);
                }

                @Override
                public void showUnknownException(String message) {
                    AllPhotosFragment
                            .this.showUnknownException(message);
                }

                @Override
                public void showLoading() {
                    AllPhotosFragment.this.showLoading();
                }

                @Override
                public void hideLoading() {
                    AllPhotosFragment.this.hideLoading();
                }
            };

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

        View root = inflater.inflate(R.layout.fragment_all_photos, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated:called");

        messageDialog = new MessageDialog(getContext());

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

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume:called");

        // register receiver for download complete
        getActivity().registerReceiver(downloadReceiver, filter);
    }

    // for testing
    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause:called");

        // unregister receiver for download complete
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

        Log.d(TAG, "onViewCreated:called");

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getAllPhotos();
            }
        });


    }

    @Override
    public void setupList(DataManager dataManager, NewPhotosCache photosCache) {

        // changing grid size for orientation changes
        if (getActivity().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            layoutManager =
                    new StaggeredGridLayoutManager(3,
                            StaggeredGridLayoutManager.VERTICAL);
        } else {

            layoutManager =
                    new StaggeredGridLayoutManager(2,
                            StaggeredGridLayoutManager.VERTICAL);
        }

        itemsRv.setLayoutManager(layoutManager);

        adapter = new PhotoListAdapter(
                getActivity(),
                new ListItemTypeFactory(),
                photosCache,
                dataManager,
                adapterListener);

        itemsRv.setAdapter(adapter);

        itemsRv.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                        int lastVisibleItems[] = new int[3];

                        layoutManager.findLastVisibleItemPositions(lastVisibleItems);

                        if (lastVisibleItems[0] == adapter.getItemCount()-1
                                || lastVisibleItems[1] == adapter.getItemCount()-1) {

                            if (!isListEmpty()) {
                                adapter.getMorePhotos();
                            }
                        }
                    }
                });
    }

    @Override
    public void getAllPhotos() {

        adapter.getAllPhotos();
    }

    @Override
    public int getHomeNavItemId() {
        return AllPhotosFragment.class.hashCode();
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getItemCount() == 0;
    }

    @Override
    public void showLoading() {
        if (isListEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            errorLl.setVisibility(View.GONE);
        }
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
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_cloud_off_grey_56dp));

            errorTitleTv.setText(getString(titleStringRes));
            errorMessageTv.setText(getString(messageStringRes));
        } else {
            MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
            messageDialogConfig.color(getContext().getColor(R.color.darkRed));
            messageDialogConfig.actionText("OK");
            messageDialogConfig.iconDrawable(R.drawable.ic_cloud_off_grey_56dp);
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
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_error_grey_56dp));

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
            messageDialogConfig.color(getContext().getColor(R.color.darkRed));
            messageDialogConfig.actionText("OK");
            messageDialogConfig.iconDrawable(R.drawable.ic_error_grey_56dp);
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
