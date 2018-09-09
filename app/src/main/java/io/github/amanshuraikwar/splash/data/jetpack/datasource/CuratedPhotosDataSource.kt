package io.github.amanshuraikwar.splash.data.jetpack.datasource

import android.util.Log
import io.github.amanshuraikwar.splash.data.network.ApiInterface
import io.github.amanshuraikwar.splash.di.Qualifiers
import io.github.amanshuraikwar.splash.model.Photo
import io.github.amanshuraikwar.splash.util.Util
import retrofit2.Call
import java.util.concurrent.Executor
import javax.inject.Inject

class CuratedPhotosDataSource
@Inject constructor(@Qualifiers.NetworkExecutor
                    retryExecutor: Executor,
                    apiInterface: ApiInterface)
    : PhotosDataSource(retryExecutor, apiInterface) {

    val TAG = Util.getTag(this)

    override fun getNetworkCall(page: Int, perPage: Int, orderBy: String): Call<List<Photo>> {
        Log.d(TAG, "getNetworkCall: called")
        return apiInterface.getCuratedPhotos(page = page, perPage = perPage, orderBy = orderBy)
    }
}