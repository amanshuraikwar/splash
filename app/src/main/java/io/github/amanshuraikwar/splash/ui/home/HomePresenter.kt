package io.github.amanshuraikwar.splash.ui.home

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import io.github.amanshuraikwar.multiitemlistadapter.ListItem
import io.github.amanshuraikwar.multiitemlistadapter.SimpleListItemOnClickListener
import io.github.amanshuraikwar.splash.bus.AppBus
import io.github.amanshuraikwar.splash.data.DataManager
import io.github.amanshuraikwar.splash.data.jetpack.NetworkState
import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.ui.base.BasePresenterImpl
import io.github.amanshuraikwar.splash.ui.list.ListItemTypeFactory
import io.github.amanshuraikwar.splash.ui.list.photo.PhotoListItem
import io.github.amanshuraikwar.splash.util.Util
import javax.inject.Inject

/**
 * Implementation of presenter of MainActivity.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 30/04/18.
 */
class HomePresenter @Inject constructor(appBus: AppBus, dataManager: DataManager)
    : BasePresenterImpl<HomeContract.View>(appBus, dataManager), HomeContract.Presenter {

    private val TAG = Util.getTag(this)

    private val repoResult = dataManager.getCuratedPhotosListing{ mapPhotoToListItem(it) }

    val posts = repoResult.pagedList
    val networkState = repoResult.networkState
    val refreshState = repoResult.refreshState

    //region parent class methods
    override fun onAttach(wasViewRecreated: Boolean) {
        super.onAttach(wasViewRecreated)

    }
    //endregion

    override fun retry() {
        val listing = repoResult
        listing.retry.invoke()
    }

    override fun posts(): LiveData<PagedList<ListItem<SimpleListItemOnClickListener, ListItemTypeFactory>>> {
        return posts
    }

    override fun networkState(): LiveData<NetworkState> {
        return networkState
    }

    private fun mapPhotoToListItem(photo: Photo) = PhotoListItem(photo)
}