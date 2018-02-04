package com.sonu.app.splash.ui.content;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.data.cache.PhotosCache;
import com.sonu.app.splash.data.download.PhotoDownload;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.home.HomeNavItem;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.ContentListAdapter;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
import com.sonu.app.splash.ui.photo.Photo;
import com.sonu.app.splash.ui.photo.PhotoListItem;
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionActivity;
import com.sonu.app.splash.util.LogUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public abstract class ContentFragment<Presenter extends ContentContract.Presenter, DataModel>
        extends BaseFragment<Presenter>
        implements ContentContract.View {

    private static final String TAG = LogUtils.getLogTag(ContentFragment.class);

    @BindView(R.id.itemsRv)
    RecyclerView itemsRv;

    @BindView(R.id.progressBar)
    MaterialProgressBar progressBar;

    @BindView(R.id.errorIconIv)
    ImageView errorIconIv;

    @BindView(R.id.errorWrapperLl)
    View errorWrapperLl;

    @BindView(R.id.errorTitleTv)
    TextView errorTitleTv;

    @BindView(R.id.errorMessageTv)
    TextView errorMessageTv;

    @BindView(R.id.retryBtn)
    Button retryBtn;

    @BindView(R.id.parentCl)
    CoordinatorLayout parent;

    private ContentListAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    private MessageDialog messageDialog;

    private ContentListAdapter.AdapterListener<DataModel> adapterListener =
            new ContentListAdapter.AdapterListener<DataModel>() {

                @Override
                public ListItem createListItem(DataModel dataModel) {
                    return getListItem(dataModel);
                }

                @Override
                public void showIoException(int titleStringRes, int messageStringRes) {
                    ContentFragment
                            .this.showIoException(titleStringRes, messageStringRes);
                }

                @Override
                public void showUnsplashApiException(int titleStringRes, int messageStringRes) {
                    ContentFragment
                            .this.showUnsplashApiException(titleStringRes, messageStringRes);
                }

                @Override
                public void showUnknownException(String message) {
                    ContentFragment
                            .this.showUnknownException(message);
                }

                @Override
                public void showLoading() {
                    ContentFragment.this.showLoading();
                }

                @Override
                public void hideLoading() {
                    ContentFragment.this.hideLoading();
                }
            };

    @Inject
    Activity host;

    public ContentFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView:called");

        View root = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated:called");

        messageDialog = new MessageDialog(host);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated:called");

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getAllContent();
            }
        });
    }

    @Override
    public void setupList(ContentCache contentCache) {

        // changing grid size for orientation changes
        if (getActivity().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            layoutManager =
                    new StaggeredGridLayoutManager(3,
                            StaggeredGridLayoutManager.VERTICAL);
        } else {

            layoutManager =
                    new StaggeredGridLayoutManager(getSpanCount(),
                            StaggeredGridLayoutManager.VERTICAL);
        }

        itemsRv.setLayoutManager(layoutManager);

        adapter = new ContentListAdapter(
                getActivity(),
                new ListItemTypeFactory(),
                contentCache,
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
                                adapter.getMoreContent();
                            }
                        }
                    }
                });
    }

    @Override
    public void getAllContent() {

        adapter.getAllContent();
    }

    @Override
    public boolean isListEmpty() {
        return adapter.getItemCount() == 0;
    }

    @Override
    public void showLoading() {
        if (isListEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            errorWrapperLl.setVisibility(View.GONE);
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
            errorWrapperLl.setVisibility(View.VISIBLE);
            errorTitleTv.setVisibility(View.GONE);
            errorMessageTv.setVisibility(View.GONE);

            final AnimatedVectorDrawable avd =
                    (AnimatedVectorDrawable)
                            ContextCompat.getDrawable(getContext(), R.drawable.avd_no_connection);
            if (avd != null) {
                errorIconIv.setImageDrawable(avd);
                avd.start();
            }
        }
    }

    @Override
    public void showUnsplashApiException(int titleStringRes, int messageStringRes) {

        if (isListEmpty()) {
            progressBar.setVisibility(View.GONE);
            errorWrapperLl.setVisibility(View.VISIBLE);
            errorTitleTv.setVisibility(View.VISIBLE);
            errorMessageTv.setVisibility(View.VISIBLE);

            errorIconIv.setImageDrawable(
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
            errorWrapperLl.setVisibility(View.VISIBLE);
            errorTitleTv.setVisibility(View.VISIBLE);
            errorMessageTv.setVisibility(View.VISIBLE);

            errorIconIv.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_error_grey_56dp));

            errorTitleTv.setText(getString(R.string.unknown_exception_title));
            if (message != null) {
                if (!message.equals("")) {
                    errorMessageTv.setText(message);
                } else {
                    errorMessageTv.setText(getString(R.string.unknown_exception_message));
                }
            }
        } else {
            MessageDialogConfig messageDialogConfig = new MessageDialogConfig();
            messageDialogConfig.color(getContext().getColor(R.color.darkRed));
            messageDialogConfig.actionText("OK");
            messageDialogConfig.iconDrawable(R.drawable.ic_error_grey_56dp);
            messageDialogConfig.title(getString(R.string.unknown_exception_title));
            if (message != null) {
                if (!message.equals("")) {
                    messageDialogConfig.message(message);
                } else {
                    messageDialogConfig.message(getString(R.string.unknown_exception_message));
                }
            }
            messageDialog.setConfig(messageDialogConfig);
            messageDialog.show();
        }
    }

    protected abstract ListItem getListItem(DataModel dataModel);

    protected int getSpanCount() {
        return 2;
    }
}
