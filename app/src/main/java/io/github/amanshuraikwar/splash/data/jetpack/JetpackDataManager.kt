package io.github.amanshuraikwar.splash.data.jetpack

import io.github.amanshuraikwar.multiitemlistadapter.ListItem
import io.github.amanshuraikwar.splash.model.Photo

interface JetpackDataManager {

    fun <T1, T2> getCuratedPhotosListing(mapper: (Photo) -> ListItem<T1, T2>): Listing<ListItem<T1, T2>>
}