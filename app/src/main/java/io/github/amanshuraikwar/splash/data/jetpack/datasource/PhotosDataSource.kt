package io.github.amanshuraikwar.splash.data.jetpack.datasource

import io.github.amanshuraikwar.splash.data.network.ApiInterface
import io.github.amanshuraikwar.splash.model.Photo
import retrofit2.Call
import java.util.concurrent.Executor

abstract class PhotosDataSource
constructor(retryExecutor: Executor,
                    protected val apiInterface: ApiInterface)
    : ContentDataSource<Photo>(retryExecutor) {

    var orderBy: String = ApiInterface.ORDER_BY_LATEST

    override fun getNetworkCall(page: Int, perPage: Int)
            = getNetworkCall(page, perPage, orderBy)

    protected abstract fun getNetworkCall(page: Int, perPage: Int, orderBy: String): Call<List<Photo>>
}