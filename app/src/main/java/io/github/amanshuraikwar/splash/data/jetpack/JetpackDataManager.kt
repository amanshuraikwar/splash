package io.github.amanshuraikwar.splash.data.jetpack

import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.ui.list.ListItem

interface JetpackDataManager {

    fun getCuratedPhotosListing(mapper: (Photo) -> ListItem<*>): Listing<ListItem<*>>
}