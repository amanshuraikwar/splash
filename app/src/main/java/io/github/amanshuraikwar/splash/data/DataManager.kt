package io.github.amanshuraikwar.splash.data

import io.github.amanshuraikwar.splash.data.jetpack.JetpackDataManager
import io.github.amanshuraikwar.splash.data.network.NetworkDataManager

/**
 * Data Manager abstraction for the whole app.
 * This is a single entry point to access/modify any kind of data the app uses.
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 06/03/18.
 */
interface DataManager : NetworkDataManager, JetpackDataManager