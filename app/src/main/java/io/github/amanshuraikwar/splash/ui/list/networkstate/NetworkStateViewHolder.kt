package io.github.amanshuraikwar.splash.ui.list.networkstate

import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.view.View
import io.github.amanshuraikwar.splash.R
import io.github.amanshuraikwar.splash.data.jetpack.Status
import io.github.amanshuraikwar.splash.ui.list.ViewHolder
import io.github.amanshuraikwar.splash.util.Util
import kotlinx.android.synthetic.main.item_network_state.view.*

/**
 * ViewHolder corresponding to CurrenctListItem
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 11/03/18.
 */
class NetworkStateViewHolder(itemView: View) : ViewHolder<NetworkStateListItem>(itemView) {

    private val TAG = Util.getTag(this)

    companion object {

        @LayoutRes
        val LAYOUT = R.layout.item_network_state

        fun toVisibility(constraint : Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun bind(listItem: NetworkStateListItem, host: FragmentActivity) {

        itemView.retryButton.setOnClickListener {
            listItem.retryCallback()
        }

        itemView.progressBar.visibility =
                toVisibility(listItem.networkState?.status == Status.RUNNING)

        itemView.retryButton.visibility =
                toVisibility(listItem.networkState?.status == Status.FAILED)

        itemView.errorMsgTv.visibility =
                toVisibility(listItem.networkState?.msg != null)

        itemView.errorMsgTv.text = listItem.networkState?.msg
    }
}