package io.github.amanshuraikwar.splash.ui.home

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.util.Log
import io.github.amanshuraikwar.splash.R
import io.github.amanshuraikwar.splash.data.jetpack.NetworkState
import io.github.amanshuraikwar.splash.ui.base.BaseActivity
import io.github.amanshuraikwar.splash.ui.list.InfiniteListAdapter
import io.github.amanshuraikwar.splash.ui.list.ListItem
import io.github.amanshuraikwar.splash.ui.list.ListItemTypeFactory
import io.github.amanshuraikwar.splash.util.Util
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    @Suppress("PrivatePropertyName")
    private val TAG = Util.getTag(this)

    lateinit var adapter: InfiniteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initAdapter()
    }

    private fun initAdapter() {

        adapter = InfiniteListAdapter(this, ListItemTypeFactory()) { presenter.retry() }
        itemsRv.adapter = adapter

        presenter.posts().observe(this, Observer<PagedList<ListItem<*>>> {
            Log.d(TAG, "getting new posts: $it")
            adapter.submitList(it)
        })
        presenter.networkState().observe(this, Observer {
            Log.d(TAG, "network state changed: $it")
            adapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {
//        model.refreshState.observe(this, Observer {
//            swipe_refresh.isRefreshing = it == NetworkState.LOADING
//        })
//        swipe_refresh.setOnRefreshListener {
//            model.refresh()
//        }
    }

    override fun submitList(pagerList: PagedList<ListItem<*>>?) {
        adapter.submitList(pagerList)
    }

    override fun setNetworkState(networkState: NetworkState?) {
        adapter.setNetworkState(networkState)
    }
}
