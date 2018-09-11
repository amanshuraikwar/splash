package io.github.amanshuraikwar.splash.ui.home

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import io.github.amanshuraikwar.multiitemlistadapter.ListItem
import io.github.amanshuraikwar.multiitemlistadapter.SimpleListItemOnClickListener
import io.github.amanshuraikwar.splash.data.jetpack.NetworkState
import io.github.amanshuraikwar.splash.ui.base.BasePresenter
import io.github.amanshuraikwar.splash.ui.base.BaseView
import io.github.amanshuraikwar.splash.ui.list.ListItemTypeFactory

/**
 * MVP contract of MainActivity.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 30/04/18.
 */
interface HomeContract {

    interface View : BaseView {
        fun submitList(pagerList: PagedList<ListItem<*, *>>?)
        fun setNetworkState(networkState: NetworkState?)
    }

    interface Presenter : BasePresenter<View> {
        fun retry()
        fun posts() : LiveData<PagedList<ListItem<SimpleListItemOnClickListener, ListItemTypeFactory>>>
        fun networkState() : LiveData<NetworkState>
    }
}