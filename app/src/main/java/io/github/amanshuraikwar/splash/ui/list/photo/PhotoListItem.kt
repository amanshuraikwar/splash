package io.github.amanshuraikwar.splash.ui.list.photo

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
class PhotoListItem(val photo: Photo)
    : ListItem<SimpleListItemOnClickListener>() {

    override fun id() = photo.id!!

    override fun concreteClass() = PhotoListItem::class.toString()

    override fun type(typeFactory: ListItemTypeFactory): Int {
        return typeFactory.type(this)
    }
}