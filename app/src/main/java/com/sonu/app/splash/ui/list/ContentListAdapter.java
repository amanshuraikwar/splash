package com.sonu.app.splash.ui.list;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.ui.loading.LoadingListItem;
import com.sonu.app.splash.ui.loading.LoadingOnClickListener;
import com.sonu.app.splash.ui.loading.LoadingViewHolder;
import com.sonu.app.splash.ui.architecture.CoroutineTaskScope;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UiExceptionUtils;

import java.util.ArrayList;
import java.util.List;



public class ContentListAdapter<DataModel> extends RecyclerViewAdapter {

    private static final String TAG = LogUtils.getLogTag(ContentListAdapter.class);

    private ContentCache contentCache;
    private AdapterListener adapterListener;
    private boolean fetching = false;
    private final CoroutineTaskScope taskScope = new CoroutineTaskScope();

    public ContentListAdapter(@NonNull FragmentActivity parentActivity,
                              @NonNull ListItemTypeFactory typeFactory,
                              @NonNull ContentCache contentCache,
                              @NonNull AdapterListener adapterListener) {
        super(parentActivity, typeFactory);
        this.contentCache = contentCache;
        this.adapterListener = adapterListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        // making loadingViewHolder cover full span
        if (holder instanceof LoadingViewHolder) {

            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    layoutParams.setFullSpan(true);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
            // Perform a full update
            onBindViewHolder(holder, position);
        } else {
            // Perform a partial update
            for (Object payload : payloads) {
                if (payload instanceof LoadingListItem.STATE) {
                    ((LoadingViewHolder) holder).bindState((LoadingListItem.STATE) payload);
                }
            }
        }
    }

    private synchronized boolean isFetching() {
        return fetching;
    }

    private synchronized void setFetching(boolean fetching) {
        this.fetching = fetching;
    }

    /**
     * to get all content according to cache state
     * -> if : cache empty then fetch more content
     * -> else if : cache is not empty fetch cached content
     */
    public void getAllContent() {

        if (contentCache.isCacheEmpty()) {
            getMoreContent();
        } else {
            // getting already cached photos
            getCachedContent();
        }
    }

    /**
     * to fetch cached content from content cache
     */
    @SuppressWarnings({"WeakerAccess", "unchecked"})
    public void getCachedContent() {

        if (isFetching()) {
            return;
        }

        setFetching(true);
        taskScope.launch(
                () -> (List<DataModel>) contentCache.getCachedContent(),
                content -> {
                    Log.d(TAG, "getCachedContent:completed");
                    if (!content.isEmpty()) {
                        setListItems(adapterListener.createListItems(content));
                        addListItem(getLoadingListItem());
                        notifyDataSetChanged();
                    } else if (!isEmpty()) {
                        ((LoadingListItem) getListItems().get(getItemCount() - 1))
                                .setState(LoadingListItem.STATE.NORMAL);
                        notifyItemChanged(getItemCount() - 1, LoadingListItem.STATE.NORMAL);
                    }
                    setFetching(false);
                },
                error -> {
                    Log.e(TAG, "getCachedContent:error=" + error);
                    UiExceptionUtils.handleUiException(error, adapterListener, getActivity());
                    setFetching(false);
                });
    }

    /**
     * to fetch more content in the content cache
     */
    @SuppressWarnings("unchecked")
    public void getMoreContent() {

        if (isFetching()) {
            return;
        }

        setFetching(true);
        adapterListener.showLoading();
        taskScope.launch(
                () -> (List<DataModel>) contentCache.getMoreContent(),
                content -> {
                    Log.d(TAG, "getMoreContent:completed");
                    if (!content.isEmpty()) {
                        int lastIndex = getItemCount() - 1;
                        if (getItemCount() != 0) {
                            removeListItem(getItemCount() - 1);
                        }
                        addListItems(adapterListener.createListItems(content));
                        addListItem(getLoadingListItem());
                        notifyItemRangeInserted(lastIndex + 1, content.size());
                    } else if (!isEmpty()) {
                        ((LoadingListItem) getListItems().get(getItemCount() - 1))
                                .setState(LoadingListItem.STATE.NORMAL);
                        notifyItemChanged(getItemCount() - 1, LoadingListItem.STATE.NORMAL);
                    }
                    adapterListener.hideLoading();
                    setFetching(false);
                },
                error -> {
                    Log.e(TAG, "getMoreContent:error=" + error);
                    UiExceptionUtils.handleUiException(error, adapterListener, getActivity());
                    adapterListener.hideLoading();
                    setFetching(false);
                });
    }

    /**
     * loading list item - onRetry click
     */
    private LoadingOnClickListener loadingOnClickListener = this::getMoreContent;

    /**
     * loading list item - get list item
     * @return loadingListItem
     */
    private LoadingListItem getLoadingListItem() {

        LoadingListItem loadingListItem = new LoadingListItem();
        loadingListItem.setOnClickListener(loadingOnClickListener);
        return loadingListItem;
    }

    /**
     * receiving/sending "things" to/from the view
     * @param <DataModel> data model supported by the content cache
     */
    public static abstract class AdapterListener<DataModel> {

        public abstract ListItem createListItem(DataModel dataModel);

        public List<ListItem> createListItems(List<DataModel> dataModels) {

            List<ListItem> listItems = new ArrayList<>();

            for (DataModel dataModel : dataModels) {
                listItems.add(createListItem(dataModel));
            }

            return listItems;
        }

        public abstract void showLoading();
        public abstract void hideLoading();
        public abstract void showError(int errorIconDrawableId,
                                           String errorTitle,
                                           String errorMessage);
    }
}
