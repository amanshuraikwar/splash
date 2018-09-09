package io.github.amanshuraikwar.splash.data

import android.content.Context
import io.github.amanshuraikwar.splash.data.jetpack.JetpackDataManager
import io.github.amanshuraikwar.splash.data.jetpack.Listing
import io.github.amanshuraikwar.splash.data.network.NetworkDataManager
import io.github.amanshuraikwar.splash.di.ApplicationContext
import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.ui.list.ListItem
import javax.inject.Inject

/**
 * Implementation of the Data Manager for the app.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 07/03/18.
 */
class DataManagerImpl @Inject constructor(
        @ApplicationContext val context: Context,
        private val networkDataManager: NetworkDataManager,
        private val jetpackDataManager: JetpackDataManager) : DataManager {

    override fun getAllPhotos(page: Int, orderBy: String, perPage: Int)
            = networkDataManager.getAllPhotos(page, orderBy, perPage)


    override fun getCurartedPhotos(page: Int, orderBy: String, perPage: Int)
            = networkDataManager.getCurartedPhotos(page, orderBy, perPage)


    override fun getCuratedPhotosListing(mapper: (Photo) -> ListItem<*>)
            = jetpackDataManager.getCuratedPhotosListing(mapper)
}