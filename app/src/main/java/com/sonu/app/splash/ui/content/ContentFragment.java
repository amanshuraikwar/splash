package com.sonu.app.splash.ui.content;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sonu.app.splash.R;
import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.ui.architecture.BaseFragment;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.ui.list.ListItemTypeFactory;
import com.sonu.app.splash.ui.list.ContentListAdapter;
import com.sonu.app.splash.ui.loading.LoadingListItem;
import com.sonu.app.splash.ui.messagedialog.MessageDialog;
import com.sonu.app.splash.ui.messagedialog.MessageDialogConfig;
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

    @BindView(R.id.errorWrapperLl)
    View errorWrapperLl;

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
                public void showError(int errorIconDrawableId, String errorTitle, String errorMessage) {
                    ContentFragment.this.showError();
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

        retryBtn.setOnClickListener(view1 -> adapter.getAllContent());
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

        itemsRv.clearOnScrollListeners();

        itemsRv.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                        int lastVisibleItems[] = new int[3];

                        layoutManager.findLastVisibleItemPositions(lastVisibleItems);

                        Log.i(TAG, "onScrollStateChanged:lastVisibleItems");

                        if (lastVisibleItems[0] == adapter.getItemCount()-1
                                || lastVisibleItems[1] == adapter.getItemCount()-1) {

                            Log.i(TAG, "onScrollStateChanged:ready to load more");

                            if (!isListEmpty()) {
                                Log.d(TAG, "onScrollStateChanged:list not empty");
                                LoadingListItem loadingListItem =
                                        (LoadingListItem)
                                                adapter.getListItem(adapter.getItemCount() - 1);
                                Log.d(TAG, "onScrollStateChanged:state="+loadingListItem.getState());
                                if (loadingListItem.getState() == LoadingListItem.STATE.NORMAL) {
                                    adapter.getMoreContent();
                                }
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
        } else {

            ((LoadingListItem)adapter.getListItems().get(adapter.getItemCount() - 1))
                    .setState(LoadingListItem.STATE.LOADING);
            adapter.notifyItemChanged(adapter.getItemCount() - 1, LoadingListItem.STATE.LOADING);
        }
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void showError() {

        Toast.makeText(host, R.string.content_error_text, Toast.LENGTH_SHORT).show();

        if (isListEmpty()) {
            progressBar.setVisibility(View.GONE);
            errorWrapperLl.setVisibility(View.VISIBLE);
        } else {

            ((LoadingListItem)adapter.getListItems().get(adapter.getItemCount() - 1))
                    .setState(LoadingListItem.STATE.ERROR);
            adapter.notifyItemChanged(adapter.getItemCount() - 1, LoadingListItem.STATE.ERROR);
        }
    }

    protected abstract ListItem getListItem(DataModel dataModel);

    protected int getSpanCount() {
        return 2;
    }
}
