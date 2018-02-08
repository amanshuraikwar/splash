package com.sonu.app.splash.ui.list;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.sonu.app.splash.data.cache.ContentCache;
import com.sonu.app.splash.util.LogUtils;
import com.sonu.app.splash.util.UiExceptionUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContentListAdapter<DataModel> extends RecyclerViewAdapter {

    private static final String TAG = LogUtils.getLogTag(ContentListAdapter.class);

    private ContentCache contentCache;
    private AdapterListener adapterListener;

    public ContentListAdapter(@NonNull FragmentActivity parentActivity,
                              @NonNull ListItemTypeFactory typeFactory,
                              @NonNull ContentCache contentCache,
                              @Nonnull AdapterListener adapterListener) {
        super(parentActivity, typeFactory);
        this.contentCache = contentCache;
        this.adapterListener = adapterListener;
    }

    /**
     * to get all photos according to cache state
     * -> if : cache empty then fetch more photos
     * -> else if : cache is not empty fetch cached photos
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
     * to fetch cached photos from photos cache
     */
    public void getCachedContent() {

        contentCache
                .getCachedContent()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DataModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<DataModel> content) {
                        Log.d(TAG, "getMoreContent:onNext:called");
                        setListItems(adapterListener.createListItems(content));
                        notifyDataSetChanged();
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getMoreContent:onError:called");
                        Log.e(TAG, "getMoreContent:onError:error="+e);
                        e.printStackTrace();
                        UiExceptionUtils.handleUiException(e, adapterListener);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getMoreContent:onCompleted:called");
                    }
                });
    }

    /**
     * to fetch more photos in the photos cache
     */
    public void getMoreContent() {

        contentCache
                .getMoreContent()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DataModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        adapterListener.showLoading();
                    }

                    @Override
                    public void onNext(List<DataModel> content) {
                        Log.d(TAG, "getMoreContent:onNext:called");
                        int lastIndex = getItemCount() - 1;
                        addListItems(adapterListener.createListItems(content));
                        notifyItemRangeInserted(lastIndex+1, content.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getMoreContent:onError:called");
                        Log.e(TAG, "getMoreContent:onError:error="+e);
                        e.printStackTrace();
                        UiExceptionUtils.handleUiException(e, adapterListener);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getMoreContent:onCompleted:called");
                        adapterListener.hideLoading();
                    }
                });
    }

    public static abstract class AdapterListener<DataModel> {

        public abstract ListItem createListItem(DataModel dataModel);

        public List<ListItem> createListItems(List<DataModel> dataModels) {

            List<ListItem> listItems = new ArrayList<>();

            for (DataModel dataModel : dataModels) {
                listItems.add(createListItem(dataModel));
            }

            return listItems;
        }

        public abstract void showIoException(int titleStringRes, int messageStringRes);
        public abstract void showUnsplashApiException(int titleStringRes, int messageStringRes);
        public abstract void showUnknownException(String message);
        public abstract void showLoading();
        public abstract void hideLoading();
    }
}
