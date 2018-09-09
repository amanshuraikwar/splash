package io.github.amanshuraikwar.splash.data.network

import io.github.amanshuraikwar.splash.model.Photo

/**
 * Data Manager for the content fetched from the network.
 * This is the single entry point to fetch data from the network.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 30/04/18.
 */
interface NetworkDataManager {

    /**
     *
     */
    fun getAllPhotos(page: Int, orderBy: String, perPage: Int): List<Photo>?

    /**
     *
     */
    fun getCurartedPhotos(page: Int, orderBy: String, perPage: Int): List<Photo>?
}