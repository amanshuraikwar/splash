package io.github.amanshuraikwar.splash.ui.list.photo

import io.github.amanshuraikwar.multiitemlistadapter.ListItem
import io.github.amanshuraikwar.multiitemlistadapter.SimpleListItemOnClickListener
import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.ui.list.ListItemTypeFactory

/**
 * ListItem representing a CryptoCurrency in the RecyclerView.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 11/03/18.
 */
class PhotoListItem(val photo: Photo)
    : ListItem<SimpleListItemOnClickListener, ListItemTypeFactory>() {

    override fun id() = photo.id!!

    override fun concreteClass() = PhotoListItem::class.toString()

    override fun type(typeFactory: ListItemTypeFactory): Int {
        return typeFactory.type(this)
    }
}