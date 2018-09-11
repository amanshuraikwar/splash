package io.github.amanshuraikwar.splash.ui.list.networkstate

import io.github.amanshuraikwar.multiitemlistadapter.ListItem
import io.github.amanshuraikwar.multiitemlistadapter.SimpleListItemOnClickListener
import io.github.amanshuraikwar.splash.data.jetpack.NetworkState
import io.github.amanshuraikwar.splash.ui.list.ListItemTypeFactory

/**
 * ListItem representing a CryptoCurrency in the RecyclerView.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 11/03/18.
 */
class NetworkStateListItem(var networkState: NetworkState?,
                           var retryCallback: () -> Unit)
    : ListItem<SimpleListItemOnClickListener, ListItemTypeFactory>() {

    override fun id() = ""

    override fun concreteClass() = NetworkStateListItem::class.toString()

    override fun type(typeFactory: ListItemTypeFactory): Int {
        return typeFactory.type(this)
    }
}