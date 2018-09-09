package io.github.amanshuraikwar.splash.ui.list.networkstate

import io.github.amanshuraikwar.splash.data.jetpack.NetworkState
import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.ui.list.ListItem
import io.github.amanshuraikwar.splash.ui.list.ListItemTypeFactory
import io.github.amanshuraikwar.splash.ui.list.SimpleListItemOnClickListener

/**
 * ListItem representing a CryptoCurrency in the RecyclerView.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 11/03/18.
 */
class NetworkStateListItem(var networkState: NetworkState?,
                           var retryCallback: () -> Unit)
    : ListItem<SimpleListItemOnClickListener>() {

    override fun id() = ""

    override fun concreteClass() = NetworkStateListItem::class.toString()

    override fun type(typeFactory: ListItemTypeFactory): Int {
        return typeFactory.type(this)
    }
}