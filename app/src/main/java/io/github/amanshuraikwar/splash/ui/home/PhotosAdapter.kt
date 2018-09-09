package io.github.amanshuraikwar.splash.ui.home

import android.arch.paging.PagedListAdapter
import android.support.v4.app.FragmentActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import io.github.amanshuraikwar.splash.R
import io.github.amanshuraikwar.splash.data.jetpack.NetworkState
import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.util.Util

class PhotosAdapter(private val host : FragmentActivity,
                    private val retryCallback: () -> Unit)
    : PagedListAdapter<Photo, RecyclerView.ViewHolder>(PHOTO_COMPARATOR){

    private val TAG = Util.getTag(this)
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_photo -> PhotoItemViewHolder.create(parent)
            R.layout.item_network_state -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_photo
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        when (getItemViewType(position)) {

            R.layout.item_photo -> {
                (holder as PhotoItemViewHolder).bindTo(getItem(position)?.photoUrls?.regular ?: "https://images.unsplash.com/photo-1506757171859-d7ba4a0c9a94?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&dl=amanshu-raikwar-396508-unsplash.jpg&s=28aa9521f129c6e4c98daed142349f55", host)
                Log.i(TAG, getItem(position)?.toString())
            }

            R.layout.item_network_state -> (holder as NetworkStateItemViewHolder).bindTo(
                    networkState)
        }
    }

//    override fun onBindViewHolder(
//            holder: RecyclerView.ViewHolder,
//            position: Int,
//            payloads: MutableList<Any>) {
//        if (payloads.isNotEmpty()) {
//            val item = getItem(position)
//            (holder as PhotoItemViewHolder).updateScore(item)
//        } else {
//            onBindViewHolder(holder, position)
//        }
//    }

    companion object {

        val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                    oldItem.id == newItem.id
        }
    }

}