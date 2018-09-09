package io.github.amanshuraikwar.splash.data.network

import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.util.Util
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of Network Data Manager.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 30/04/18.
 */
class NetworkDataManagerImpl @Inject constructor(
        private val apiInterface: ApiInterface) : NetworkDataManager {

    private val TAG = Util.getTag(this)

    @Throws(IOException::class)
    override fun getAllPhotos(page: Int, orderBy: String, perPage: Int): List<Photo>? {
        return apiInterface.getAllPhotos(page, orderBy, perPage).execute().body()
    }

    @Throws(IOException::class)
    override fun getCurartedPhotos(page: Int, orderBy: String, perPage: Int): List<Photo>? {
        return apiInterface.getCuratedPhotos(page, orderBy, perPage).execute().body()
    }
}