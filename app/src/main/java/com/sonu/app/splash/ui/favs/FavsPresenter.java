package com.sonu.app.splash.ui.favs;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;

import com.sonu.app.splash.bus.AppBus;
import com.sonu.app.splash.data.DataManager;
import com.sonu.app.splash.data.local.room.favourites.FavCollection;
import com.sonu.app.splash.data.local.room.favourites.FavPhoto;
import com.sonu.app.splash.data.local.room.favourites.FavUser;
import com.sonu.app.splash.model.unsplash.Photo;
import com.sonu.app.splash.ui.architecture.BasePresenterImpl;
import com.sonu.app.splash.ui.architecture.PresenterPlugin;
import com.sonu.app.splash.ui.downloads.DownloadsContract;
import com.sonu.app.splash.ui.list.ListItem;
import com.sonu.app.splash.util.ModelUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amanshuraikwar on 17/02/18.
 */

public class FavsPresenter
        extends BasePresenterImpl<FavsContract.View>
        implements FavsContract.Presenter {

    private Disposable dataDisp, downloadPhotoDisp;;

    @Inject
    public FavsPresenter(AppBus appBus, DataManager dataManager, Activity activity) {
        super(appBus, dataManager, activity);
    }

    @Override
    public void attachView(FavsContract.View view, boolean wasViewRecreated) {
        super.attachView(view, wasViewRecreated);

        if (wasViewRecreated) {

            getData();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getData() {

        dataDisp =
                Observable
                        .zip(
                                getDataManager().getFavPhotos(),
                                getDataManager().getFavCollections(),
                                getDataManager().getFavUsers(),
                                Triple::new)
                        .flatMap(item ->
                                Observable.create(e -> {
                                    try {
                                        e.onNext(processData(item.first, item.second, item.third));
                                        e.onComplete();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        e.tryOnError(ex);
                                    }
                                }))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(item ->
                                getView().displayData((List<ListItem>) item));
    }

    @Override
    public void downloadPhoto(Photo photo) {

        downloadPhotoDisp = PresenterPlugin.DownloadPhoto.downloadPhoto(photo, this);
    }

    @Override
    public void detachView() {
        super.detachView();

        if (downloadPhotoDisp != null) {
            if (!downloadPhotoDisp.isDisposed()) {
                downloadPhotoDisp.dispose();
            }
        }
    }

    private List<ListItem> processData(List<FavPhoto> photos,
                                       List<FavCollection> collections,
                                       List<FavUser> users) {

        // reversing
        Collections.sort(photos, (x, y) -> {return y.getLikedAt().compareTo(x.getLikedAt());});
        Collections.sort(collections, (x, y) -> {return y.getLikedAt().compareTo(x.getLikedAt());});
        Collections.sort(users, (x, y) -> {return y.getLikedAt().compareTo(x.getLikedAt());});

        List<ListItem> listItems = new ArrayList<>();

        int i = 0;
        int i1=0, i2=0, i3=0;

        String lastDate = "";

        while (i < (photos.size() + collections.size() + users.size())) {

            String s1, s2, s3;

            if (i1 != photos.size()) {
                s1 = photos.get(i1).getLikedAt();
            } else {
                s1 = "";
            }

            if (i2 != collections.size()) {
                s2 = collections.get(i2).getLikedAt();
            } else {
                s2 = "";
            }

            if (i3 != users.size()) {
                s3 = users.get(i3).getLikedAt();
            } else {
                s3 = "";
            }

            Log.i("process", s1+" "+s2+" "+s3);

            int curIndex = argmax(s1, s2, s3);

            Log.i("argmax", curIndex+"");

            switch (curIndex) {

                case 0:
                    if (!lastDate.equals(photos.get(i1).getLikedAt())) {
                        listItems.add(getView().getFavHeaderListItem(photos.get(i1).getLikedAt()));
                        lastDate = photos.get(i1).getLikedAt();
                    }
                    listItems.add(getView().getListItem(ModelUtils.buildPhotoObj(photos.get(i1))));
                    i1++;
                    break;
                case 1:
                    if (!lastDate.equals(collections.get(i2).getLikedAt())) {
                        listItems.add(getView().getFavHeaderListItem(collections.get(i2).getLikedAt()));
                        lastDate = collections.get(i2).getLikedAt();
                    }
                    listItems.add(getView().getListItem(ModelUtils.buildCollectionObj(collections.get(i2))));
                    i2++;
                    break;
                case 2:
                    if (!lastDate.equals(users.get(i3).getLikedAt())) {
                        listItems.add(getView().getFavHeaderListItem(users.get(i3).getLikedAt()));
                        lastDate = users.get(i3).getLikedAt();
                    }
                    listItems.add(getView().getListItem(ModelUtils.buildUserObj(users.get(i3))));
                    i3++;
                    break;
            }

            i++;
        }

        return listItems;
    }

    private int argmax(String s1, String s2, String s3) {

        if (s1.compareTo(s2) >= 0) {

            if (s1.compareTo(s3) >= 0) {
                return 0;
            } else {
                return 2;
            }
        } else {

            if (s2.compareTo(s3) >= 0) {
                return 1;
            } else {
                return 2;
            }
        }

    }

    // from https://stackoverflow.com/a/32484678/5011101
    public static class Triple<F, S, T> {

        public final F first;
        public final S second;
        public final T third;

        public Triple(F first, S second, T third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Triple)) {
                return false;
            }
            Triple<?, ?, ?> p = (Triple<?, ?, ?>) o;
            return first.equals(p.first) && second.equals(p.second) && third.equals(p.third);
        }

        private static boolean equals(Object x, Object y) {
            return (x == null && y == null) || (x != null && x.equals(y));
        }

        @Override
        public int hashCode() {
            return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode()) ^ (third == null ? 0 : third.hashCode());
        }

        public static <F, S, T> Triple <F, S, T> create(F f, S s, T t) {
            return new Triple<F, S, T>(f, s, t);
        }
    }
}
