package io.github.amanshuraikwar.splash.data.jetpack.datasource

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WrapperContentDataSource<DataModelOut, DataModelIn>(
        private val contentDataSource: ContentDataSource<DataModelIn>,
        private val mapper: (List<DataModelIn>) -> List<DataModelOut>)
    : ContentDataSource<DataModelOut>(
        contentDataSource.retryExecutor,
        contentDataSource.networkState,
        contentDataSource.initialLoad) {

    override fun invalidate() {
        contentDataSource.invalidate()
    }

    override fun isInvalid(): Boolean {
        return contentDataSource.isInvalid
    }

    override fun removeInvalidatedCallback(onInvalidatedCallback: InvalidatedCallback) {
        contentDataSource.removeInvalidatedCallback(onInvalidatedCallback)
    }

    override fun addInvalidatedCallback(onInvalidatedCallback: InvalidatedCallback) {
        contentDataSource.addInvalidatedCallback(onInvalidatedCallback)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<DataModelOut>) {
        contentDataSource.loadRange(
                params,
                callback = object : LoadRangeCallback<DataModelIn>() {

                    override fun onResult(data: MutableList<DataModelIn>) {
                        callback.onResult(mapper(data))
                    }
                })
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<DataModelOut>) {
        contentDataSource.loadInitial(
                params,
                callback = object : LoadInitialCallback<DataModelIn>() {

                    override fun onResult(data: MutableList<DataModelIn>, position: Int, totalCount: Int) {
                        callback.onResult(mapper(data), position, totalCount)
                    }

                    override fun onResult(data: MutableList<DataModelIn>, position: Int) {
                        callback.onResult(mapper(data), position)
                    }
                })
    }

    override fun  retryAllFailed() {
        contentDataSource.retryAllFailed()
    }

    override fun getNetworkCall(page: Int, perPage: Int)
            =
            object : Call<List<DataModelOut>> {
                override fun enqueue(callback: Callback<List<DataModelOut>>) {
                    // do nothing
                }

                override fun isExecuted() = false

                override fun clone() = this

                override fun isCanceled() = false

                override fun cancel() {
                    // do nothing
                }

                override fun execute() = Response.success<List<DataModelOut>>(null)

                override fun request() = Request.Builder().build()

            }
}